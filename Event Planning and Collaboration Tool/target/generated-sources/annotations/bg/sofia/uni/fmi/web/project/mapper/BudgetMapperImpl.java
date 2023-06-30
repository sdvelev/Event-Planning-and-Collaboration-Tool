package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.BudgetDto;
import bg.sofia.uni.fmi.web.project.model.Budget;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-30T14:00:29+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class BudgetMapperImpl implements BudgetMapper {

    @Autowired
    private EventMapper eventMapper;

    @Override
    public BudgetDto toDto(Budget model) {
        if ( model == null ) {
            return null;
        }

        BudgetDto.BudgetDtoBuilder budgetDto = BudgetDto.builder();

        budgetDto.associatedEventDto( eventMapper.toDto( model.getAssociatedEvent() ) );
        budgetDto.id( model.getId() );
        budgetDto.description( model.getDescription() );
        budgetDto.expenditureCategory( model.getExpenditureCategory() );
        budgetDto.amount( model.getAmount() );
        budgetDto.approved( model.isApproved() );
        budgetDto.createdBy( model.getCreatedBy() );
        budgetDto.creationTime( model.getCreationTime() );
        budgetDto.updatedBy( model.getUpdatedBy() );
        budgetDto.lastUpdatedTime( model.getLastUpdatedTime() );

        return budgetDto.build();
    }

    @Override
    public Budget toEntity(BudgetDto dto) {
        if ( dto == null ) {
            return null;
        }

        Budget.BudgetBuilder budget = Budget.builder();

        budget.associatedEvent( eventMapper.toEntity( dto.getAssociatedEventDto() ) );
        budget.id( dto.getId() );
        budget.description( dto.getDescription() );
        budget.expenditureCategory( dto.getExpenditureCategory() );
        budget.amount( dto.getAmount() );
        budget.approved( dto.isApproved() );
        budget.createdBy( dto.getCreatedBy() );
        budget.creationTime( dto.getCreationTime() );
        budget.updatedBy( dto.getUpdatedBy() );
        budget.lastUpdatedTime( dto.getLastUpdatedTime() );

        return budget.build();
    }

    @Override
    public List<BudgetDto> toDtoList(List<Budget> modelList) {
        if ( modelList == null ) {
            return null;
        }

        List<BudgetDto> list = new ArrayList<BudgetDto>( modelList.size() );
        for ( Budget budget : modelList ) {
            list.add( toDto( budget ) );
        }

        return list;
    }
}
