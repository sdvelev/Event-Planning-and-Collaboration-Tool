package bg.sofia.uni.fmi.web.project.dto;

import bg.sofia.uni.fmi.web.project.enums.TaskProgress;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Participant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    @NotNull(message = "The name cannot be null!")
    @NotEmpty(message = "The name cannot be empty!")
    @NotBlank(message = "The name cannot be blank!")
    private String name;

    @JsonProperty("description")
    @NotNull(message = "The description cannot be null!")
    @NotEmpty(message = "The description cannot be empty!")
    @NotBlank(message = "The description cannot be blank!")
    private String description;

    @JsonProperty("task_progress")
    private TaskProgress taskProgress;

    @JsonProperty("due_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "The due date cannot be null!")
    private LocalDateTime dueDate;

    @JsonProperty("last_notified")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastNotified;

    @JsonProperty("associated_event")
    private EventDto associatedEventDto;

    @JsonProperty("associated_participant")
    private ParticipantDto associatedParticipantDto;

//    @NotNull(message = "The created by cannot be null!")
//    @NotEmpty(message = "The created by cannot be empty!")
//    @NotBlank(message = "The created by cannot be blank!")
//    private String createdBy;
//
//    @NotNull(message = "The creation time cannot be null!")
//    private LocalDateTime creationTime;
//
//    private String updatedBy;
//
//    private LocalDateTime lastUpdatedTime;
//
//    private boolean deleted;
}