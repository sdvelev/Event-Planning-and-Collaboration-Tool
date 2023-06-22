package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.TaskDto;
import bg.sofia.uni.fmi.web.project.model.Task;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-22T12:30:30+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private ParticipantMapper participantMapper;

    @Override
    public TaskDto toDto(Task taskEntity) {
        if ( taskEntity == null ) {
            return null;
        }

        TaskDto.TaskDtoBuilder taskDto = TaskDto.builder();

        taskDto.associatedEventDto( eventMapper.toDto( taskEntity.getAssociatedEvent() ) );
        taskDto.associatedParticipantDto( participantMapper.toDto( taskEntity.getParticipant() ) );
        taskDto.id( taskEntity.getId() );
        taskDto.name( taskEntity.getName() );
        taskDto.description( taskEntity.getDescription() );
        taskDto.taskProgress( taskEntity.getTaskProgress() );
        taskDto.dueDate( taskEntity.getDueDate() );
        taskDto.lastNotified( taskEntity.getLastNotified() );
        taskDto.createdBy( taskEntity.getCreatedBy() );
        taskDto.creationTime( taskEntity.getCreationTime() );
        taskDto.updatedBy( taskEntity.getUpdatedBy() );
        taskDto.lastUpdatedTime( taskEntity.getLastUpdatedTime() );
        taskDto.deleted( taskEntity.isDeleted() );

        return taskDto.build();
    }

    @Override
    public Task toEntity(TaskDto taskDto) {
        if ( taskDto == null ) {
            return null;
        }

        Task.TaskBuilder task = Task.builder();

        task.associatedEvent( eventMapper.toEntity( taskDto.getAssociatedEventDto() ) );
        task.participant( participantMapper.toEntity( taskDto.getAssociatedParticipantDto() ) );
        task.id( taskDto.getId() );
        task.name( taskDto.getName() );
        task.description( taskDto.getDescription() );
        task.taskProgress( taskDto.getTaskProgress() );
        task.dueDate( taskDto.getDueDate() );
        task.lastNotified( taskDto.getLastNotified() );
        task.createdBy( taskDto.getCreatedBy() );
        task.creationTime( taskDto.getCreationTime() );
        task.updatedBy( taskDto.getUpdatedBy() );
        task.lastUpdatedTime( taskDto.getLastUpdatedTime() );
        task.deleted( taskDto.isDeleted() );

        return task.build();
    }

    @Override
    public List<TaskDto> toDtoCollection(Collection<Task> tasksEntities) {
        if ( tasksEntities == null ) {
            return null;
        }

        List<TaskDto> list = new ArrayList<TaskDto>( tasksEntities.size() );
        for ( Task task : tasksEntities ) {
            list.add( toDto( task ) );
        }

        return list;
    }

    @Override
    public List<Task> toEntityCollection(Collection<TaskDto> tasksDtos) {
        if ( tasksDtos == null ) {
            return null;
        }

        List<Task> list = new ArrayList<Task>( tasksDtos.size() );
        for ( TaskDto taskDto : tasksDtos ) {
            list.add( toEntity( taskDto ) );
        }

        return list;
    }
}
