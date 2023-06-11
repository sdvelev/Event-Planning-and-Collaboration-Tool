package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.EventDto;
import bg.sofia.uni.fmi.web.project.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    EventDto toDto (Event model);

    Event toEntity(EventDto dto);

//    public EventDto toDto(Event entity) {
//
//        return EventDto.builder()
//            .id(entity.getId())
//            .name(entity.getName())
//            .date(entity.getDate())
//            .location(entity.getLocation())
//            .description(entity.getDescription())
//            .build();
//    }
//
//    public Event toEntity(EventDto eventDto) {
//
//        return Event.builder()
//            .id(eventDto.getId())
//            .name(eventDto.getName())
//            .date(eventDto.getDate())
//            .location(eventDto.getLocation())
//            .description(eventDto.getDescription())
//            .build();
//    }
}
