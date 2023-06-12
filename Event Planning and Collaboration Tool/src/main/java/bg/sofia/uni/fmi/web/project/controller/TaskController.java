package bg.sofia.uni.fmi.web.project.controller;

import bg.sofia.uni.fmi.web.project.dto.TaskDto;
import bg.sofia.uni.fmi.web.project.mapper.TaskMapper;
import bg.sofia.uni.fmi.web.project.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@Validated
@Mapper
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper mapper;

    @PostMapping(params = {"assigned_event_id", "assigned_participant_id", "task_progress"})
    public long addTask(@Valid @NotNull(message = "The guestDto cannot be null!") @RequestBody TaskDto taskDto,
                        @Valid @NotNull(message = "The event id cannot be null!") @RequestParam("assigned_event_id")
                        Long eventId,
                        @Valid @NotNull(message = "The event id cannot be null!")
                        @RequestParam("assigned_participant_id")
                        Long participantId,
                        @Valid
                        @NotNull(message = "The guest type cannot be null!")
                        @NotEmpty(message = "The guest type cannot be empty!")
                        @NotBlank(message = "The guest type cannot be blank!")
                        @RequestParam("task_progress")
                        String taskProgress) {
        return taskService.addTask(mapper.toEntity(taskDto), eventId, participantId, taskProgress);
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
                                                       @Positive(message = "The given event id cannot be 0 or less!")
                                                       long eventId) {
        return ResponseEntity.ok(mapper.toDtoCollection(taskService.getTasksByEventId(eventId)));
    }

    @GetMapping(value = "/search", params = {"participant_id"})
    public ResponseEntity<List<TaskDto>> findByParticipantId(@RequestParam("participant_id")
                                                             @Positive(message = "The given participant id cannot be 0 or less!")
                                                             long participantId) {
        return ResponseEntity.ok(mapper.toDtoCollection(taskService.getTasksByParticipantId(participantId)));
    }

    @GetMapping(value = "/search", params = {"created_by"})
    public ResponseEntity<List<TaskDto>> getTasksByCreatedBy(@NotNull(message = "The created by cannot be null!")
                                                             @NotEmpty(message = "The created by cannot be empty!")
                                                             @NotBlank(message = "The created by cannot be blank!")
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
        @NotNull(message = "The due date before cannot be null!")
        @NotEmpty(message = "The due date before cannot be empty!")
        @NotBlank(message = "The due date before cannot be blank!")
        @RequestParam("due_date_before")
        String dueDateBefore) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ResponseEntity.ok(
            mapper.toDtoCollection(taskService.getTaskByDueDateBetween(LocalDateTime.parse(dueDateAfter, formatter),
                LocalDateTime.parse(dueDateBefore, formatter))));
    }

    @GetMapping(value = "/search", params = {"due_date_before"})
    public ResponseEntity<List<TaskDto>> getTasksByDueDateBefore(
        @NotNull(message = "The due date before cannot be null!")
        @NotEmpty(message = "The due date before cannot be empty!")
        @NotBlank(message = "The due date before cannot be blank!")
        @RequestParam("due_date_before")
        String dueDateBefore) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ResponseEntity.ok(
            mapper.toDtoCollection(taskService.getTaskByDueDateBefore(LocalDateTime.parse(dueDateBefore, formatter))));
    }

//    @PatchMapping(value = "/settings", params = {"name", "id"})
//    public boolean updateName(@NotNull(message = "The task name cannot be null!")
//                              @NotEmpty(message = "The task name cannot be empty!")
//                              @NotBlank(message = "The task name cannot be blank!")
//                              @RequestParam("name")
//                              String name,
//                              @Positive(message = "The given ID cannot be less than zero!")
//                              long taskId) {
//        return taskService.updateName(name, taskId);
//    }
//
//    @PatchMapping(value = "/settings", params = {"description", "id"})
//    public boolean updateDescription(@NotNull(message = "The description cannot be null!")
//                                     @NotEmpty(message = "The description cannot be empty!")
//                                     @NotBlank(message = "The description cannot be blank!")
//                                     @RequestParam("description")
//                                     String description,
//                                     @Positive(message = "The given ID cannot be less than zero!")
//                                     @RequestParam("id")
//                                     long taskId) {
//        return taskService.updateDescription(description, taskId);
//    }
//
//    @PatchMapping(value = "/settings", params = {"task_progress", "id"})
//    public boolean updateTaskProgress(@NotNull(message = "The task progress cannot be null!")
//                                      @NotEmpty(message = "The task progress cannot be empty!")
//                                      @NotBlank(message = "The task progress cannot be blank!")
//                                      @RequestParam("task_progress")
//                                      String taskProgress,
//                                      @Positive(message = "The given ID cannot be less than zero!")
//                                      @RequestParam("id")
//                                      long taskId) {
//        return taskService.updateTaskProgress(TaskProgress.valueOf(taskProgress.toUpperCase()), taskId);
//    }
//
//    @PatchMapping(value = "/settings", params = {"due_date", "id"})
//    public boolean updateDueDate(@NotNull(message = "The due date cannot be null!")
//                                 @NotEmpty(message = "The due date cannot be empty!")
//                                 @NotBlank(message = "The due date cannot be blank!")
//                                 @RequestParam("due_date")
//                                 String dueDate,
//                                 @Positive(message = "The given ID cannot be less than zero!")
//                                 @RequestParam("id")
//                                 long taskId) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        return taskService.updateDueDate(LocalDateTime.parse(dueDate, formatter), taskId);
//    }
//
//    @PatchMapping(value = "/settings", params = {"last_notified", "id"})
//    public boolean updateLastNotified(@NotNull(message = "The last notified cannot be null!")
//                                      @NotEmpty(message = "The last notified cannot be empty!")
//                                      @NotBlank(message = "The last notified cannot be blank!")
//                                      @RequestParam("last_notified")
//                                      String lastNotified,
//                                      @Positive(message = "The given ID cannot be less than zero!")
//                                      @RequestParam("id")
//                                      long taskId) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        return taskService.updateLastNotified(LocalDateTime.parse(lastNotified, formatter), taskId);
//    }
//
//    @PatchMapping(value = "/settings", params = {"created_by", "id"})
//    public boolean updateCreatedBy(@RequestParam("created_by")
//                                   @NotNull(message = "The created by cannot be null!")
//                                   @NotEmpty(message = "The created by cannot be empty!")
//                                   @NotBlank(message = "The created by cannot be blank!")
//                                   String createdBy,
//                                   @Positive(message = "The given ID cannot be less than zero!")
//                                   @RequestParam("id")
//                                   long taskId) {
//        return taskService.updateCreatedBy(createdBy, taskId);
//    }
//
//    @PatchMapping(value = "/settings", params = {"creation_time", "id"})
//    public boolean updateCreationTime(@NotNull(message = "The creation time cannot be null!")
//                                      @NotEmpty(message = "The creation time cannot be empty!")
//                                      @NotBlank(message = "The creation time cannot be blank!")
//                                      @RequestParam("creation_time")
//                                      String creationTime,
//                                      @Positive(message = "The given ID cannot be less than zero!")
//                                      @RequestParam("id")
//                                      long taskId) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        return taskService.updateCreationTime(LocalDateTime.parse(creationTime, formatter), taskId);
//    }
//
//    @PatchMapping(value = "/settings", params = {"updated_by", "id"})
//    public boolean updateUpdatedBy(@NotNull(message = "The updated by cannot be null!")
//                                   @NotEmpty(message = "The updated by cannot be empty!")
//                                   @NotBlank(message = "The updated by cannot be blank!")
//                                   @RequestParam("updated_by")
//                                   String updatedBy,
//                                   @Positive(message = "The given ID cannot be less than zero!")
//                                   @RequestParam("id")
//                                   long taskId) {
//        return taskService.updateUpdatedBy(updatedBy, taskId);
//    }
//
//    @PatchMapping(value = "/settings", params = {"last_updated_time", "id"})
//    public boolean updateLastUpdatedTime(@NotNull(message = "The last_updated_time cannot be null!")
//                                         @NotEmpty(message = "The last_updated_time cannot be empty!")
//                                         @NotBlank(message = "The last_updated_time cannot be blank!")
//                                         @RequestParam("last_updated_time")
//                                         String lastUpdatedTime,
//                                         @Positive(message = "The given ID cannot be less than zero!")
//                                         @RequestParam("id")
//                                         long taskId) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        return taskService.updateLastUpdatedTime(LocalDateTime.parse(lastUpdatedTime, formatter), taskId);
//    }

    @DeleteMapping(value = "/delete", params = {"deleted", "id"})
    public boolean deleteTask(@RequestParam("deleted")
                              boolean deleted,
                              @Positive(message = "The given ID cannot be less than zero!")
                              @RequestParam("id")
                              long taskId) {
        return taskService.delete(deleted, taskId);
    }
}