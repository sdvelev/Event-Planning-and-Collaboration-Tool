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
import java.util.stream.Collectors;

@Service
@Validated
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public Participant createParticipant(
        @NotNull(message = "Participant cannot be null")
        Participant participantToSave) {

        participantToSave.setCreationTime(LocalDateTime.now());
        participantToSave.setDeleted(false);

        return participantRepository.save(participantToSave);
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

        Optional<Participant> potentialParticipant  = this.getParticipantById(id);

        if (potentialParticipant.isPresent()) {
            return potentialParticipant.get().getUserRole();
        }

        return null;
    }

    public User getUserByParticipantId(
        @NotNull(message = "Id cannot be null")
        @Positive(message = "Id must be positive.")
        Long id) {

        Optional<Participant> optionalParticipant = this.getParticipantById(id);;

        if (optionalParticipant.isPresent() && !optionalParticipant.get().isDeleted()) {
            return optionalParticipant.get().getAssociatedUser();
        }
        return null;
    }

    public Event getEventByParticipantId(Long id) {

        Optional<Participant> optionalParticipant = this.getParticipantById(id);

        if (optionalParticipant.isPresent() && !optionalParticipant.get().isDeleted()) {
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

        if (optionalParticipant.isPresent() && !optionalParticipant.get().isDeleted() &&
            optionalParticipant.get().getUserRole() != UserRole.CREATOR &&
            userRoleToSet != UserRole.CREATOR) {

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

    public boolean setParticipantById(
        @NotNull(message = "Participant record cannot be null")
        ParticipantDto participantFieldsToChange,
        @NotNull(message = "Participant id cannot be null")
        @Positive(message = "Participant id must be positive")
        Long participantId) {

        Optional<Participant> optionalParticipantToUpdate = participantRepository.findById(participantId);

        if (optionalParticipantToUpdate.isPresent() && !optionalParticipantToUpdate.get().isDeleted()) {

            Participant participantToUpdate = setParticipantNonNullFields(participantFieldsToChange,
                optionalParticipantToUpdate.get());;
            participantToUpdate.setLastUpdatedTime(LocalDateTime.now());
            participantRepository.save(participantToUpdate);
            return true;
        }

        throw new ResourceNotFoundException("There is not an event with such an id");
    }

    public boolean deleteParticipant(
        @NotNull(message = "Id cannot be null")
        @Positive(message = "Id must be positive.")
        Long participantId) {

        Optional<Participant> optionalParticipantToDelete = participantRepository.findById(participantId);

        if (optionalParticipantToDelete.isPresent() && !optionalParticipantToDelete.get().isDeleted()) {

            Participant participantToDelete = optionalParticipantToDelete.get();

            participantToDelete.setLastUpdatedTime(LocalDateTime.now());

            participantToDelete.setDeleted(true);
            participantRepository.save(participantToDelete);
            return true;
        }

        throw new ResourceNotFoundException("There is not a participant with such an id");
    }

    private Participant setParticipantNonNullFields(ParticipantDto participantFieldsToChange,
                                                    Participant participantToUpdate) {

        if (participantFieldsToChange.getUserRole() != null &&
            participantFieldsToChange.getUserRole() != UserRole.CREATOR &&
            participantToUpdate.getUserRole() != UserRole.CREATOR) {
            participantToUpdate.setUserRole(participantFieldsToChange.getUserRole());
        }

        return participantToUpdate;
    }
}
