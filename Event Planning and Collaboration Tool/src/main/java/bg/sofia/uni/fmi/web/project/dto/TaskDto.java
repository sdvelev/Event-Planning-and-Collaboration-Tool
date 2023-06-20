package bg.sofia.uni.fmi.web.project.dto;

import bg.sofia.uni.fmi.web.project.enums.TaskProgress;
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
import java.util.Arrays;

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


    private TaskProgress taskProgress;

    @JsonProperty("task_progress")
    public void setTaskProgress(String taskProgress) {
        if (taskProgress != null) {
            String uppercaseTaskProgress = taskProgress.toUpperCase();

            boolean isValidValue = Arrays.stream(TaskProgress.values())
                .map(Enum::name)
                .map(String::toUpperCase)
                .anyMatch(enumValue -> enumValue.equals(uppercaseTaskProgress));

            if (!isValidValue) {
                throw new IllegalArgumentException("The provided task progress is not valid!");
            }

            this.taskProgress = TaskProgress.valueOf(uppercaseTaskProgress);
        }
    }

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