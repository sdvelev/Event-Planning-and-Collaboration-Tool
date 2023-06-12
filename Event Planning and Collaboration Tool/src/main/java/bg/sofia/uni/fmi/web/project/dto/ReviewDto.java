package bg.sofia.uni.fmi.web.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
