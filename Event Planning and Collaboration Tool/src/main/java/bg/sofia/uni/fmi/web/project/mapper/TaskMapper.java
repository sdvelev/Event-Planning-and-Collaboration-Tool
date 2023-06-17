package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.TaskDto;
import bg.sofia.uni.fmi.web.project.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", uses = {EventMapper.class, ParticipantMapper.class})
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(source = "associatedEvent", target = "associatedEventDto")
    @Mapping(source = "participant", target = "associatedParticipantDto")
    TaskDto toDto(Task taskEntity);

    @Mapping(source = "associatedEventDto", target = "associatedEvent")
    @Mapping(source = "associatedParticipantDto", target = "participant")
    Task toEntity(TaskDto taskDto);

    List<TaskDto> toDtoCollection(Collection<Task> tasksEntities);

    List<Task> toEntityCollection(Collection<TaskDto> tasksDtos);
}