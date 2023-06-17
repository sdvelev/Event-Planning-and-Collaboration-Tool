package bg.sofia.uni.fmi.web.project.controller;

import bg.sofia.uni.fmi.web.project.dto.UserDto;
import bg.sofia.uni.fmi.web.project.mapper.UserMapper;
import bg.sofia.uni.fmi.web.project.model.User;
import bg.sofia.uni.fmi.web.project.service.UserParticipantFacadeService;
import bg.sofia.uni.fmi.web.project.service.UserService;
import bg.sofia.uni.fmi.web.project.validation.ApiBadRequest;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/users")
@Validated
public class UserController {

    private final UserService userService;
    private final UserParticipantFacadeService userParticipantFacadeService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserParticipantFacadeService userParticipantFacadeService, UserMapper userMapper) {
        this.userService = userService;
        this.userParticipantFacadeService = userParticipantFacadeService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {

        return userMapper.toDtoList(userService.getUsers());
    }

    @PostMapping
    public Long addUser(@NotNull(message = "The provided user dto as body of the query cannot be null")
                        @RequestBody UserDto userDto) {
        User potentialUserToCreate = userService.createUser(userMapper.toEntity(userDto));

        if (potentialUserToCreate != null) {
            return potentialUserToCreate.getId();
        }

        throw new ApiBadRequest("There was a problem in creating a user");
    }

    @DeleteMapping(params = {"username", "password"})
    public boolean removeUser(
        @NotNull(message = "The provided username cannot be null")
        @NotBlank(message = "The provided username cannot be blank")
        @RequestParam("username") String username,
        @NotNull(message = "The provided password cannot be null")
        @NotBlank(message = "The provided password cannot be blank")
        @RequestParam("password") String password) {
       return userParticipantFacadeService.deleteUserWithParticipants(username, password);
    }

    @GetMapping(value = "/search", params = {"username", "password"})
    public ResponseEntity<UserDto> searchUserByUsernameAndPassword(
        @NotNull(message = "The provided username cannot be null")
        @NotBlank(message = "The provided username cannot be blank")
        @RequestParam("username") String username,
        @NotNull(message = "The provided password cannot be null")
        @NotBlank(message = "The provided password cannot be blank")
        @RequestParam("password") String password) {
        User userToReturn = userService.getUserByUsernameAndPassword(username, password);

        return ResponseEntity.ok(userMapper.toDto(userToReturn));
    }

    @GetMapping(value = "/search", params = {"id"})
    public ResponseEntity<UserDto> searchUserById(
        @NotNull(message = "The provided user id cannot be null")
        @Positive(message = "The provided user id must be positive")
        @RequestParam("id") Long id) {
        Optional<User> optionalUserToReturn = userService.getUserById(id);

        if (optionalUserToReturn.isPresent()) {
            return ResponseEntity.ok(userMapper.toDto(optionalUserToReturn.get()));
        }

        throw new ResourceNotFoundException("User with such a username and password cannot be found");
    }

    @GetMapping(value = "/search", params = {"username"})
    public ResponseEntity<UserDto> searchUserByUsername(
        @NotNull(message = "The provided username cannot be null")
        @NotBlank(message = "The provided username cannot be blank")
        @RequestParam("username") String username) {

        User potentialUserToReturn = userService.getUserByUsername(username);

        return ResponseEntity.ok(userMapper.toDto(potentialUserToReturn));
    }

    @GetMapping(value = "/search", params = {"email"})
    public ResponseEntity<UserDto> searchUserByEmail(
        @NotNull(message = "The provided email cannot be null")
        @NotBlank(message = "The provided email cannot be blank")
        @RequestParam("email") String email) {

        User potentialUserToReturn = userService.getUserByEmail(email);

        return ResponseEntity.ok(userMapper.toDto(potentialUserToReturn));
    }

    @PutMapping(value = "/set", params = {"user_id"})
    public boolean setUserByUserId(@RequestParam("user_id")
                                        @NotNull(message = "The provided user id cannot be null")
                                        @Positive(message = "The provided user id must be positive")
                                        Long userId,
                                        @RequestBody
                                        @NotNull(message = "The provided user dto as body of the query cannot be null")
                                        UserDto userToUpdate) {
        //TODO: Authorization in order to update event
        return userService.setUserById(userToUpdate, userId);
    }

    @PatchMapping(value = "/settings", params = {"new_username", "old_username", "password"})
    public boolean setUsernameByProvidingOldUsernameAndPassword(
        @NotNull(message = "The provided  new username cannot be null")
        @NotBlank(message = "The provided new username cannot be blank")
        @RequestParam("new_username")
        String newUsername,
        @NotNull(message = "The provided old username cannot be null")
        @NotBlank(message = "The provided old username cannot be blank")
        @RequestParam("old_username") String oldUsername,
        @NotNull(message = "The provided password cannot be null")
        @NotBlank(message = "The provided password cannot be blank")
        @RequestParam("password") String password) {
        return userService.setUsernameByProvidingOldUsernameAndPassword(newUsername, oldUsername, password);
    }

    @PatchMapping(value = "/settings", params = {"new_password", "username", "old_password"})
    public boolean setPasswordByProvidingUsernameAndOldPassword(
        @NotNull(message = "The provided new password cannot be null")
        @NotBlank(message = "The provided new password cannot be empty or blank")
        @RequestParam("new_password")
        String newPassword,
        @NotNull(message = "The provided username cannot be null")
        @NotBlank(message = "The provided username cannot be empty or blank")
        @RequestParam("username")
        String username,
        @NotNull(message = "The provided old password cannot be null")
        @NotBlank(message = "The provided old password cannot be empty or blank")
        @RequestParam("old_password")
        String oldPassword) {
        return userService.setPasswordByProvidingUsernameAndOldPassword(newPassword, username, oldPassword);
    }
}
