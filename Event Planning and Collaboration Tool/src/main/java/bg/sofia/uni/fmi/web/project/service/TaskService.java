package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.enums.TaskProgress;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Participant;
import bg.sofia.uni.fmi.web.project.model.Review;
import bg.sofia.uni.fmi.web.project.model.Task;
import bg.sofia.uni.fmi.web.project.repository.TaskRepository;
import bg.sofia.uni.fmi.web.project.validation.MethodNotAllowed;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
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

    public long addTask(@NotNull(message = "The given task cannot be null!") Task taskToSave) {
        Task task = taskRepository.save(taskToSave);
        checkForSaveException(task);

        return task.getId();
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = taskRepository.findAll().parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();

        validateTasksList(tasks);
        return tasks;
    }

    public Task getTaskById(@Positive(message = "The given ID cannot be less than zero!") long id) {
        Task task = taskRepository.findTaskByIdEquals(id);
        validateTask(task);
        validateForDeletedTask(task);

        return task;
    }

    public List<Task> getTasksByName(@NotNull(message = "The name cannot be null!")
                                     @NotEmpty(message = "The name cannot be empty!")
                                     @NotBlank(message = "The name cannot be blank!")
                                     String name) {
        List<Task> tasks = taskRepository.findTasksByNameEquals(name).parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();

        validateTasksList(tasks);
        return tasks;
    }

    public List<Task> getTasksByEventId(@Positive(message = "The given ID cannot be less than zero!")
                                        long eventId) {
        List<Task> tasks = taskRepository.findTasksByAssociatedEventIdEquals(eventId).parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();

        validateTasksList(tasks);
        return tasks;
    }

    public List<Task> getTasksByParticipantId(@Positive(message = "The given ID cannot be less than zero!")
                                              long participantId) {
        List<Task> tasks = taskRepository.findTasksByParticipantIdEquals(participantId).parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();

        validateTasksList(tasks);
        return tasks;
    }

    public List<Task> getTasksByCreatedBy(@NotNull(message = "The name cannot be null!")
                                          @NotEmpty(message = "The name cannot be empty!")
                                          @NotBlank(message = "The name cannot be blank!")
                                          String name) {
        List<Task> tasks = taskRepository.findTaskByCreatedByEquals(name).parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();

        validateTasksList(tasks);
        return tasks;
    }

    public List<Task> getTaskByDueDateAfter(@NotNull(message = "The date after cannot be null!")
                                            LocalDateTime localDateTimeAfter) {
        List<Task> tasks = taskRepository.findTaskByDueDateAfter(localDateTimeAfter).parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();

        validateTasksList(tasks);
        return tasks;
    }

    public List<Task> getTaskByDueDateBefore(@NotNull(message = "The date before cannot be null!")
                                             LocalDateTime localDateTimeBefore) {
        List<Task> tasks = taskRepository.findTaskByDueDateBefore(localDateTimeBefore).parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();

        validateTasksList(tasks);
        return tasks;
    }

    public List<Task> getTaskByDueDateBetween(@NotNull(message = "The date after cannot be null!")
                                              LocalDateTime localDateTimeAfter,
                                              @NotNull(message = "The date before cannot be null!")
                                              LocalDateTime localDateTimeBefore) {
        List<Task> tasks =
            taskRepository.findTaskByDueDateBetween(localDateTimeAfter, localDateTimeBefore).parallelStream()
                .filter(t -> !t.isDeleted())
                .toList();

        validateTasksList(tasks);
        return tasks;
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
                          @Positive(message = "The given ID cannot be less than zero!")
                          long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        validateTask(task);
        validateForDeletedTask(task);

        task.setDeleted(deleted);
        taskRepository.save(task);
        return true;
    }

    private void validateTask(Task task) {
        if (task == null) {
            throw new ResourceNotFoundException("There is no such task in the database!");
        }
    }

    private void validateTasksList(List<Task> tasks) {
        if (tasks == null) {
            throw new ResourceNotFoundException("There are no such tasks in the database or have been deleted!");
        }
    }

    private void checkForSaveException(Task task) {
        if (task == null) {
            throw new RuntimeException("There was problem while saving the task in the database!");
        }
    }

    private void validateForDeletedTask(Task task) {
        if (task.isDeleted()) {
            throw new MethodNotAllowed("The current record has already been deleted!");
        }
    }
}