package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.TaskDto;
import bg.sofia.uni.fmi.web.project.model.Task;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(source = "associatedEvent", target = "associatedEventDto")
    TaskDto toDto(Task taskEntity);

    @Mapping(source = "associatedEventDto", target = "associatedEvent")
    Task toEntity(TaskDto taskDto);

    default List<TaskDto> toDtoCollection(Collection<Task> tasksEntities) {
        if (tasksEntities == null) {
            return Collections.emptyList();
        }

        return tasksEntities.stream().map(this::toDto).toList();
    }

    default List<Task> toEntityCollection(Collection<TaskDto> tasksDtos) {
        if (tasksDtos == null) {
            return Collections.emptyList();
        }

        return tasksDtos.stream().map(this::toEntity).toList();
    }
}