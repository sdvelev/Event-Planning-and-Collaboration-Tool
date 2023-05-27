package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.mapper.TaskMapper;
import bg.sofia.uni.fmi.web.project.model.Task;
import bg.sofia.uni.fmi.web.project.model.TaskProgress;
import bg.sofia.uni.fmi.web.project.model.stub.EventStub;
import bg.sofia.uni.fmi.web.project.model.stub.ParticipantStub;
import bg.sofia.uni.fmi.web.project.repository.TaskRepository;
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
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public long addTask(Task task) {
        return taskRepository.save(task).getId();
    }

    public List<Long> addTasks(List<Task> taskList) {
        return taskRepository.saveAll(taskList).stream()
            .map(Task::getId)
            .toList();
    }

    public Task getTaskById(long id) {
        return taskRepository.findTaskByIdEquals(id);
    }

    public Collection<Task> getTasksByName(String name) {
        return taskRepository.findTasksByNameEquals(name);
    }

    public Collection<Task> getTasksByEventId(long eventId) {
        return taskRepository.findTasksByEventIdIs(eventId);
    }

    public Collection<Task> getTasksByParticipantId(long participantId) {
        return taskRepository.findTasksByParticipantIdEquals(participantId);
    }

    public void updateName(String name, long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setName(name);
        taskRepository.save(task);
    }

    public void updateDescription(String description, long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setDescription(description);
        taskRepository.save(task);
    }

    public void updateTaskProgress(TaskProgress taskProgress, long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setTaskProgress(taskProgress);
        taskRepository.save(task);
    }

    public void updateDueDate(LocalDateTime dueDate, long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setDueDate(dueDate);
        taskRepository.save(task);
    }

    public void updateLastNotified(LocalDateTime lastNotified, long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setLastNotified(lastNotified);
        taskRepository.save(task);
    }

    public void updateEvent(EventStub event, long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setEvent(event);
        taskRepository.save(task);
    }

    public void updateParticipant(ParticipantStub participant, long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setParticipant(participant);
        taskRepository.save(task);
    }

    public void updateCreatedBy(String createdBy, long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setCreatedBy(createdBy);
        taskRepository.save(task);
    }

    public void updateCreationTime(LocalDateTime creationTime, long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setCreationTime(creationTime);
        taskRepository.save(task);
    }

    public void updateUpdatedBy(String updatedBy, long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setUpdatedBy(updatedBy);
        taskRepository.save(task);
    }

    public void updateLastUpdatedTime(LocalDateTime lastUpdatedTime, long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setLastUpdatedTime(lastUpdatedTime);
        taskRepository.save(task);
    }

    public void delete(boolean deleted, long taskId) {
        Task task = taskRepository.findTaskByIdEquals(taskId);
        task.setDeleted(deleted);
        taskRepository.save(task);
    }
}