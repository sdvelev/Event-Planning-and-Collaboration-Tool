package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.mapper.TaskMapper;
import bg.sofia.uni.fmi.web.project.model.Task;
import bg.sofia.uni.fmi.web.project.model.TaskProgress;
import bg.sofia.uni.fmi.web.project.stub.EventStub;
import bg.sofia.uni.fmi.web.project.stub.ParticipantStub;
import bg.sofia.uni.fmi.web.project.repository.TaskRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
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
        return taskRepository.save(task).getId();
    }

    public List<Long> addTasks(@NotNull(message = "The given task list cannot be null!") List<Task> taskList) {
        return taskRepository.saveAll(taskList).stream()
            .map(Task::getId)
            .toList();
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(@Positive(message = "The given ID cannot be less than zero!") long id) {
        return taskRepository.findTaskByIdEquals(id);
    }

    public Collection<Task> getTasksByName(@NotNull(message = "The name cannot be null!")
                                           @NotEmpty(message = "The name cannot be empty!")
                                           @NotBlank(message = "The name cannot be blank!")
                                           String name) {
        return taskRepository.findTasksByNameEquals(name);
    }

    public Collection<Task> getTasksByEventId(@Positive(message = "The given ID cannot be less than zero!")
                                              long eventId) {
        return taskRepository.findTasksByEventIdIs(eventId);
    }

    public Collection<Task> getTasksByParticipantId(@Positive(message = "The given ID cannot be less than zero!")
                                                    long participantId) {
        return taskRepository.findTasksByParticipantIdEquals(participantId);
    }

    public Collection<Task> getTasksByCreatedBy(@NotNull(message = "The name cannot be null!")
                                                @NotEmpty(message = "The name cannot be empty!")
                                                @NotBlank(message = "The name cannot be blank!")
                                                String name) {
        return taskRepository.findTaskByCreatedByEquals(name);
    }

    public Collection<Task> getTaskByDueDateAfter(LocalDateTime localDateTime) {
        return taskRepository.findTaskByDueDateAfter(localDateTime);
    }

    public Collection<Task> getTaskByDueDateBefore(LocalDateTime localDateTime) {
        return taskRepository.findTaskByDueDateBefore(localDateTime);
    }

    public Collection<Task> findTaskByDueDateBetween(LocalDateTime localDateTimeAfter,
                                                     LocalDateTime localDateTimeBefore) {
        return taskRepository.findTaskByDueDateBetween(localDateTimeAfter, localDateTimeBefore);
    }

    public void updateName(
        @NotNull(message = "The name cannot be null!")
        @NotEmpty(message = "The name cannot be empty!")
        @NotBlank(message = "The name cannot be blank!")
        String name,
        @Positive(message = "The given ID cannot be less than zero!") long taskId) {

        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setName(name);
        taskRepository.save(task);
    }

    public void updateDescription(@NotNull(message = "The description cannot be null!")
                                  @NotEmpty(message = "The description cannot be empty!")
                                  @NotBlank(message = "The description cannot be blank!")
                                  String description,
                                  @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setDescription(description);
        taskRepository.save(task);
    }

    public void updateTaskProgress(TaskProgress taskProgress,
                                   @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setTaskProgress(taskProgress);
        taskRepository.save(task);
    }

    public void updateDueDate(LocalDateTime dueDate,
                              @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setDueDate(dueDate);
        taskRepository.save(task);
    }

    public void updateLastNotified(LocalDateTime lastNotified,
                                   @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setLastNotified(lastNotified);
        taskRepository.save(task);
    }

    public void updateEvent(EventStub event,
                            @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setEvent(event);
        taskRepository.save(task);
    }

    public void updateParticipant(ParticipantStub participant,
                                  @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setParticipant(participant);
        taskRepository.save(task);
    }

    public void updateCreatedBy(@NotNull(message = "The createdBy cannot be null!")
                                @NotEmpty(message = "The createdBy cannot be empty!")
                                @NotBlank(message = "The createdBy cannot be blank!")
                                String createdBy,
                                @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setCreatedBy(createdBy);
        taskRepository.save(task);
    }

    public void updateCreationTime(LocalDateTime creationTime,
                                   @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setCreationTime(creationTime);
        taskRepository.save(task);
    }

    public void updateUpdatedBy(@NotNull(message = "The updatedBy cannot be null!")
                                @NotEmpty(message = "The updatedBy cannot be empty!")
                                @NotBlank(message = "The updatedBy cannot be blank!")
                                String updatedBy,
                                @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setUpdatedBy(updatedBy);
        taskRepository.save(task);
    }

    public void updateLastUpdatedTime(LocalDateTime lastUpdatedTime,
                                      @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setLastUpdatedTime(lastUpdatedTime);
        taskRepository.save(task);
    }

    public void delete(boolean deleted, @Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setDeleted(deleted);
        taskRepository.save(task);
    }
}