package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.ExpenseDto;
import bg.sofia.uni.fmi.web.project.model.Expense;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-30T18:19:39+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class ExpenseMapperImpl implements ExpenseMapper {

    @Autowired
    private EventMapper eventMapper;

    @Override
    public ExpenseDto toDto(Expense model) {
        if ( model == null ) {
            return null;
        }

        ExpenseDto.ExpenseDtoBuilder expenseDto = ExpenseDto.builder();

        expenseDto.associatedEventDto( eventMapper.toDto( model.getAssociatedEvent() ) );
        expenseDto.id( model.getId() );
        expenseDto.description( model.getDescription() );
        expenseDto.expenditureCategory( model.getExpenditureCategory() );
        expenseDto.amount( model.getAmount() );
        expenseDto.approved( model.isApproved() );
        expenseDto.createdBy( model.getCreatedBy() );
        expenseDto.creationTime( model.getCreationTime() );
        expenseDto.updatedBy( model.getUpdatedBy() );
        expenseDto.lastUpdatedTime( model.getLastUpdatedTime() );

        return expenseDto.build();
    }

    @Override
    public Expense toEntity(ExpenseDto dto) {
        if ( dto == null ) {
            return null;
        }

        Expense.ExpenseBuilder expense = Expense.builder();

        expense.associatedEvent( eventMapper.toEntity( dto.getAssociatedEventDto() ) );
        expense.id( dto.getId() );
        expense.description( dto.getDescription() );
        expense.expenditureCategory( dto.getExpenditureCategory() );
        expense.amount( dto.getAmount() );
        expense.approved( dto.isApproved() );
        expense.createdBy( dto.getCreatedBy() );
        expense.creationTime( dto.getCreationTime() );
        expense.updatedBy( dto.getUpdatedBy() );
        expense.lastUpdatedTime( dto.getLastUpdatedTime() );

        return expense.build();
    }

    @Override
    public List<ExpenseDto> toDtoList(List<Expense> modelList) {
        if ( modelList == null ) {
            return null;
        }

        List<ExpenseDto> list = new ArrayList<ExpenseDto>( modelList.size() );
        for ( Expense expense : modelList ) {
            list.add( toDto( expense ) );
        }

        return list;
    }
}
