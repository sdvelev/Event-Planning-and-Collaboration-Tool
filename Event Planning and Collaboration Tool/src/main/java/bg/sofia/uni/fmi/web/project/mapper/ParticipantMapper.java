package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.ParticipantDto;
import bg.sofia.uni.fmi.web.project.model.Participant;
import org.springframework.stereotype.Component;

@Component
public class ParticipantMapper {

    public ParticipantDto toDto(Participant entity) {

        return ParticipantDto.builder()
            .id(entity.getId())
            .userRole(entity.getUserRole())
            .build();
    }

    public Participant toEntity(ParticipantDto participantDto) {

        return Participant.builder()
            .id(participantDto.getId())
            .userRole(participantDto.getUserRole())
            .build();
    }
}
