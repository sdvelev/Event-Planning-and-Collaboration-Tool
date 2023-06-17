package bg.sofia.uni.fmi.web.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("total_price")
    @Positive(message = "The total price must be positive!")
    private BigDecimal totalPrice;

    @JsonProperty("finished")
    private boolean finished;

    @JsonProperty("associated_event")
    private EventDto associatedEventDto;

    @JsonProperty("associated_vendor")
    private VendorDto associatedVendorDto;

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

    @JsonProperty("deleted")
    private boolean deleted;
}
