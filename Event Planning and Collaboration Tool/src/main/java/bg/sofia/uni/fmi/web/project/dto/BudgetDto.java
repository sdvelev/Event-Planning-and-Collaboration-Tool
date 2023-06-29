package bg.sofia.uni.fmi.web.project.dto;

import bg.sofia.uni.fmi.web.project.enums.BudgetExpenditureCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    private BudgetExpenditureCategory expenditureCategory;

    @JsonProperty("expenditure_category")
    public void setExpenditureCategory(String expenditureCategory) {
        if (expenditureCategory != null) {
            String uppercaseExpenditureCategory = expenditureCategory.toUpperCase();

            boolean isValidValue = Arrays.stream(BudgetExpenditureCategory.values())
                .map(Enum::name)
                .map(String::toUpperCase)
                .anyMatch(enumValue -> enumValue.equals(uppercaseExpenditureCategory));

            if (!isValidValue) {
                throw new IllegalArgumentException("The provided expenditure category is not valid.");
            }

            this.expenditureCategory = BudgetExpenditureCategory.valueOf(uppercaseExpenditureCategory);
        }
    }

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("approved")
    private boolean approved;

    @JsonProperty("associated_event")
    private EventDto associatedEventDto;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("creation_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationTime;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("last_updated_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdatedTime;
}
