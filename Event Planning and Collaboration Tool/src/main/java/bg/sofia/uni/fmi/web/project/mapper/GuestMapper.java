package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.GuestDto;
import bg.sofia.uni.fmi.web.project.model.Guest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface GuestMapper {

    GuestMapper INSTANCE = Mappers.getMapper(GuestMapper.class);

    @Mapping(source = "associatedEvent", target = "associatedEventDto")
    GuestDto toDto(Guest guestEntity);

    @Mapping(source = "associatedEventDto", target = "associatedEvent")
    Guest toEntity(GuestDto guestDto);

    List<GuestDto> toDtoCollection(Collection<Guest> guestsEntities);

    List<Guest> toEntityCollection(Collection<GuestDto> guestDtos);
}
