package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.BudgetDto;
import bg.sofia.uni.fmi.web.project.dto.ExpenseDto;
import bg.sofia.uni.fmi.web.project.model.Budget;
import bg.sofia.uni.fmi.web.project.model.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface ExpenseMapper {

    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    @Mapping(source = "associatedEvent", target = "associatedEventDto")
    ExpenseDto toDto (Expense model);

    @Mapping(source = "associatedEventDto", target = "associatedEvent")
    Expense toEntity(ExpenseDto dto);

    List<ExpenseDto> toDtoList(List<Expense> modelList);

}
