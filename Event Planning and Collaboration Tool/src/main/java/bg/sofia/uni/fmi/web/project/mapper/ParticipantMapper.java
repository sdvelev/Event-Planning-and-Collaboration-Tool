package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.BudgetDto;
import bg.sofia.uni.fmi.web.project.dto.ParticipantDto;
import bg.sofia.uni.fmi.web.project.model.Budget;
import bg.sofia.uni.fmi.web.project.model.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, EventMapper.class})
public interface ParticipantMapper {

    ParticipantMapper INSTANCE = Mappers.getMapper(ParticipantMapper.class);
    @Mapping(source = "associatedUser", target = "associatedUserDto")
    @Mapping(source = "associatedEvent", target = "associatedEventDto")
    ParticipantDto toDto (Participant model);
    @Mapping(source = "associatedUserDto", target = "associatedUser")
    @Mapping(source = "associatedEventDto", target = "associatedEvent")
    Participant toEntity(ParticipantDto dto);

    List<ParticipantDto> toDtoList(List<Participant> modelList);
}
