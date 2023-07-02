package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.model.Participant;
import bg.sofia.uni.fmi.web.project.model.User;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;


@Service
@Validated
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
        @NotNull(message = "The provided username cannot be null")
        @NotBlank(message = "The provided username cannot be empty or blank")
        String username,
        @NotNull(message = "The provided password cannot be null")
        @NotBlank(message = "The provided password cannot be empty or blank")
        String password,
        @NotNull(message = "The user who makes changes cannot be null")
        User userToMakeChanges) {

        User userToDelete = userService.deleteUser(username, password, userToMakeChanges);

        for (Participant currentParticipant : userToDelete.getParticipantProfiles()) {
            if (!currentParticipant.isDeleted()) {
                participantService.deleteParticipant(currentParticipant.getId(), userToMakeChanges);
            }
        }

        return true;
    }

    public List<Participant> getParticipantsByUserId(
        @NotNull(message = "The provided user id cannot be null")
        @Positive(message = "The provided user id must be positive")
        Long userId) {

        Optional<User> associatedUser = userService.getUserById(userId);
        if (associatedUser.isPresent()) {
            return participantService.getParticipantsByUser(associatedUser.get());
        }

        throw new ResourceNotFoundException("User with such an id cannot be found");
    }
}
