package bg.sofia.uni.fmi.web.project.controller;

import bg.sofia.uni.fmi.web.project.dto.TaskDto;
import bg.sofia.uni.fmi.web.project.mapper.TaskMapper;
import bg.sofia.uni.fmi.web.project.service.TaskFacadeService;
import bg.sofia.uni.fmi.web.project.service.TaskService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final TaskFacadeService taskFacadeService;
    private final TaskMapper mapper;

    @PostMapping(params = {"assigned_event_id", "assigned_participant_id"})
    public long addTask(@NotNull(message = "The guestDto cannot be null!") @RequestBody TaskDto taskDto,
                        @NotNull(message = "The event id cannot be null!")
                        @Positive(message = "The event id must be positive!")
                        @RequestParam("assigned_event_id")
                        Long eventId,
                        @NotNull(message = "The event id cannot be null!")
                        @Positive(message = "The participant id must be positive!")
                        @RequestParam("assigned_participant_id")
                        Long participantId) {
        return taskFacadeService.addTask(mapper.toEntity(taskDto), eventId, participantId);
    }

    @GetMapping
    public List<TaskDto> getAllTasks() {
        return mapper.toDtoCollection(taskService.getAllTasks());
    }

    @GetMapping(value = "/search", params = {"id"})
    public ResponseEntity<TaskDto> findById(@RequestParam("id")
                                            @Positive(message = "The event id must be positive!")
                                            long id) {
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

    @PutMapping(value = "/set", params = {"task_id"})
    public boolean setTaskByTaskId(@RequestParam("task_id")
                                   @Positive(message = "The task id must be positive!")
                                   long taskId,
                                   @RequestBody
                                   @NotNull(message = "The given task dto cannot be null!")
                                   TaskDto taskDto) {
        return taskService.setTaskByTaskId(taskId, taskDto);
    }

    @DeleteMapping(value = "/delete", params = {"id"})
    public boolean deleteTask(@Positive(message = "The given ID cannot be less than zero!")
                              @RequestParam("id")
                              long taskId) {
        return taskService.delete(taskId);
    }
}