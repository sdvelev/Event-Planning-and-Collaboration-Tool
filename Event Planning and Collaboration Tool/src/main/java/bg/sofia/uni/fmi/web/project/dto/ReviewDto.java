package bg.sofia.uni.fmi.web.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
