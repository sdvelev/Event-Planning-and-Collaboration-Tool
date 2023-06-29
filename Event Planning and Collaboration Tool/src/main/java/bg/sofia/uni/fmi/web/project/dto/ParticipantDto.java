package bg.sofia.uni.fmi.web.project.dto;

import bg.sofia.uni.fmi.web.project.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantDto {

    @JsonProperty("id")
    private Long id;

    private UserRole userRole;

    @JsonProperty("user_role")
    public void setUserRole(String userRole) {
        if (userRole != null) {
            String uppercaseUserRole = userRole.toUpperCase();

            boolean isValidValue = Arrays.stream(UserRole.values())
                .map(Enum::name)
                .map(String::toUpperCase)
                .anyMatch(enumValue -> enumValue.equals(uppercaseUserRole));

            if (!isValidValue) {
                throw new IllegalArgumentException("The provided user role is not valid.");
            }

            this.userRole = UserRole.valueOf(uppercaseUserRole);
        }
    }

    @JsonProperty("associated_user")
    private UserDto associatedUserDto;

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
}