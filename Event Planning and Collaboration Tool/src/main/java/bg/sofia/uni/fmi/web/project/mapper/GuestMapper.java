package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.GuestDto;
import bg.sofia.uni.fmi.web.project.model.Guest;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class GuestMapper {
    public GuestDto toDto(Guest guestEntity) {
        if (guestEntity == null) {
            return null;
        }

        return GuestDto.builder()
            .id(guestEntity.getId())
            .name(guestEntity.getName())
            .surname(guestEntity.getSurname())
            .email(guestEntity.getEmail())
            .guestType(guestEntity.getGuestType())
            .attendanceType(guestEntity.getAttendanceType())
            .invitationSent(guestEntity.isInvitationSent())
            .event(guestEntity.getEvent())
            .createdBy(guestEntity.getCreatedBy())
            .creationTime(guestEntity.getCreationTime())
            .updatedBy(guestEntity.getUpdatedBy())
            .lastUpdatedTime(guestEntity.getLastUpdatedTime())
            .deleted(guestEntity.isDeleted())
            .build();
    }

    public Guest toEntity(GuestDto guestDto) {
        if (guestDto == null) {
            return null;
        }

        return Guest.builder()
            .id(guestDto.getId())
            .name(guestDto.getName())
            .surname(guestDto.getSurname())
            .email(guestDto.getEmail())
            .guestType(guestDto.getGuestType())
            .attendanceType(guestDto.getAttendanceType())
            .invitationSent(guestDto.isInvitationSent())
            .event(guestDto.getEvent())
            .createdBy(guestDto.getCreatedBy())
            .creationTime(guestDto.getCreationTime())
            .updatedBy(guestDto.getUpdatedBy())
            .lastUpdatedTime(guestDto.getLastUpdatedTime())
            .deleted(guestDto.isDeleted())
            .build();
    }

    public Collection<GuestDto> toDtoCollection(Collection<Guest> guestsEntities) {
        if (guestsEntities == null) {
            return Collections.emptyList();
        }

        return guestsEntities.stream()
            .map(this::toDto)
            .toList();
    }

    public Collection<Guest> toEntityCollection(Collection<GuestDto> guestDtos) {
        if (guestDtos == null) {
            return Collections.emptyList();
        }

        return guestDtos.stream()
            .map(this::toEntity)
            .toList();
    }
}