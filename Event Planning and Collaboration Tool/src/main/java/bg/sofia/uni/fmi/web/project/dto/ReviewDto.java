package bg.sofia.uni.fmi.web.project.dto;

import bg.sofia.uni.fmi.web.project.model.Vendor;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class ReviewDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("rating")
    @Positive(message = "The rating must be positive!")
    private BigDecimal rating;

    @JsonProperty("comment")
    @NotNull(message = "The comment cannot be null!")
    @NotEmpty(message = "The comment cannot be empty!")
    @NotBlank(message = "The comment cannot be blank!")
    private String comment;

    @JsonProperty("photo_link")
    @NotNull(message = "The photo link cannot be null!")
    @NotEmpty(message = "The photo link cannot be empty!")
    @NotBlank(message = "The photo link cannot be blank!")
    private String photoLink;

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
