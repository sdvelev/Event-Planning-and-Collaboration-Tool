package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.dto.TaskDto;
import bg.sofia.uni.fmi.web.project.model.Task;
import bg.sofia.uni.fmi.web.project.repository.TaskRepository;
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
        return taskRepository.save(taskToSave).getId();
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        validateTasksList(tasks);

        return tasks;
    }

    public Task getTaskById(@Positive(message = "The given ID cannot be less than zero!") long id) {
        Task task = taskRepository.findTaskByIdEquals(id);
        validateTask(task);

        return task;
    }

    public List<Task> getTasksByName(@NotNull(message = "The name cannot be null!")
                                     @NotEmpty(message = "The name cannot be empty!")
                                     @NotBlank(message = "The name cannot be blank!")
                                     String name) {
        List<Task> tasks = taskRepository.findTasksByNameEquals(name);
        validateTasksList(tasks);

        return tasks;
    }

    public List<Task> getTasksByEventId(@Positive(message = "The given ID cannot be less than zero!")
                                        long eventId) {
        List<Task> tasks = taskRepository.findTasksByAssociatedEventIdEquals(eventId);
        validateTasksList(tasks);

        return tasks;
    }

    public List<Task> getTasksByParticipantId(@Positive(message = "The given ID cannot be less than zero!")
                                              long participantId) {
        List<Task> tasks = taskRepository.findTasksByAndParticipantIdEquals(participantId);
        validateTasksList(tasks);

        return tasks;
    }

    public List<Task> getTasksByCreatedBy(@NotNull(message = "The name cannot be null!")
                                          @NotEmpty(message = "The name cannot be empty!")
                                          @NotBlank(message = "The name cannot be blank!")
                                          String name) {
        List<Task> tasks = taskRepository.findTaskByCreatedByEquals(name);
        validateTasksList(tasks);

        return tasks;
    }

    public List<Task> getTaskByDueDateBetween(@NotNull(message = "The date after cannot be null!")
                                              LocalDateTime localDateTimeAfter,
                                              @NotNull(message = "The date before cannot be null!")
                                              LocalDateTime localDateTimeBefore) {
        List<Task> tasks =
            taskRepository.findTaskByDueDateBetween(localDateTimeAfter, localDateTimeBefore);

        validateTasksList(tasks);
        return tasks;
    }

    public boolean setTaskByTaskId(@Positive(message = "The task id must be positive!")
                                   long taskId,
                                   @NotNull(message = "The given task dto cannot be null!")
                                   TaskDto taskDto) {
        Task task = getTaskById(taskId);
        validateTask(task);

        Task newTaskToSave = updateFields(taskDto, task);
        newTaskToSave.setUpdatedBy("b");
        newTaskToSave.setLastUpdatedTime(LocalDateTime.now());

        taskRepository.save(newTaskToSave);

        return true;
    }

    public boolean delete(@Positive(message = "The given ID cannot be less than zero!") long taskId) {
        Task task = getTaskById(taskId);
        validateTask(task);

        task.setDeleted(true);
        taskRepository.save(task);
        return true;
    }

    private Task updateFields(TaskDto taskDto, Task newTaskToSave) {
        if (taskDto.getName() != null && !taskDto.getName().equals(newTaskToSave.getName())) {
            newTaskToSave.setName(taskDto.getName());
        }
        if (taskDto.getDescription() != null && !taskDto.getDescription().equals(newTaskToSave.getDescription())) {
            newTaskToSave.setDescription(taskDto.getDescription());
        }
        if (taskDto.getTaskProgress() != null && !taskDto.getTaskProgress().equals(newTaskToSave.getTaskProgress())) {
            newTaskToSave.setTaskProgress(taskDto.getTaskProgress());
        }
        if (taskDto.getDueDate() != null && !taskDto.getDueDate().equals(newTaskToSave.getDueDate())) {
            newTaskToSave.setDueDate(taskDto.getDueDate());
        }
        if (taskDto.getLastNotified() != null && !taskDto.getLastNotified().equals(newTaskToSave.getLastNotified())) {
            newTaskToSave.setLastNotified(taskDto.getLastNotified());
        }

        return newTaskToSave;
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
}