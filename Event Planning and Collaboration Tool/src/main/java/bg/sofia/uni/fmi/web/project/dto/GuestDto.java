package bg.sofia.uni.fmi.web.project.dto;

import bg.sofia.uni.fmi.web.project.enums.AttendanceType;
import bg.sofia.uni.fmi.web.project.enums.GuestType;
import bg.sofia.uni.fmi.web.project.model.Event;
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
public class GuestDto {

    @Positive(message = "The guest id cannot be negative or zero!")
    private long id;

    @NotNull(message = "The name cannot be null!")
    @NotEmpty(message = "The name cannot be empty!")
    @NotBlank(message = "The name cannot be blank!")
    private String name;

    @NotNull(message = "The surname cannot be null!")
    @NotEmpty(message = "The surname cannot be empty!")
    @NotBlank(message = "The surname cannot be blank!")
    private String surname;

    @NotNull(message = "The email cannot be null!")
    @NotEmpty(message = "The email cannot be empty!")
    @NotBlank(message = "The email cannot be blank!")
    private String email;

    @NotNull(message = "The guest type cannot be null!")
    private GuestType guestType;

    @NotNull(message = "The attendance type cannot be null!")
    private AttendanceType attendanceType;

    private boolean invitationSent;

    //Event
    @NotNull(message = "The event ID cannot be null!")
    private Event event;

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