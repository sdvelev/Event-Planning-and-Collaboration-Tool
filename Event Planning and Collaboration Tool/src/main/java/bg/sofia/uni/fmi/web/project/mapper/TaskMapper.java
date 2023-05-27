package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.TaskDto;
import bg.sofia.uni.fmi.web.project.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskDto toDto(Task taskEntity) {
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
}