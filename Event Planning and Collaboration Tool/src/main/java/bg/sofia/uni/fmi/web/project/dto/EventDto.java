package bg.sofia.uni.fmi.web.project.dto;

import bg.sofia.uni.fmi.web.project.model.Participant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    @NotNull
    private String name;

    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @JsonProperty("location")
    private String location;

    @JsonProperty("description")
    private String description;
}