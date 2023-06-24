package bg.sofia.uni.fmi.web.project.dto;

import bg.sofia.uni.fmi.web.project.enums.ExpenditureCategory;
import bg.sofia.uni.fmi.web.project.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BudgetDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("description")
    private String description;

    private ExpenditureCategory expenditureCategory;

    @JsonProperty("expenditure_category")
    public void setExpenditureCategory(String expenditureCategory) {
        if (expenditureCategory != null) {
            String uppercaseExpenditureCategory = expenditureCategory.toUpperCase();

            boolean isValidValue = Arrays.stream(ExpenditureCategory.values())
                .map(Enum::name)
                .map(String::toUpperCase)
                .anyMatch(enumValue -> enumValue.equals(uppercaseExpenditureCategory));

            if (!isValidValue) {
                throw new IllegalArgumentException("The provided expenditure category is not valid.");
            }

            this.expenditureCategory = ExpenditureCategory.valueOf(uppercaseExpenditureCategory);
        }
    }

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("approved")
    private boolean approved;

    @JsonProperty("associated_event")
    private EventDto associatedEventDto;
}
