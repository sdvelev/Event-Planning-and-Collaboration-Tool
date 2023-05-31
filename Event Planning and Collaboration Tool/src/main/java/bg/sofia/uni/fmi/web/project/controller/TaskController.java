package bg.sofia.uni.fmi.web.project.controller;

import bg.sofia.uni.fmi.web.project.dto.TaskDto;
import bg.sofia.uni.fmi.web.project.enums.TaskProgress;
import bg.sofia.uni.fmi.web.project.mapper.TaskMapper;
import bg.sofia.uni.fmi.web.project.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper mapper;

    @PostMapping
    public long addTask(@RequestBody @Valid TaskDto taskDto) {
        return taskService.addTask(mapper.toEntity(taskDto));
    }

    @GetMapping
    public List<TaskDto> getAllTasks() {
        return mapper.toDtoCollection(taskService.getAllTasks());
    }

    @GetMapping(value = "/search", params = {"id"})
    public ResponseEntity<TaskDto> findById(@RequestParam("id") long id) {
        return ResponseEntity.ok(mapper.toDto(taskService.getTaskById(id)));
    }

    @GetMapping(value = "/search", params = {"name"})
    public ResponseEntity<List<TaskDto>> findTasksByName(@RequestParam("name")
                                                         @NotNull(message = "The given name cannot be null!")
                                                         @NotEmpty(message = "The given name cannot be empty!")
                                                         @NotBlank(message = "The given name cannot be blank!")
                                                         String name) {
        return ResponseEntity.ok(mapper.toDtoCollection(taskService.getTasksByName(name)));
    }

    @GetMapping(value = "/search", params = {"event_id"})
    public ResponseEntity<List<TaskDto>> findByEventId(@RequestParam("event_id")
                                                       @Positive(message = "The given id cannot be 0 or less!")
                                                       long eventId) {
        return ResponseEntity.ok(mapper.toDtoCollection(taskService.getTasksByEventId(eventId)));
    }

    @GetMapping(value = "/search", params = {"participant_id"})
    public ResponseEntity<List<TaskDto>> findByParticipantId(@RequestParam("participant_id")
                                                             @Positive(message = "The given id cannot be 0 or less!")
                                                             long participantId) {
        return ResponseEntity.ok(mapper.toDtoCollection(taskService.getTasksByParticipantId(participantId)));
    }

    @GetMapping(value = "/search", params = {"created_by"})
    public ResponseEntity<List<TaskDto>> getTasksByCreatedBy(@NotNull(message = "The name cannot be null!")
                                                             @NotEmpty(message = "The name cannot be empty!")
                                                             @NotBlank(message = "The name cannot be blank!")
                                                             @RequestParam("created_by")
                                                             String createdBy) {
        return ResponseEntity.ok(mapper.toDtoCollection(taskService.getTasksByCreatedBy(createdBy)));
    }

    @GetMapping(value = "/search", params = {"due_date_after"})
    public ResponseEntity<List<TaskDto>> getTasksByDueDateAfter(@NotNull(message = "The due date after cannot be null!")
                                                                @NotEmpty(message = "The due date after cannot be empty!")
                                                                @NotBlank(message = "The due date after cannot be blank!")
                                                                @RequestParam("due_date_after")
                                                                String dueDateAfter) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return ResponseEntity.ok(
            mapper.toDtoCollection(taskService.getTaskByDueDateAfter(LocalDateTime.parse(dueDateAfter, formatter))));
    }

    @GetMapping(value = "/search", params = {"due_date_after", "due_date_before"})
    public ResponseEntity<List<TaskDto>> getTasksByDueDateBetween(
        @NotNull(message = "The due date after cannot be null!")
        @NotEmpty(message = "The due date after cannot be empty!")
        @NotBlank(message = "The due date after cannot be blank!")
        @RequestParam("due_date_after")
        String dueDateAfter,
        @NotNull(message = "The due date after cannot be null!")
        @NotEmpty(message = "The due date after cannot be empty!")
        @NotBlank(message = "The due date after cannot be blank!")
        @RequestParam("due_date_before")
        String dueDateBefore) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return ResponseEntity.ok(
            mapper.toDtoCollection(taskService.getTaskByDueDateBetween(LocalDateTime.parse(dueDateAfter, formatter),
                LocalDateTime.parse(dueDateBefore, formatter))));
    }

    @GetMapping(value = "/search", params = {"due_date_before"})
    public ResponseEntity<List<TaskDto>> getTasksByDueDateBefore(
        @NotNull(message = "The due date after cannot be null!")
        @NotEmpty(message = "The due date after cannot be empty!")
        @NotBlank(message = "The due date after cannot be blank!")
        @RequestParam("due_date_before")
        String dueDateBefore) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return ResponseEntity.ok(
            mapper.toDtoCollection(taskService.getTaskByDueDateBefore(LocalDateTime.parse(dueDateBefore, formatter))));
    }

    @PatchMapping(value = "/settings", params = {"name", "id"})
    public boolean updateName(@NotNull(message = "The name cannot be null!")
                              @NotEmpty(message = "The name cannot be empty!")
                              @NotBlank(message = "The name cannot be blank!")
                              @RequestParam("name")
                              String name,
                              @Positive(message = "The given ID cannot be less than zero!")
                              long taskId) {
        return taskService.updateName(name, taskId);
    }

    @PatchMapping(value = "/settings", params = {"description", "id"})
    public boolean updateDescription(@NotNull(message = "The description cannot be null!")
                                     @NotEmpty(message = "The description cannot be empty!")
                                     @NotBlank(message = "The description cannot be blank!")
                                     @RequestParam("description")
                                     String description,
                                     @Positive(message = "The given ID cannot be less than zero!")
                                     @RequestParam("id")
                                     long taskId) {
        return taskService.updateDescription(description, taskId);
    }

    @PatchMapping(value = "/settings", params = {"task_progress", "id"})
    public boolean updateTaskProgress(@NotNull(message = "The task progress cannot be null!")
                                      @RequestParam("task_progress")
                                      TaskProgress taskProgress,
                                      @Positive(message = "The given ID cannot be less than zero!")
                                      @RequestParam("id")
                                      long taskId) {
        return taskService.updateTaskProgress(taskProgress, taskId);
    }

    @PatchMapping(value = "/settings", params = {"due_date", "id"})
    public boolean updateDueDate(@NotNull(message = "The description cannot be null!")
                                 @NotEmpty(message = "The description cannot be empty!")
                                 @NotBlank(message = "The description cannot be blank!")
                                 @RequestParam("due_date")
                                 String dueDate,
                                 @Positive(message = "The given ID cannot be less than zero!")
                                 @RequestParam("id")
                                 long taskId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return taskService.updateDueDate(LocalDateTime.parse(dueDate, formatter), taskId);
    }

    @PatchMapping(value = "/settings", params = {"last_notified", "id"})
    public boolean updateLastNotified(@NotNull(message = "The description cannot be null!")
                                      @NotEmpty(message = "The description cannot be empty!")
                                      @NotBlank(message = "The description cannot be blank!")
                                      @RequestParam("last_notified")
                                      String lastNotified,
                                      @Positive(message = "The given ID cannot be less than zero!")
                                      @RequestParam("id")
                                      long taskId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return taskService.updateLastNotified(LocalDateTime.parse(lastNotified, formatter), taskId);
    }

    @PatchMapping(value = "/settings", params = {"created_by", "id"})
    public boolean updateCreatedBy(@RequestParam("created_by")
                                   @NotNull(message = "The createdBy cannot be null!")
                                   @NotEmpty(message = "The createdBy cannot be empty!")
                                   @NotBlank(message = "The createdBy cannot be blank!")
                                   String createdBy,
                                   @Positive(message = "The given ID cannot be less than zero!")
                                   @RequestParam("id")
                                   long taskId) {
        return taskService.updateCreatedBy(createdBy, taskId);
    }

    ///////////////////// UPDATE EVENT
    ///////////////////// UPDATE PARTICIPANT

    @PatchMapping(value = "/settings", params = {"creation_time", "id"})
    public boolean updateCreationTime(@NotNull(message = "The creation_time cannot be null!")
                                      @NotEmpty(message = "The creation_time cannot be empty!")
                                      @NotBlank(message = "The creation_time cannot be blank!")
                                      @RequestParam("created_by")
                                      String creationTime,
                                      @Positive(message = "The given ID cannot be less than zero!")
                                      @RequestParam("id")
                                      long taskId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return taskService.updateCreationTime(LocalDateTime.parse(creationTime, formatter), taskId);
    }

    @PatchMapping(value = "/settings", params = {"created_by", "id"})
    public boolean updateUpdatedBy(@NotNull(message = "The updatedBy cannot be null!")
                                   @NotEmpty(message = "The updatedBy cannot be empty!")
                                   @NotBlank(message = "The updatedBy cannot be blank!")
                                   @RequestParam("updated_by")
                                   String updatedBy,
                                   @Positive(message = "The given ID cannot be less than zero!")
                                   @RequestParam("id")
                                   long taskId) {
        return taskService.updateUpdatedBy(updatedBy, taskId);
    }

    @PatchMapping(value = "/settings", params = {"last_updated_time", "id"})
    public boolean updateLastUpdatedTime(@NotNull(message = "The creation_time cannot be null!")
                                         @NotEmpty(message = "The creation_time cannot be empty!")
                                         @NotBlank(message = "The creation_time cannot be blank!")
                                         @RequestParam("last_updated_time")
                                         String lastUpdatedTime,
                                         @Positive(message = "The given ID cannot be less than zero!")
                                         @RequestParam("id")
                                         long taskId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return taskService.updateLastUpdatedTime(LocalDateTime.parse(lastUpdatedTime, formatter), taskId);
    }

    @DeleteMapping(value = "/delete", params = {"deleted", "id"})
    public boolean deleteGuest(@RequestParam("deleted")
                               boolean deleted,
                               @Positive(message = "The given ID cannot be less than zero!")
                               @RequestParam("id")
                               long taskId) {
        return taskService.delete(deleted, taskId);
    }


}
