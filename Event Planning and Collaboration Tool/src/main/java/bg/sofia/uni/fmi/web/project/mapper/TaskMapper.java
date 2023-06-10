package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.TaskDto;
import bg.sofia.uni.fmi.web.project.model.Task;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class TaskMapper {
    public TaskDto toDto(Task taskEntity) {
        if (taskEntity == null) {
            return null;
        }

        TaskDto newTaskDto = new TaskDto();
        newTaskDto.setId((taskEntity.getId()));
        newTaskDto.setName(taskEntity.getName());
        newTaskDto.setDescription(taskEntity.getDescription());

        if (taskEntity.getTaskProgress() != null) {
            newTaskDto.setTaskProgress(taskEntity.getTaskProgress());
        }

        if (taskEntity.getDueDate() != null) {
            newTaskDto.setDueDate(taskEntity.getDueDate());
        }

        if (taskEntity.getLastNotified() != null) {
            newTaskDto.setLastNotified(taskEntity.getLastNotified());
        }

        if (taskEntity.getEvent() != null) {
            newTaskDto.setAssociatedEventDto(new EventMapper().toDto(taskEntity.getEvent()));
        }

        if (taskEntity.getParticipant() != null) {
            newTaskDto.setAssociatedParticipantDto(
                new ParticipantMapper(new UserMapper(), new EventMapper())
                    .toDto(taskEntity.getParticipant()));
        }

        return newTaskDto;
    }

    public Task toEntity(TaskDto taskDto) {
        if (taskDto == null) {
            return null;
        }

        Task newTaskEntity = new Task();
        newTaskEntity.setId((taskDto.getId()));
        newTaskEntity.setName(taskDto.getName());
        newTaskEntity.setDescription(taskDto.getDescription());

        if (taskDto.getTaskProgress() != null) {
            newTaskEntity.setTaskProgress(taskDto.getTaskProgress());
        }

        if (taskDto.getDueDate() != null) {
            newTaskEntity.setDueDate(taskDto.getDueDate());
        }

        if (taskDto.getLastNotified() != null) {
            newTaskEntity.setLastNotified(taskDto.getLastNotified());
        }

        if (taskDto.getAssociatedEventDto() != null) {
            newTaskEntity.setEvent(new EventMapper().toEntity(taskDto.getAssociatedEventDto()));
        }

        if (taskDto.getAssociatedParticipantDto() != null) {
            newTaskEntity.setParticipant(
                new ParticipantMapper(new UserMapper(), new EventMapper())
                    .toEntity(taskDto.getAssociatedParticipantDto()));
        }

        return newTaskEntity;
    }

    public List<TaskDto> toDtoCollection(Collection<Task> tasksEntities) {
        if (tasksEntities == null) {
            return Collections.emptyList();
        }

        return tasksEntities.stream()
            .map(this::toDto)
            .toList();
    }

    public List<Task> toEntityCollection(Collection<TaskDto> tasksDtos) {
        if (tasksDtos == null) {
            return Collections.emptyList();
        }

        return tasksDtos.stream()
            .map(this::toEntity)
            .toList();
    }
}