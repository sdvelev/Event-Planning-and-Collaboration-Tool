package bg.sofia.uni.fmi.web.project.controller;

import bg.sofia.uni.fmi.web.project.dto.UserDto;
import bg.sofia.uni.fmi.web.project.enums.UserRole;
import bg.sofia.uni.fmi.web.project.mapper.UserMapper;
import bg.sofia.uni.fmi.web.project.model.User;
import bg.sofia.uni.fmi.web.project.service.UserService;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        final List<User> toReturnList = userService.getUsers();

        return toReturnList.stream()
            .map(userMapper::toDto)
            .collect(Collectors.toList());
    }

    @PostMapping
    public Long addUser(@NotNull(message = "UserDto cannot be null") @RequestBody UserDto userDto) {
        User potentialUserToCreate = userService.createUser(userMapper.toEntity(userDto));

        if (potentialUserToCreate != null) {
            return potentialUserToCreate.getId();
        }

        return -1L;
    }

    @DeleteMapping
    public boolean removeUser(
        @NotNull(message = "Username cannot be null")
        @NotBlank(message = "Username cannot be blank")
        @RequestParam("username") String username,
        @NotNull(message = "Password cannot be null")
        @NotBlank(message = "Password cannot be blank")
        @RequestParam("password") String password) {
       return userService.deleteUser(username, password);
    }

    @GetMapping(value = "/search", params = {"username", "password"})
    public ResponseEntity<UserDto> searchUserByUsernameAndPassword(
        @NotNull(message = "Username cannot be null")
        @NotBlank(message = "Username cannot be blank")
        @RequestParam("username") String username,
        @NotNull(message = "Password cannot be null")
        @NotBlank(message = "Password cannot be blank")
        @RequestParam("password") String password) {
        User userToReturn = userService.getUserByUsernameAndPassword(username, password);

        return ResponseEntity.ok(userMapper.toDto(userToReturn));

        //return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/search", params = {"id"})
    public ResponseEntity<UserDto> searchUserById(
        @Valid
        @NotNull(message = "UserID cannot be null")
        @Positive(message = "UserID must be positive")
        @RequestParam("id") Long id) {
        Optional<User> optionalUserToReturn = userService.getUserById(id);

        if (optionalUserToReturn.isPresent()) {
            return ResponseEntity.ok(userMapper.toDto(optionalUserToReturn.get()));
        }

        throw new ResourceNotFoundException("User with such a username and password cannot be found");

        //return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/search", params = {"username"})
    public ResponseEntity<UserDto> searchUserByUsername(
        @Valid
        @NotNull(message = "Username cannot be null")
        @NotBlank(message = "Username cannot be blank")
        @RequestParam("username") String username) {

        User potentialUserToReturn = userService.getUserByUsername(username);

        return ResponseEntity.ok(userMapper.toDto(potentialUserToReturn));

//        throw new ResourceNotFoundException("User with such a username and password cannot be found");

        //return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/search", params = {"email"})
    public ResponseEntity<UserDto> searchUserByEmail(
        @Valid
        @NotNull(message = "Email cannot be null")
        @NotBlank(message = "Email cannot be blank")
        @RequestParam("email") String email) {

        User potentialUserToReturn = userService.getUserByEmail(email);

        return ResponseEntity.ok(userMapper.toDto(potentialUserToReturn));

//        throw new ResourceNotFoundException("User with such a username and password cannot be found");

        //return ResponseEntity.notFound().build();
    }

    @PatchMapping(value = "/settings", params = {"new_username", "old_username", "password"})
    public boolean setUsernameByProvidingOldUsernameAndPassword(
        @NotNull(message = "The new username cannot be null")
        @NotBlank(message = "The new username cannot be blank")
        @RequestParam("new_username")
        String newUsername,
        @NotNull(message = "The old username cannot be null")
        @NotBlank(message = "The old username cannot be blank")
        @RequestParam("old_username") String oldUsername,
        @NotNull(message = "The password cannot be null")
        @NotBlank(message = "The password cannot be blank")
        @RequestParam("password") String password) {
        return userService.setUsernameByProvidingOldUsernameAndPassword(newUsername, oldUsername, password);
    }

    @PatchMapping(value = "/settings", params = {"new_password", "username", "old_password"})
    public boolean setPasswordByProvidingUsernameAndOldPassword(
        @NotNull(message = "New password cannot be null")
        @NotBlank(message = "New password cannot be empty or blank")
        @RequestParam("new_password")
        String newPassword,
        @NotNull(message = "Username cannot be null")
        @NotBlank(message = "Username cannot be empty or blank")
        @RequestParam("username")
        String username,
        @NotNull(message = "Old password cannot be null")
        @NotBlank(message = "Old password cannot be empty or blank")
        @RequestParam("old_password")
        String oldPassword) {
        return userService.setPasswordByProvidingUsernameAndOldPassword(newPassword, username, oldPassword);
    }

}
