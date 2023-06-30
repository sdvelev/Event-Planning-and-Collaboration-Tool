package bg.sofia.uni.fmi.web.project.service.security;

import bg.sofia.uni.fmi.web.project.dto.AuthenticationDto;
import bg.sofia.uni.fmi.web.project.dto.TokenUserIdDto;
import bg.sofia.uni.fmi.web.project.model.User;
import bg.sofia.uni.fmi.web.project.service.UserService;
import bg.sofia.uni.fmi.web.project.validation.ApiBadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationManagerService {

    private UserService userService;
    private TokenManagerService tokenManagerService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationManagerService(UserService userService,
                                        TokenManagerService tokenManagerService,
                                        PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tokenManagerService = tokenManagerService;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenUserIdDto login(AuthenticationDto authenticationDto) {

        String insertedUsername = authenticationDto.getUsername();
        String insertedPassword = authenticationDto.getPassword();

        validateCredentials(insertedUsername, insertedPassword);

        User returnedUser = userService.getUserByUsername(insertedUsername);
        if (returnedUser == null) {
            throw new ApiBadRequest("There is not a user with such a username");
        }

        if (!passwordEncoder.matches(insertedPassword, returnedUser.getPassword())) {
            throw new ApiBadRequest("Login failed");
        }

        TokenUserIdDto tokenIdDtoToReturn = new TokenUserIdDto();
        tokenIdDtoToReturn.setToken(tokenManagerService.generateTokenByUser(returnedUser));
        tokenIdDtoToReturn.setUserId(returnedUser.getId());

        return tokenIdDtoToReturn;
    }

    private void validateCredentials(String potentialUsername, String potentialPassword) {

        if (potentialUsername == null || potentialUsername.isBlank() ||
            potentialPassword == null || potentialPassword.isBlank()) {
            throw new ApiBadRequest("You have to provide a username and a password to log in");
        }
    }
}
