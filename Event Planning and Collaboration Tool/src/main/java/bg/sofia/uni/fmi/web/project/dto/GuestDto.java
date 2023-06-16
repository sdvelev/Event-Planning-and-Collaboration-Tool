package bg.sofia.uni.fmi.web.project.dto;

import bg.sofia.uni.fmi.web.project.enums.AttendanceType;
import bg.sofia.uni.fmi.web.project.enums.GuestType;
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
public class GuestDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    @NotNull(message = "The name cannot be null!")
    @NotEmpty(message = "The name cannot be empty!")
    @NotBlank(message = "The name cannot be blank!")
    private String name;

    @JsonProperty("surname")
    @NotNull(message = "The surname cannot be null!")
    @NotEmpty(message = "The surname cannot be empty!")
    @NotBlank(message = "The surname cannot be blank!")
    private String surname;

    @JsonProperty("email")
    @NotNull(message = "The email cannot be null!")
    @NotEmpty(message = "The email cannot be empty!")
    @NotBlank(message = "The email cannot be blank!")
    private String email;

    @JsonProperty("guest_type")
    private GuestType guestType;

    @JsonProperty("attendance_type")
    private AttendanceType attendanceType;

    @JsonProperty("invitation_sent")
    private boolean invitationSent;

    @JsonProperty("associated_event")
    private EventDto associatedEventDto;

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

    private boolean deleted;
}