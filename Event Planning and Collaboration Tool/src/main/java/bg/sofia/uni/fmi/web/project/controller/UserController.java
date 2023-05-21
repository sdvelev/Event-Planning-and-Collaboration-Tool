package bg.sofia.uni.fmi.web.project.controller;

import bg.sofia.uni.fmi.web.project.dto.UserDto;
import bg.sofia.uni.fmi.web.project.mapper.UserMapper;
import bg.sofia.uni.fmi.web.project.model.User;
import bg.sofia.uni.fmi.web.project.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/users")
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
    public Long addUser(@Valid @NotNull @RequestBody UserDto userDto) {

        User potentialUserToCreate = userService.createUser(userMapper.toEntity(userDto));

        if (potentialUserToCreate != null) {
            return potentialUserToCreate.getId();
        }

        return -1L;
    }

    @DeleteMapping
    public boolean removeUser(@RequestParam("username") String username, @RequestParam("password") String password) {
       return userService.deleteUser(username, password);
    }

    @GetMapping(value = "/search", params = {"username", "password"})
    public ResponseEntity<UserDto> searchUserByUsernameAndPassword(@RequestParam("username") String username,
                                                                  @RequestParam("password") String password) {
        Optional<User> optionalUserToReturn = userService.getUserByUsernameAndPassword(username, password);

        if (optionalUserToReturn.isPresent()) {
            return ResponseEntity.ok(userMapper.toDto(optionalUserToReturn.get()));
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/search", params = {"id"})
    public ResponseEntity<UserDto> searchUserById(@RequestParam("id") Long id) {
        Optional<User> optionalUserToReturn = userService.getUserById(id);

        if (optionalUserToReturn.isPresent()) {
            return ResponseEntity.ok(userMapper.toDto(optionalUserToReturn.get()));
        }

        return ResponseEntity.noContent().build();
    }
}
