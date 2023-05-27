package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.model.Participant;
import bg.sofia.uni.fmi.web.project.model.Invitation;
import bg.sofia.uni.fmi.web.project.model.User;
import bg.sofia.uni.fmi.web.project.repository.InvitationRepository;
import bg.sofia.uni.fmi.web.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, InvitationRepository invitationRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User userToSave) {

        if (userRepository.findByUsername(userToSave.getUsername()).isPresent()) {
            return null;
        }

        if (userRepository.findByEmail(userToSave.getEmail()).isPresent()) {
            return null;
        }

        return userRepository.save(userToSave);
    }

//    public User createUser(String username, String password, String email) {
//        User userToCreate = User.builder()
//            .username(username)
//            .password(password)
//            .email(email)
//            .build();
//
//        return userRepository.save(userToCreate);
//    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public boolean setUsernameByProvidingOldUsernameAndPassword(String newUserName, String oldUsername, String password) {

        Optional<User> optionalUserToChange = userRepository.findByUsernameAndPassword(oldUsername, password);

        if (optionalUserToChange.isPresent()) {
            User userToChange = optionalUserToChange.get();

            userToChange.setUsername(newUserName);

            userRepository.save(userToChange);
            return true;
        } else {
            return false;
        }
    }

    public boolean setNameById(String name, Long id) {
        Optional<User> optionalUserToChange = userRepository.findById(id);

        if (optionalUserToChange.isPresent()) {
            User userToChange = optionalUserToChange.get();

            userToChange.setName(name);

            userRepository.save(userToChange);
            return true;
        } else {
            return false;
        }
    }

//    public boolean setAgeById(Integer age, Long id) {
//        Optional<User> optionalUserToChange = userRepository.findById(id);
//
//        if (optionalUserToChange.isPresent()) {
//            User userToChange = optionalUserToChange.get();
//
//            userToChange.setAge(age);
//
//            userRepository.save(userToChange);
//            return true;
//        } else {
//            return false;
//        }
//    }

    public boolean setAddressById(String address, Long id) {
        Optional<User> optionalUserToChange = userRepository.findById(id);

        if (optionalUserToChange.isPresent()) {

            User userToChange = optionalUserToChange.get();

            userToChange.setAddress(address);

            userRepository.save(userToChange);
            return true;
        } else {
            return false;
        }
    }

    public boolean setPasswordById(String password, Long id) {
        Optional<User> optionalUserToChange = userRepository.findById(id);

        if (optionalUserToChange.isPresent()) {

            User userToChange = optionalUserToChange.get();

            userToChange.setPassword(password);

            userRepository.save(userToChange);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteUser(String username, String password) {

        Optional<User> optionalUserToDelete = userRepository.findByUsernameAndPassword(username, password);

        if (optionalUserToDelete.isPresent()) {

            User userToDelete = optionalUserToDelete.get();

            for (Participant currentParticipant : userToDelete.getParticipantProfiles()) {

                //currentParticipant.getEvent().delete();
            }

            userRepository.delete(userToDelete);
            return true;
        }

        return false;
    }



}
