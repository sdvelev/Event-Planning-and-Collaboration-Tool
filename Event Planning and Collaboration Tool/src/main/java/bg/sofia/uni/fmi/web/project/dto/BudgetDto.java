package bg.sofia.uni.fmi.web.project.dto;

import bg.sofia.uni.fmi.web.project.enums.ExpenditureCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BudgetDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("expenditure_category")
    private ExpenditureCategory expenditureCategory;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("approved")
    private boolean approved;

    @JsonProperty("associated_event")
    private EventDto associatedEventDto;
}
