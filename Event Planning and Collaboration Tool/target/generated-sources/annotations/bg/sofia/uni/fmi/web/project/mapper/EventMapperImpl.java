package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.EventDto;
import bg.sofia.uni.fmi.web.project.model.Event;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-29T16:29:30+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19 (Oracle Corporation)"
)
@Component
public class EventMapperImpl implements EventMapper {

    @Override
    public EventDto toDto(Event model) {
        if ( model == null ) {
            return null;
        }

        EventDto.EventDtoBuilder eventDto = EventDto.builder();

        eventDto.id( model.getId() );
        eventDto.name( model.getName() );
        eventDto.date( model.getDate() );
        eventDto.location( model.getLocation() );
        eventDto.description( model.getDescription() );
        eventDto.pictureLink( model.getPictureLink() );
        eventDto.createdBy( model.getCreatedBy() );
        eventDto.creationTime( model.getCreationTime() );
        eventDto.updatedBy( model.getUpdatedBy() );
        eventDto.lastUpdatedTime( model.getLastUpdatedTime() );

        return eventDto.build();
    }

    @Override
    public Event toEntity(EventDto dto) {
        if ( dto == null ) {
            return null;
        }

        Event.EventBuilder event = Event.builder();

        event.id( dto.getId() );
        event.name( dto.getName() );
        event.date( dto.getDate() );
        event.location( dto.getLocation() );
        event.description( dto.getDescription() );
        event.pictureLink( dto.getPictureLink() );
        event.createdBy( dto.getCreatedBy() );
        event.creationTime( dto.getCreationTime() );
        event.updatedBy( dto.getUpdatedBy() );
        event.lastUpdatedTime( dto.getLastUpdatedTime() );

        return event.build();
    }

    @Override
    public List<EventDto> toDtoList(List<Event> modelList) {
        if ( modelList == null ) {
            return null;
        }

        List<EventDto> list = new ArrayList<EventDto>( modelList.size() );
        for ( Event event : modelList ) {
            list.add( toDto( event ) );
        }

        return list;
    }
}
