package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.BudgetDto;
import bg.sofia.uni.fmi.web.project.model.Budget;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface BudgetMapper {

    BudgetMapper INSTANCE = Mappers.getMapper(BudgetMapper.class);

    @Mapping(source = "associatedEvent", target = "associatedEventDto")
    BudgetDto toDto (Budget model);

    @Mapping(source = "associatedEventDto", target = "associatedEvent")
    Budget toEntity(BudgetDto dto);

    List<BudgetDto> toDtoList(List<Budget> modelList);
}
