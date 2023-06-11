package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.enums.TaskProgress;
import bg.sofia.uni.fmi.web.project.mapper.TaskMapper;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Participant;
import bg.sofia.uni.fmi.web.project.model.Task;
import bg.sofia.uni.fmi.web.project.repository.TaskRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Validated
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final EventService eventService;
    private final ParticipantService participantService;
    private final TaskMapper taskMapper;

    public long addTask(@NotNull(message = "The given task cannot be null!") Task taskToSave,
                        @NotNull(message = "The event id cannot be null!")
                        Long eventId,
                        @NotNull(message = "The event id cannot be null!")
                        Long participantId,
                        @NotNull(message = "The guest type cannot be null!")
                        @NotEmpty(message = "The guest type cannot be empty!")
                        @NotBlank(message = "The guest type cannot be blank!")
                        String taskProgress) {
//        if (!validateForExistingTaskByName(task) || !validateForExistingTaskByEventId(task)) {
//            throw new ApiBadRequest("There is already a task with the same credentials");
//        }

        TaskProgress newTaskProgress = TaskProgress.valueOf(taskProgress.toUpperCase());
        Event event = eventService.getEventById(eventId);
        Participant participant = participantService.getParticipantById(participantId).get();


        taskToSave.setTaskProgress(newTaskProgress);
        taskToSave.setEvent(event);
        taskToSave.setParticipant(participant);
        taskToSave.setCreatedBy("a");
        taskToSave.setCreationTime(LocalDateTime.now());
        taskToSave.setDeleted(false);
        return taskRepository.save(taskToSave).getId();
    }

    private boolean validateForExistingTaskByName(Task task) {
        long foundTasks = taskRepository.findTasksByNameEquals(task.getName()).stream()
            .filter(t -> t.equals(task))
            .count();

        return foundTasks == 0;
    }

    private boolean validateForExistingTaskByEventId(Task task) {
        long foundTasks = taskRepository.findTasksByEventIdIs(task.getEvent().getId()).stream()
            .filter(t -> t.equals(task))
            .count();

        return foundTasks == 0;
    }

//    public List<Long> addTasks(@NotNull(message = "The given task list cannot be null!") List<Task> taskList) {
//        return taskRepository.saveAll(taskList).stream()
//            .map(Task::getId)
//            .toList();
//    }


    public List<Task> getAllTasks() {
        return taskRepository.findAll().parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();
    }

    public Task getTaskById(@Positive(message = "The given ID cannot be less than zero!") long id) {
        Task task = taskRepository.findTaskByIdEquals(id);

        if (task != null && !task.isDeleted()) {
            return task;
        }

        return null;
    }

    public List<Task> getTasksByName(@NotNull(message = "The name cannot be null!")
                                     @NotEmpty(message = "The name cannot be empty!")
                                     @NotBlank(message = "The name cannot be blank!")
                                     String name) {
        return taskRepository.findTasksByNameEquals(name).parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();
    }

    public List<Task> getTasksByEventId(@Positive(message = "The given ID cannot be less than zero!")
                                        long eventId) {
        return taskRepository.findTasksByEventIdIs(eventId).parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();
    }

    public List<Task> getTasksByParticipantId(@Positive(message = "The given ID cannot be less than zero!")
                                              long participantId) {
        return taskRepository.findTasksByParticipantIdEquals(participantId).parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();
    }

    public List<Task> getTasksByCreatedBy(@NotNull(message = "The name cannot be null!")
                                          @NotEmpty(message = "The name cannot be empty!")
                                          @NotBlank(message = "The name cannot be blank!")
                                          String name) {
        return taskRepository.findTaskByCreatedByEquals(name).parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();
    }

    public List<Task> getTaskByDueDateAfter(@NotNull(message = "The date after cannot be null!")
                                            LocalDateTime localDateTimeAfter) {
        return taskRepository.findTaskByDueDateAfter(localDateTimeAfter).parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();
    }

    public List<Task> getTaskByDueDateBefore(@NotNull(message = "The date before cannot be null!")
                                             LocalDateTime localDateTimeBefore) {
        return taskRepository.findTaskByDueDateBefore(localDateTimeBefore).parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();
    }

    public List<Task> getTaskByDueDateBetween(@NotNull(message = "The date after cannot be null!")
                                              LocalDateTime localDateTimeAfter,
                                              @NotNull(message = "The date before cannot be null!")
                                              LocalDateTime localDateTimeBefore) {
        return taskRepository.findTaskByDueDateBetween(localDateTimeAfter, localDateTimeBefore).parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();
    }

//    public boolean updateName(@NotNull(message = "The name cannot be null!")
//                              @NotEmpty(message = "The name cannot be empty!")
//                              @NotBlank(message = "The name cannot be blank!")
//                              String name,
//                              @Positive(message = "The given ID cannot be less than zero!")
//                              long taskId) {
//        Task task = taskRepository.findTaskByIdEquals(taskId);
//
//        if (task != null && !task.isDeleted()) {
//            task.setName(name);
//            taskRepository.save(task);
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean updateDescription(@NotNull(message = "The description cannot be null!")
//                                     @NotEmpty(message = "The description cannot be empty!")
//                                     @NotBlank(message = "The description cannot be blank!")
//                                     String description,
//                                     @Positive(message = "The given ID cannot be less than zero!")
//                                     long taskId) {
//        Task task = taskRepository.findTaskByIdEquals(taskId);
//
//        if (task != null && !task.isDeleted()) {
//            task.setDescription(description);
//            taskRepository.save(task);
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean updateTaskProgress(@NotNull(message = "The task progress cannot be null!") TaskProgress taskProgress,
//                                      @Positive(message = "The given ID cannot be less than zero!") long taskId) {
//        Task task = taskRepository.findTaskByIdEquals(taskId);
//
//        if (task != null && !task.isDeleted()) {
//            task.setTaskProgress(taskProgress);
//            taskRepository.save(task);
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean updateDueDate(@NotNull(message = "The due date cannot be null!") LocalDateTime dueDate,
//                                 @Positive(message = "The given ID cannot be less than zero!") long taskId) {
//        Task task = taskRepository.findTaskByIdEquals(taskId);
//
//        if (task != null && !task.isDeleted()) {
//            task.setDueDate(dueDate);
//            taskRepository.save(task);
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean updateLastNotified(@NotNull(message = "The last notified date cannot be null!")
//                                      LocalDateTime lastNotified,
//                                      @Positive(message = "The given ID cannot be less than zero!")
//                                      long taskId) {
//        Task task = taskRepository.findTaskByIdEquals(taskId);
//
//        if (task != null && !task.isDeleted()) {
//            task.setLastNotified(lastNotified);
//            taskRepository.save(task);
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean updateCreatedBy(@NotNull(message = "The createdBy cannot be null!")
//                                   @NotEmpty(message = "The createdBy cannot be empty!")
//                                   @NotBlank(message = "The createdBy cannot be blank!")
//                                   String createdBy,
//                                   @Positive(message = "The given ID cannot be less than zero!")
//                                   long taskId) {
//        Task task = taskRepository.findTaskByIdEquals(taskId);
//
//        if (task != null && !task.isDeleted()) {
//            task.setCreatedBy(createdBy);
//            taskRepository.save(task);
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean updateCreationTime(
//        @NotNull(message = "The creation date cannot be null!") LocalDateTime creationTime,
//        @Positive(message = "The given ID cannot be less than zero!") long taskId) {
//        Task task = taskRepository.findTaskByIdEquals(taskId);
//
//        if (task != null && !task.isDeleted()) {
//            task.setCreationTime(creationTime);
//            taskRepository.save(task);
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean updateUpdatedBy(@NotNull(message = "The updatedBy cannot be null!")
//                                   @NotEmpty(message = "The updatedBy cannot be empty!")
//                                   @NotBlank(message = "The updatedBy cannot be blank!")
//                                   String updatedBy,
//                                   @Positive(message = "The given ID cannot be less than zero!")
//                                   long taskId) {
//        Task task = taskRepository.findTaskByIdEquals(taskId);
//
//        if (task != null && !task.isDeleted()) {
//            task.setUpdatedBy(updatedBy);
//            taskRepository.save(task);
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean updateLastUpdatedTime(@NotNull(message = "The last updated time cannot be null!")
//                                         LocalDateTime lastUpdatedTime,
//                                         @Positive(message = "The given ID cannot be less than zero!")
//                                         long taskId) {
//        Task task = taskRepository.findTaskByIdEquals(taskId);
//
//        if (task != null && !task.isDeleted()) {
//            task.setLastUpdatedTime(lastUpdatedTime);
//            taskRepository.save(task);
//            return true;
//        }
//
//        return false;
//    }

    public boolean delete(boolean deleted,
                          @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);

        if (task != null && !task.isDeleted()) {
            task.setDeleted(deleted);
            taskRepository.save(task);
            return true;
        }

        return false;
    }
}