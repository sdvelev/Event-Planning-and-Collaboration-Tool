package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.dto.ParticipantDto;
import bg.sofia.uni.fmi.web.project.enums.UserRole;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Participant;
import bg.sofia.uni.fmi.web.project.model.User;
import bg.sofia.uni.fmi.web.project.repository.ParticipantRepository;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public Participant createParticipant(
        @NotNull(message = "The provided participant cannot be null")
        Participant participantToSave) {

        participantToSave.setCreationTime(LocalDateTime.now());

        return participantRepository.save(participantToSave);
    }

    public List<Participant> getParticipants() {
        return participantRepository.findAllByDeletedFalse();
    }

    public Optional<Participant> getParticipantById(
        @NotNull(message = "The provided participant id cannot be null")
        @Positive(message = "The provided participant id must be positive")
        Long id) {

        Optional<Participant> potentialParticipantToReturn = participantRepository.findByIdAndDeletedFalse(id);

        if (potentialParticipantToReturn.isPresent()) {
            return potentialParticipantToReturn;
        }

        throw new ResourceNotFoundException("Participant with such an id cannot be found");
    }

    public List<Participant> getParticipantsByUser(User userToSearch) {
        return participantRepository.findAllByAssociatedUserAndDeletedFalse(userToSearch);
    }

    public UserRole getUserRoleByParticipantId(
        @NotNull(message = "The provided participant id cannot be null")
        @Positive(message = "The provided participant id must be positive")
        Long id) {

        Optional<Participant> potentialParticipant  = this.getParticipantById(id);

        if (potentialParticipant.isPresent()) {
            return potentialParticipant.get().getUserRole();
        }

        throw new ResourceNotFoundException("Participant with such an id cannot be found");
    }

    public User getUserByParticipantId(
        @NotNull(message = "The provided participant id cannot be null")
        @Positive(message = "The provided participant id must be positive")
        Long id) {

        Optional<Participant> optionalParticipant = this.getParticipantById(id);;

        if (optionalParticipant.isPresent() && !optionalParticipant.get().isDeleted()) {
            return optionalParticipant.get().getAssociatedUser();
        }
        return null;
    }

    public Event getEventByParticipantId(
        @NotNull(message = "The provided participant id cannot be null")
        @Positive(message = "The provided participant id must be positive")
        Long id) {

        Optional<Participant> optionalParticipant = this.getParticipantById(id);

        if (optionalParticipant.isPresent() && !optionalParticipant.get().isDeleted()) {
            return optionalParticipant.get().getAssociatedEvent();
        }
        throw new ResourceNotFoundException("Participant with such an id cannot be found");
    }

    public boolean setUserRoleByParticipantId(
        @NotNull(message = "The provided participant id cannot be null")
        @Positive(message = "The provided participant id must be positive")
        Long id,
        @NotNull(message = "The user role cannot be null")
        UserRole userRoleToSet,
        @NotNull(message = "The user to make changes cannot be null")
        User userToSetChanges) {

        Optional<Participant> optionalParticipant = this.getParticipantById(id);

        if (optionalParticipant.isPresent() && !optionalParticipant.get().isDeleted() &&
            optionalParticipant.get().getUserRole() != UserRole.CREATOR &&
            userRoleToSet != UserRole.CREATOR) {

            optionalParticipant.get().setUserRole(userRoleToSet);
            optionalParticipant.get().setUpdatedBy(userToSetChanges.getUsername());
            optionalParticipant.get().setLastUpdatedTime(LocalDateTime.now());
            participantRepository.save(optionalParticipant.get());
            return true;
        }
        return false;
    }

    public boolean setParticipantById(
        @NotNull(message = "The provided participant dto cannot be null")
        ParticipantDto participantFieldsToChange,
        @NotNull(message = "The provided participant id cannot be null")
        @Positive(message = "The provided participant id must be positive")
        Long participantId,
        @NotNull(message = "The user to make changes cannot be null")
        User updatedUser) {

        Optional<Participant> optionalParticipantToUpdate = participantRepository.findByIdAndDeletedFalse(participantId);

        if (optionalParticipantToUpdate.isPresent()) {

            Participant participantToUpdate = setParticipantNonNullFields(participantFieldsToChange,
                optionalParticipantToUpdate.get());;
            participantToUpdate.setLastUpdatedTime(LocalDateTime.now());
            participantToUpdate.setUpdatedBy(updatedUser.getUsername());
            participantRepository.save(participantToUpdate);
            return true;
        }

        throw new ResourceNotFoundException("There is not a participant with such an id");
    }

    public boolean deleteParticipant( @NotNull(message = "The provided participant id cannot be null")
                              @Positive(message = "The provided participant id must be positive")
                              Long participantId,
                              @NotNull(message = "The user to make changes cannot be null")
                              User updatedUser) {
        Optional<Participant> optionalParticipantToDelete = participantRepository.findByIdAndDeletedFalse(participantId);

        if (optionalParticipantToDelete.isPresent()) {

            Participant participantToDelete = optionalParticipantToDelete.get();

            participantToDelete.setLastUpdatedTime(LocalDateTime.now());
            participantToDelete.setUpdatedBy(updatedUser.getUsername());
            participantToDelete.setDeleted(true);
            participantRepository.save(participantToDelete);
            return true;
        }

        throw new ResourceNotFoundException("There is not a participant with such an id");
    }

    public boolean deleteParticipantByRole(
        @NotNull(message = "The provided participant id cannot be null")
        @Positive(message = "The provided participant id must be positive")
        Long participantId,
        @NotNull(message = "The user to make changes cannot be null")
        User updatedUser) {

        Optional<Participant> optionalParticipantToDelete = participantRepository.findByIdAndDeletedFalse(participantId);

        if (optionalParticipantToDelete.isPresent() && optionalParticipantToDelete.get().getUserRole() != UserRole.CREATOR) {

            Participant participantToDelete = optionalParticipantToDelete.get();

            participantToDelete.setLastUpdatedTime(LocalDateTime.now());
            participantToDelete.setUpdatedBy(updatedUser.getUsername());
            participantToDelete.setDeleted(true);
            participantRepository.save(participantToDelete);
            return true;
        }

        throw new ResourceNotFoundException("There is not a participant with such an id or the participant is with creator role");
    }

    private Participant setParticipantNonNullFields(
        @NotNull(message = "The provided participant dto cannot be null")
        ParticipantDto participantFieldsToChange,
        @NotNull(message = "The provided participant cannot be null")
        Participant participantToUpdate) {
        if (participantFieldsToChange.getUserRole() != null &&
            participantFieldsToChange.getUserRole() != UserRole.CREATOR &&
            participantToUpdate.getUserRole() != UserRole.CREATOR) {
            participantToUpdate.setUserRole(participantFieldsToChange.getUserRole());
        }

        return participantToUpdate;
    }
}
