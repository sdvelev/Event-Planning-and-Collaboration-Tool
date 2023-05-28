package bg.sofia.uni.fmi.web.project.dto;

import bg.sofia.uni.fmi.web.project.model.TaskProgress;
import bg.sofia.uni.fmi.web.project.stub.EventStub;
import bg.sofia.uni.fmi.web.project.stub.ParticipantStub;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @Positive(message = "The task id cannot be negative or zero!")
    private long id;

    @NotNull(message = "The name cannot be null!")
    @NotEmpty(message = "The name cannot be empty!")
    @NotBlank(message = "The name cannot be blank!")
    private String name;

    @NotNull(message = "The description cannot be null!")
    @NotEmpty(message = "The description cannot be empty!")
    @NotBlank(message = "The description cannot be blank!")
    private String description;

    @NotNull(message = "The task progress cannot be null")
    private TaskProgress taskProgress;

    @NotNull(message = "The due date cannot be null!")
    private LocalDateTime dueDate;

    private LocalDateTime lastNotified;

    // Event
    @NotNull(message = "The event ID cannot be null!")
    private EventStub event;

    // Participant
    @NotNull(message = "The participant ID cannot be null!")
    private ParticipantStub participant;

    @NotNull(message = "The created by cannot be null!")
    @NotEmpty(message = "The created by cannot be empty!")
    @NotBlank(message = "The created by cannot be blank!")
    private String createdBy;

    @NotNull(message = "The creation time cannot be null!")
    private LocalDateTime creationTime;

    private String updatedBy;

    private LocalDateTime lastUpdatedTime;

    private boolean deleted;
}