package bg.sofia.uni.fmi.web.project.dto;

import bg.sofia.uni.fmi.web.project.enums.VendorType;
import bg.sofia.uni.fmi.web.project.model.Contract;
import bg.sofia.uni.fmi.web.project.model.Review;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    @NotNull(message = "The name cannot be null!")
    @NotEmpty(message = "The name cannot be empty!")
    @NotBlank(message = "The name cannot be blank!")
    private String name;

    @JsonProperty("address")
    @NotNull(message = "The address cannot be null!")
    @NotEmpty(message = "The address cannot be empty!")
    @NotBlank(message = "The address cannot be blank!")
    private String address;

    @JsonProperty("phone_number")
    @NotNull(message = "The phone number cannot be null!")
    @NotEmpty(message = "The phone number cannot be empty!")
    @NotBlank(message = "The phone number cannot be blank!")
    private String phoneNumber;

    @JsonProperty("email")
    @NotNull(message = "The email cannot be null!")
    @NotEmpty(message = "The email cannot be empty!")
    @NotBlank(message = "The email cannot be blank!")
    private String email;

    @JsonProperty("vendor_type")
    private VendorType vendorType;

    @JsonProperty("vendor_reviews")
    Set<ReviewDto> vendorReviewsDto;

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
