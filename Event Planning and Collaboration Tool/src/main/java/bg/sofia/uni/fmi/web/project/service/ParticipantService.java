package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.enums.UserRole;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Participant;
import bg.sofia.uni.fmi.web.project.model.User;
import bg.sofia.uni.fmi.web.project.repository.EventRepository;
import bg.sofia.uni.fmi.web.project.repository.ParticipantRepository;
import bg.sofia.uni.fmi.web.project.repository.UserRepository;
import bg.sofia.uni.fmi.web.project.validation.ApiBadRequest;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository, UserRepository userRepository,
                              EventRepository eventRepository) {
        this.participantRepository = participantRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public Participant createParticipant(
        @NotNull(message = "Participant cannot be null")
        Participant participantToSave,
        @NotNull(message = "Associated userId cannot be null")
        @Positive(message = "Associated userId must be positive.")
        Long userIdToAssociate,
        @NotNull(message = "Associated event Id cannot be null")
        @Positive(message = "Associated event Id must be positive.")
        Long eventIdToAssociate) {


        Optional<User> potentialUserToAssociate = userRepository.findById(userIdToAssociate);
        Optional<Event> potentialEventToAssociate = eventRepository.findById(eventIdToAssociate);

        if (potentialUserToAssociate.isPresent() && !potentialUserToAssociate.get().isDeleted() &&
            potentialEventToAssociate.isPresent() && !potentialEventToAssociate.get().isDeleted()) {
            participantToSave.setAssociatedUser(potentialUserToAssociate.get());
            participantToSave.setAssociatedEvent(potentialEventToAssociate.get());
            participantToSave.setCreatedBy(potentialUserToAssociate.get().getEmail());
            participantToSave.setCreationTime(LocalDateTime.now());
            participantToSave.setDeleted(false);

            potentialUserToAssociate.get().getParticipantProfiles().add(participantToSave);
            potentialEventToAssociate.get().getAssociatedParticipants().add(participantToSave);
            return participantRepository.save(participantToSave);
        }

        throw new ApiBadRequest("There is no such associated user or event");
    }

    public List<Participant> getParticipants() {
        return participantRepository.findAll()
            .parallelStream()
            .filter(participant -> !participant.isDeleted())
            .collect(Collectors.toList());
    }

    public Optional<Participant> getParticipantById(
        @NotNull(message = "Id cannot be null")
        @Positive(message = "Id userId must be positive.")
        Long id) {

        Optional<Participant> potentialParticipantToReturn = participantRepository.findById(id);

        if (potentialParticipantToReturn.isPresent() && !potentialParticipantToReturn.get().isDeleted()) {
            return potentialParticipantToReturn;
        }

        throw new ResourceNotFoundException("Participant with such an id cannot be found");
    }

    public UserRole getUserRoleByParticipantId(
        @NotNull(message = "Id cannot be null")
        @Positive(message = "Id must be positive.")
        Long id) {

        Optional<Participant> potentialParticipant = this.getParticipantById(id);

        if (potentialParticipant.isPresent()) {
            return potentialParticipant.get().getUserRole();
        }

        return null;
    }

    public User getUserByParticipantId(
        @NotNull(message = "Id cannot be null")
        @Positive(message = "Id must be positive.")
        Long id) {

        Optional<Participant> optionalParticipant = this.getParticipantById(id);
        ;

        if (optionalParticipant.isPresent()) {
            return optionalParticipant.get().getAssociatedUser();
        }
        return null;
    }

    public Event getEventByParticipantId(Long id) {

        Optional<Participant> optionalParticipant = participantRepository.findById(id);

        if (optionalParticipant.isPresent()) {
            return optionalParticipant.get().getAssociatedEvent();
        }
        return null;
    }

    public boolean setUserRoleByParticipantId(
        @NotNull(message = "Associated userId cannot be null")
        @Positive(message = "Id must be positive.")
        Long id,
        @NotNull(message = "User role cannot be null")
        UserRole userRoleToSet) {

        Optional<Participant> optionalParticipant = this.getParticipantById(id);

        if (optionalParticipant.isPresent()) {

            optionalParticipant.get().setUserRole(userRoleToSet);

            if (this.getUserByParticipantId(id) != null) {
                optionalParticipant.get().setUpdatedBy(this.getUserByParticipantId(id).getEmail());
            }

            optionalParticipant.get().setLastUpdatedTime(LocalDateTime.now());
            participantRepository.save(optionalParticipant.get());
            return true;
        }
        return false;
    }

    public boolean deleteParticipant(
        @NotNull(message = "Id cannot be null")
        @Positive(message = "Id must be positive.")
        Long participantById) {

        Optional<Participant> optionalParticipantToDelete = participantRepository.findById(participantById);

        if (optionalParticipantToDelete.isPresent() && !optionalParticipantToDelete.get().isDeleted()) {

            Participant participantToDelete = optionalParticipantToDelete.get();

            participantToDelete.setLastUpdatedTime(LocalDateTime.now());

            participantToDelete.setDeleted(true);
            participantRepository.save(participantToDelete);
            return true;
        }

        throw new ResourceNotFoundException("There is not a participant with such an id");
    }

}
