package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.EventDto;
import bg.sofia.uni.fmi.web.project.dto.ParticipantDto;
import bg.sofia.uni.fmi.web.project.model.Participant;
import bg.sofia.uni.fmi.web.project.model.User;
import org.springframework.stereotype.Component;

@Component
public class ParticipantMapper {

    public ParticipantDto toDto(Participant entity) {

        ParticipantDto toReturnDto = new ParticipantDto();

        if (entity.getAssociatedUser() != null) {
            toReturnDto.setAssociatedUserDto(new UserMapper().toDto(entity.getAssociatedUser()));
        }

        if (entity.getAssociatedEvent() != null) {
            toReturnDto.setAssociatedEventDto(new EventMapper().toDto(entity.getAssociatedEvent()));
        }

        toReturnDto.setId(entity.getId());
        toReturnDto.setUserRole(entity.getUserRole());

        return toReturnDto;
    }

    public Participant toEntity(ParticipantDto participantDto) {

        return Participant.builder()
            .id(participantDto.getId())
            .userRole(participantDto.getUserRole())
            .associatedUser(new UserMapper().toEntity(participantDto.getAssociatedUserDto()))
            .associatedEvent(new EventMapper().toEntity(participantDto.getAssociatedEventDto()))
            .build();
    }
}
