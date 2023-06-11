package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.model.Participant;
import bg.sofia.uni.fmi.web.project.model.User;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserParticipantFacadeService {
    private final UserService userService;
    private final ParticipantService participantService;

    @Autowired
    public UserParticipantFacadeService(UserService userService, ParticipantService participantService) {
        this.userService = userService;
        this.participantService = participantService;
    }

    @Transactional
    public boolean deleteUserWithParticipants(
        @NotNull(message = "Username cannot be null")
        @NotBlank(message = "Username cannot be empty or blank")
        String username,
        @NotNull(message = "Password cannot be null")
        @NotBlank(message = "Password cannot be empty or blank")
        String password) {

        User userToDelete = userService.deleteUser(username, password);

        for (Participant currentParticipant : userToDelete.getParticipantProfiles()) {
            participantService.deleteParticipant(currentParticipant.getId());
        }

        return true;
    }
}
