package bg.sofia.uni.fmi.web.project.dto;

import bg.sofia.uni.fmi.web.project.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_role")
    private UserRole userRole;

    @JsonProperty("associated_user")
    private UserDto associatedUserDto;
}