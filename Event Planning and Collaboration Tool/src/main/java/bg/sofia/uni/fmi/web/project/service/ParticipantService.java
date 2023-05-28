package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.enums.UserRole;
import bg.sofia.uni.fmi.web.project.model.Participant;
import bg.sofia.uni.fmi.web.project.model.User;
import bg.sofia.uni.fmi.web.project.repository.ParticipantRepository;
import bg.sofia.uni.fmi.web.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository, UserRepository userRepository) {
        this.participantRepository = participantRepository;
        this.userRepository = userRepository;
    }

    public Participant createParticipant(Participant participantToSave, Long userIdToAssociate) {


        Optional<User> potentialUserToAssociate = userRepository.findById(userIdToAssociate);

        if (potentialUserToAssociate.isEmpty()) {
            return null;
        }

        participantToSave.setAssociatedUser(potentialUserToAssociate.get());;

        participantToSave.setCreationTime(LocalDateTime.now());

        participantToSave.setDeleted(false);

        return participantRepository.save(participantToSave);
    }

    public List<Participant> getParticipants() {
        return participantRepository.findAll();
    }

    public Optional<Participant> getParticipantById(Long id) {
        return participantRepository.findById(id);
    }

    public UserRole getUserRoleByParticipantId(Long id) {

        Optional<Participant> potentialParticipant  = this.getParticipantById(id);

        if (potentialParticipant.isPresent()) {
            return potentialParticipant.get().getUserRole();
        }
        return null;
    }

    public User getUserByParticipantId(Long id) {

        Optional<Participant> optionalParticipant = participantRepository.findById(id);

        if (optionalParticipant.isPresent()) {
            return optionalParticipant.get().getAssociatedUser();
        }
        return null;
    }

//    public Event getEventByParticipantId(Long id) {
//
//        Optional<Participant> optionalParticipant = participantRepository.findById(id);
//
//        if (optionalParticipant.isPresent()) {
//            return optionalParticipant.get().getAssociatedEvent();
//        }
//        return null;
//    }

    public boolean setUserRoleByParticipantId(Long id, UserRole userRoleToSet) {

        Optional<Participant> optionalParticipant = this.getParticipantById(id);

        if (optionalParticipant.isPresent()) {

            optionalParticipant.get().setUserRole(userRoleToSet);

            optionalParticipant.get().setUpdatedBy(this.getUserByParticipantId(id).getEmail());
            optionalParticipant.get().setLastUpdatedTime(LocalDateTime.now());

            participantRepository.save(optionalParticipant.get());
            return true;
        }
        return false;
    }


    public boolean deleteParticipant(Long participantById) {

        Optional<Participant> optionalParticipantToDelete = participantRepository.findById(participantById);

        if (optionalParticipantToDelete.isPresent()) {

            Participant participantToDelete = optionalParticipantToDelete.get();

            participantToDelete.setLastUpdatedTime(LocalDateTime.now());

            participantToDelete.setDeleted(true);

            participantRepository.save(participantToDelete);

            return true;
        }

        return false;
    }


}
