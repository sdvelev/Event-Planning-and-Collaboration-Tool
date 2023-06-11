package bg.sofia.uni.fmi.web.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

//    @Column(length = 255, nullable = false)
//    private String createdBy;
//
//    @Column(nullable = false)
//    private LocalDateTime creationTime;
//
//    @Column(length = 255)
//    private String updatedBy;
//
//    @Column
//    private LocalDateTime lastUpdatedTime;
//
//    @Column(columnDefinition = "boolean default false", nullable = false)
//    private boolean deleted;
}
