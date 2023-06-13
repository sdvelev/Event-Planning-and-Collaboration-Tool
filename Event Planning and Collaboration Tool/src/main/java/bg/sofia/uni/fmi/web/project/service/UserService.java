package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.dto.UserDto;
import bg.sofia.uni.fmi.web.project.model.User;
import bg.sofia.uni.fmi.web.project.repository.UserRepository;
import bg.sofia.uni.fmi.web.project.validation.ApiBadRequest;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.validation.constraints.NotBlank;
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
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(@NotNull(message = "User cannot be null") User userToSave) {

        for (User currentUser : userRepository.findByUsername(userToSave.getUsername())) {
            if (!currentUser.isDeleted()) {
                throw new ApiBadRequest("There is already a user associated with that credentials");
            }
        }

        for (User currentUser : userRepository.findByEmail(userToSave.getEmail())) {
            if (!currentUser.isDeleted()) {
                throw new ApiBadRequest("There is already a user associated with that credentials");
            }
        }

        userToSave.setCreationTime(LocalDateTime.now());
        userToSave.setDeleted(false);

        return userRepository.save(userToSave);
    }

    public List<User> getUsers() {
        return userRepository.findAll()
            .parallelStream()
            .filter(user -> !user.isDeleted())
            .collect(Collectors.toList());
    }

    public Optional<User> getUserById(@NotNull(message = "User Id cannot be null")
                                      @Positive(message = "User Id must be positive")
                                      Long id) {

        Optional<User> potentialUserToReturn = userRepository.findById(id);

        if (potentialUserToReturn.isPresent() && !potentialUserToReturn.get().isDeleted()) {
            return potentialUserToReturn;
        }

        throw new ResourceNotFoundException("User with such an id cannot be found");
    }

    public User getUserByEmail(@NotNull(message = "Email cannot be null")
                                         @NotBlank(message = "Email cannot be empty or blank")
                                         String email) {
        List<User> potentialUsersToReturn = userRepository.findByEmail(email);

        for (User currentUser : potentialUsersToReturn) {
            if (!currentUser.isDeleted()) {
                return currentUser;
            }
        }

        throw new ResourceNotFoundException("User with such an email cannot be found");
    }

    public User getUserByUsername(@NotNull(message = "Username cannot be null")
                                            @NotBlank(message = "Username cannot be empty or blank")
                                            String username) {
        List<User> potentialUsersToReturn = userRepository.findByUsername(username);


        for (User currentUser : potentialUsersToReturn) {
            if (!currentUser.isDeleted()) {
                return currentUser;
            }
        }

        throw new ResourceNotFoundException("User with such a username cannot be found");
    }

    public User getUserByUsernameAndPassword(@NotNull(message = "Username cannot be null")
                                                       @NotBlank(message = "Username cannot be empty or blank")
                                                       String username,
                                                       @NotNull(message = "Password cannot be null")
                                                       @NotBlank(message = "Password cannot be empty or blank")
                                                       String password) {

        List<User> potentialUsersToReturn = userRepository.findByUsernameAndPassword(username, password);


        for (User currentUser : potentialUsersToReturn) {
            if (!currentUser.isDeleted()) {
                return currentUser;
            }
        }

        throw new ResourceNotFoundException("User with such a username and password cannot be found");
    }

    public boolean setUsernameByProvidingOldUsernameAndPassword(@NotNull(message = "New username cannot be null")
                                                                @NotBlank(message = "New username cannot be empty or blank")
                                                                String newUserName,
                                                                @NotNull(message = "Old username cannot be null")
                                                                @NotBlank(message = "Old username cannot be empty or blank")
                                                                String oldUsername,
                                                                @NotNull(message = "Password cannot be null")
                                                                @NotBlank(message = "Password cannot be empty or blank")
                                                                String password) {

        List<User> optionalUsersToChange = userRepository.findByUsernameAndPassword(oldUsername, password);

        for (User currentUser : optionalUsersToChange) {

            if (!currentUser.isDeleted()) {
                for (User user : userRepository.findByUsername(newUserName)) {
                    if (!user.isDeleted()) {
                        throw new ApiBadRequest("The new username is already taken");
                    }
                }

                currentUser.setUsername(newUserName);
                currentUser.setUpdatedBy(currentUser.getEmail());
                currentUser.setLastUpdatedTime(LocalDateTime.now());
                userRepository.save(currentUser);
                return true;
            }
        }

        throw new ResourceNotFoundException("User with such a username and password cannot be found");
    }

    public boolean setPasswordByProvidingUsernameAndOldPassword(@NotNull(message = "New password cannot be null")
                                                                @NotBlank(message = "New password cannot be empty or blank")
                                                                String newPassword,
                                                                @NotNull(message = "Username cannot be null")
                                                                @NotBlank(message = "Username cannot be empty or blank")
                                                                String username,
                                                                @NotNull(message = "Old password cannot be null")
                                                                @NotBlank(message = "Old password cannot be empty or blank")
                                                                String oldPassword) {

        List<User> optionalUsersToChange = userRepository.findByUsernameAndPassword(username, oldPassword);

        for (User currentUser : optionalUsersToChange) {

            if (!currentUser.isDeleted()) {
                currentUser.setPassword(newPassword);
                currentUser.setUpdatedBy(currentUser.getEmail());
                currentUser.setLastUpdatedTime(LocalDateTime.now());
                userRepository.save(currentUser);
                return true;
            }
        }

        throw new ResourceNotFoundException("User with such a username and password cannot be found");
    }

    private User setUserNonNullFields(UserDto userFieldsToChange, User userToUpdate) {

        if (userFieldsToChange.getName() != null) {
            userToUpdate.setName(userFieldsToChange.getName());
        }

        if (userFieldsToChange.getSurname() != null) {
            userToUpdate.setSurname(userFieldsToChange.getSurname());
        }

        if (userFieldsToChange.getEmail() != null) {
            userToUpdate.setEmail(userFieldsToChange.getEmail());
        }

        if (userFieldsToChange.getProfilePhotoLink() != null) {
            userToUpdate.setProfilePhotoLink(userFieldsToChange.getProfilePhotoLink());
        }

        if (userFieldsToChange.getAddress() != null) {
            userToUpdate.setAddress(userFieldsToChange.getAddress());
        }

        return userToUpdate;
    }

    public boolean setUserById(
        @NotNull(message = "User record cannot be null")
        UserDto userFieldsToChange,
        @NotNull(message = "User id cannot be null")
        @Positive(message = "User id must be positive")
        Long userId) {

        Optional<User> optionalUserToUpdate = userRepository.findById(userId);

        if (optionalUserToUpdate.isPresent() && !optionalUserToUpdate.get().isDeleted()) {

            User userToUpdate = setUserNonNullFields(userFieldsToChange, optionalUserToUpdate.get());;
            userToUpdate.setLastUpdatedTime(LocalDateTime.now());
            userRepository.save(userToUpdate);
            return true;
        }

        throw new ResourceNotFoundException("There is not a user with such an id");
    }

    public User deleteUser(
        @NotNull(message = "Username cannot be null")
        @NotBlank(message = "Username cannot be empty or blank")
        String username,
        @NotNull(message = "Password cannot be null")
        @NotBlank(message = "Password cannot be empty or blank")
        String password) {

        List<User> optionalUsersToDelete = userRepository.findByUsernameAndPassword(username, password);

        for (User currentUser : optionalUsersToDelete) {

            if (!currentUser.isDeleted()) {
                currentUser.setLastUpdatedTime(LocalDateTime.now());
                currentUser.setDeleted(true);
                userRepository.save(currentUser);
                return currentUser;
            }
        }

        throw new ResourceNotFoundException("User with such a username and password cannot be found");
    }
}