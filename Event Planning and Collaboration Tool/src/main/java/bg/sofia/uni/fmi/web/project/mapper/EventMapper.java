package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.EventDto;
import bg.sofia.uni.fmi.web.project.dto.ParticipantDto;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Participant;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public EventDto toDto(Event entity) {

        return EventDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .date(entity.getDate())
            .location(entity.getLocation())
            .description(entity.getDescription())
            .build();
    }

    public Event toEntity(EventDto eventDto) {

        return Event.builder()
            .id(eventDto.getId())
            .name(eventDto.getName())
            .date(eventDto.getDate())
            .location(eventDto.getLocation())
            .description(eventDto.getDescription())
            .build();
    }
}
