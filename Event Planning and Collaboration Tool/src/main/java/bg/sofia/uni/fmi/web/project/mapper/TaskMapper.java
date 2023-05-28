package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.TaskDto;
import bg.sofia.uni.fmi.web.project.model.Task;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class TaskMapper {
    public TaskDto toDto(Task taskEntity) {
        if (taskEntity == null) {
            return null;
        }

        return TaskDto.builder()
            .id(taskEntity.getId())
            .name(taskEntity.getName())
            .description(taskEntity.getDescription())
            .taskProgress(taskEntity.getTaskProgress())
            .dueDate(taskEntity.getDueDate())
            .lastNotified(taskEntity.getLastNotified())
            .event(taskEntity.getEvent())
            .participant(taskEntity.getParticipant())
            .createdBy(taskEntity.getCreatedBy())
            .creationTime(taskEntity.getCreationTime())
            .updatedBy(taskEntity.getUpdatedBy())
            .lastUpdatedTime(taskEntity.getLastUpdatedTime())
            .deleted(taskEntity.isDeleted())
            .build();
    }

    public Task toEntity(TaskDto taskDto) {
        if (taskDto == null) {
            return null;
        }

        return Task.builder()
            .id(taskDto.getId())
            .name(taskDto.getName())
            .description(taskDto.getDescription())
            .taskProgress(taskDto.getTaskProgress())
            .dueDate(taskDto.getDueDate())
            .lastNotified(taskDto.getLastNotified())
            .event(taskDto.getEvent())
            .participant(taskDto.getParticipant())
            .createdBy(taskDto.getCreatedBy())
            .creationTime(taskDto.getCreationTime())
            .updatedBy(taskDto.getUpdatedBy())
            .lastUpdatedTime(taskDto.getLastUpdatedTime())
            .deleted(taskDto.isDeleted())
            .build();
    }

    public Collection<TaskDto> toDtoCollection(Collection<Task> tasksEntities) {
        if (tasksEntities == null) {
            return Collections.emptyList();
        }

        return tasksEntities.stream()
            .map(this::toDto)
            .toList();
    }

    public Collection<Task> toEntityCollection(Collection<TaskDto> tasksDtos) {
        if (tasksDtos == null) {
            return Collections.emptyList();
        }

        return tasksDtos.stream()
            .map(this::toEntity)
            .toList();
    }
}