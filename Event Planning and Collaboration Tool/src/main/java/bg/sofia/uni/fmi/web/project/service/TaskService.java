package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.mapper.TaskMapper;
import bg.sofia.uni.fmi.web.project.model.Task;
import bg.sofia.uni.fmi.web.project.enums.TaskProgress;
import bg.sofia.uni.fmi.web.project.repository.TaskRepository;
import bg.sofia.uni.fmi.web.project.stub.EventStub;
import bg.sofia.uni.fmi.web.project.stub.ParticipantStub;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@Validated
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(@NotNull(message = "The tasks repository cannot be null!") TaskRepository taskRepository,
                       @NotNull(message = "The tasks mapper!") TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public long addTask(@NotNull(message = "The given task cannot be null!") Task task) {
        if (validateForExistingTask(task)) {
            return taskRepository.save(task).getId();
        }

        return -1;
    }

    private boolean validateForExistingTask(Task task) {
        long foundTasks = taskRepository.findTasksByNameEquals(task.getName()).stream()
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
        return taskRepository.findTaskByIdEquals(id);
    }

    public Collection<Task> getTasksByName(@NotNull(message = "The name cannot be null!")
                                           @NotEmpty(message = "The name cannot be empty!")
                                           @NotBlank(message = "The name cannot be blank!")
                                           String name) {
        return taskRepository.findTasksByNameEquals(name).parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();
    }

    public Collection<Task> getTasksByEventId(@Positive(message = "The given ID cannot be less than zero!")
                                              long eventId) {
        return taskRepository.findTasksByEventIdIs(eventId).parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();
    }

    public Collection<Task> getTasksByParticipantId(@Positive(message = "The given ID cannot be less than zero!")
                                                    long participantId) {
        return taskRepository.findTasksByParticipantIdEquals(participantId).parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();
    }

    public Collection<Task> getTasksByCreatedBy(@NotNull(message = "The name cannot be null!")
                                                @NotEmpty(message = "The name cannot be empty!")
                                                @NotBlank(message = "The name cannot be blank!")
                                                String name) {
        return taskRepository.findTaskByCreatedByEquals(name).parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();
    }

    public Collection<Task> getTaskByDueDateAfter(@NotNull(message = "The date after cannot be null!")
                                                  LocalDateTime localDateTimeAfter) {
        return taskRepository.findTaskByDueDateAfter(localDateTimeAfter).parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();
    }

    public Collection<Task> getTaskByDueDateBefore(@NotNull(message = "The date before cannot be null!")
                                                   LocalDateTime localDateTimeBefore) {
        return taskRepository.findTaskByDueDateBefore(localDateTimeBefore).parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();
    }

    public Collection<Task> findTaskByDueDateBetween(@NotNull(message = "The date after cannot be null!")
                                                     LocalDateTime localDateTimeAfter,
                                                     @NotNull(message = "The date before cannot be null!")
                                                     LocalDateTime localDateTimeBefore) {
        return taskRepository.findTaskByDueDateBetween(localDateTimeAfter, localDateTimeBefore).parallelStream()
            .filter(t -> !t.isDeleted())
            .toList();
    }

    public void updateName(@NotNull(message = "The name cannot be null!")
                           @NotEmpty(message = "The name cannot be empty!")
                           @NotBlank(message = "The name cannot be blank!")
                           String name,
                           @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);

        if (task != null && !task.isDeleted()) {
            task.setName(name);
            taskRepository.save(task);
        }
    }

    public void updateDescription(@NotNull(message = "The description cannot be null!")
                                  @NotEmpty(message = "The description cannot be empty!")
                                  @NotBlank(message = "The description cannot be blank!")
                                  String description,
                                  @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);

        if (task != null && !task.isDeleted()) {
            task.setDescription(description);
            taskRepository.save(task);
        }
    }

    public void updateTaskProgress(@NotNull(message = "The task progress cannot be null!") TaskProgress taskProgress,
                                   @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);

        if (task != null && !task.isDeleted()) {
            task.setTaskProgress(taskProgress);
            taskRepository.save(task);
        }
    }

    public void updateDueDate(@NotNull(message = "The due date cannot be null!") LocalDateTime dueDate,
                              @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);

        if (task != null && !task.isDeleted()) {
            task.setDueDate(dueDate);
            taskRepository.save(task);
        }
    }

    public void updateLastNotified(@NotNull(message = "The last notified date cannot be null!")
                                   LocalDateTime lastNotified,
                                   @Positive(message = "The given ID cannot be less than zero!")
                                   long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);

        if (task != null && !task.isDeleted()) {
            task.setLastNotified(lastNotified);
            taskRepository.save(task);
        }
    }

    public void updateEvent(@NotNull(message = "The event cannot be null!") EventStub event,
                            @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);

        if (task != null && !task.isDeleted()) {
            task.setEvent(event);
            taskRepository.save(task);
        }
    }

    public void updateParticipant(@NotNull(message = "The participant cannot be null!") ParticipantStub participant,
                                  @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);

        if (task != null && !task.isDeleted()) {
            task.setParticipant(participant);
            taskRepository.save(task);
        }
    }

    public void updateCreatedBy(@NotNull(message = "The createdBy cannot be null!")
                                @NotEmpty(message = "The createdBy cannot be empty!")
                                @NotBlank(message = "The createdBy cannot be blank!")
                                String createdBy,
                                @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);

        if (task != null && !task.isDeleted()) {
            task.setCreatedBy(createdBy);
            taskRepository.save(task);
        }
    }

    public void updateCreationTime(@NotNull(message = "The creation date cannot be null!") LocalDateTime creationTime,
                                   @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);

        if (task != null && !task.isDeleted()) {
            task.setCreationTime(creationTime);
            taskRepository.save(task);
        }
    }

    public void updateUpdatedBy(@NotNull(message = "The updatedBy cannot be null!")
                                @NotEmpty(message = "The updatedBy cannot be empty!")
                                @NotBlank(message = "The updatedBy cannot be blank!")
                                String updatedBy,
                                @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);

        if (task != null && !task.isDeleted()) {
            task.setUpdatedBy(updatedBy);
            taskRepository.save(task);
        }
    }

    public void updateLastUpdatedTime(@NotNull(message = "The last updated time cannot be null!")
                                      LocalDateTime lastUpdatedTime,
                                      @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);

        if (task != null && !task.isDeleted()) {
            task.setLastUpdatedTime(lastUpdatedTime);
            taskRepository.save(task);
        }
    }

    public void delete(boolean deleted,
                       @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);

        if (task != null && !task.isDeleted()) {
            task.setDeleted(deleted);
            taskRepository.save(task);
        }
    }
}