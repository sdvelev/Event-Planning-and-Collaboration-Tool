package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Participant;
import bg.sofia.uni.fmi.web.project.model.User;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParticipantUserEventFacadeService {

    private final ParticipantService participantService;
    private final UserService userService;
    private final EventService eventService;

    @Autowired
    public ParticipantUserEventFacadeService(ParticipantService participantService, UserService userService,
                                             EventService eventService) {
        this.participantService = participantService;
        this.userService = userService;
        this.eventService = eventService;
    }

    @Transactional
    public Participant createParticipantWithUserAndEvent(
        @NotNull(message = "Participant cannot be null")
        Participant participantToSave,
        @NotNull(message = "Associated userId cannot be null")
        @Positive(message = "Associated userId must be positive.")
        Long userIdToAssociate,
        @NotNull(message = "Associated event Id cannot be null")
        @Positive(message = "Associated event Id must be positive.")
        Long eventIdToAssociate) {

        Optional<User> potentialUserToAssociate = userService.getUserById(userIdToAssociate);
        Optional<Event> potentialEventToAssociate = eventService.getEventById(eventIdToAssociate);

        if (potentialUserToAssociate.isPresent() && !potentialUserToAssociate.get().isDeleted() &&
            potentialEventToAssociate.isPresent() && !potentialEventToAssociate.get().isDeleted()) {

            participantToSave.setAssociatedUser(potentialUserToAssociate.get());
            participantToSave.setAssociatedEvent(potentialEventToAssociate.get());
            participantToSave.setCreatedBy(potentialUserToAssociate.get().getEmail());

            participantService.createParticipant(participantToSave);

            potentialUserToAssociate.get().getParticipantProfiles().add(participantToSave);
            potentialEventToAssociate.get().getAssociatedParticipants().add(participantToSave);
        }
        return participantToSave;
    }
}
