//package bg.sofia.uni.fmi.web.project.mapper;
//
//import bg.sofia.uni.fmi.web.project.dto.TaskDto;
//import bg.sofia.uni.fmi.web.project.model.Task;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//import java.util.Collection;
//import java.util.List;
//
//@Mapper
//public interface TaskMappe {
//    TaskMappe INSTANCE = Mappers.getMapper(TaskMappe.class);
//
//    @Mapping(source = "id", target = "id")
//    TaskDto toDto(Task task);
//
//    @Mapping(source = "id", target = "id")
//    Task toEntity(TaskDto taskDto);
//
//    @Mapping(source = "id", target = "id")
//    List<TaskDto> toDtoCollection(Collection<Task> tasksEntities);
////
////    List<Task> toEntityCollection(Collection<TaskDto> tasksDtos);
//}