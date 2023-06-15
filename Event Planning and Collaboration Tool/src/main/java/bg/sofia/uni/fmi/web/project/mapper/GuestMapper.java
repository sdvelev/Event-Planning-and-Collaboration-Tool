package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.GuestDto;
import bg.sofia.uni.fmi.web.project.model.Guest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
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

    default List<GuestDto> toDtoCollection(Collection<Guest> guestsEntities) {
        if (guestsEntities == null) {
            return Collections.emptyList();
        }

        return guestsEntities.stream().map(this::toDto).toList();
    }

    default List<Guest> toEntityCollection(Collection<GuestDto> guestDtos) {
        if (guestDtos == null) {
            return Collections.emptyList();
        }

        return guestDtos.stream().map(this::toEntity).toList();
    }
}
