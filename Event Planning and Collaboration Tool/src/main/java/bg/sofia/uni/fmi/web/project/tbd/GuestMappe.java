package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.GuestDto;
import bg.sofia.uni.fmi.web.project.model.Guest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GuestMappe {
    private final EventMapper eventMapper;

    public GuestDto toDto(Guest guestEntity) {
        if (guestEntity == null) {
            return null;
        }

        GuestDto newGuestDto = new GuestDto();
        newGuestDto.setId((guestEntity.getId()));
        newGuestDto.setName(guestEntity.getName());
        newGuestDto.setSurname(guestEntity.getSurname());
        newGuestDto.setEmail(guestEntity.getEmail());

        if (guestEntity.getGuestType() != null) {
            newGuestDto.setGuestType(guestEntity.getGuestType());
        }

        if (guestEntity.getAttendanceType() != null) {
            newGuestDto.setAttendanceType(guestEntity.getAttendanceType());
        }

        newGuestDto.setInvitationSent(guestEntity.isInvitationSent());

        if (guestEntity.getAssociatedEvent() != null) {
            newGuestDto.setAssociatedEventDto(eventMapper.toDto(guestEntity.getAssociatedEvent()));
        }

        return newGuestDto;
    }

    public Guest toEntity(GuestDto guestDto) {
        if (guestDto == null) {
            return null;
        }

        Guest newGuestEntity = new Guest();
        newGuestEntity.setId((guestDto.getId()));
        newGuestEntity.setName(guestDto.getName());
        newGuestEntity.setSurname(guestDto.getSurname());
        newGuestEntity.setEmail(guestDto.getEmail());

        if (guestDto.getGuestType() != null) {
            newGuestEntity.setGuestType(guestDto.getGuestType());
        }

        if (guestDto.getAttendanceType() != null) {
            newGuestEntity.setAttendanceType(guestDto.getAttendanceType());
        }

        newGuestEntity.setInvitationSent(guestDto.isInvitationSent());

        if (guestDto.getAssociatedEventDto() != null) {
            newGuestEntity.setAssociatedEvent(eventMapper.toEntity(guestDto.getAssociatedEventDto()));
        }

        return newGuestEntity;
    }

//    public List<GuestDto> toDtoCollection(Collection<Guest> guestsEntities) {
//        if (guestsEntities == null) {
//            return Collections.emptyList();
//        }
//
//        return guestsEntities.stream().map(this::toDto).toList();
//    }
//
//    public List<Guest> toEntityCollection(Collection<GuestDto> guestDtos) {
//        if (guestDtos == null) {
//            return Collections.emptyList();
//        }
//
//        return guestDtos.stream().map(this::toEntity).toList();
//    }
}