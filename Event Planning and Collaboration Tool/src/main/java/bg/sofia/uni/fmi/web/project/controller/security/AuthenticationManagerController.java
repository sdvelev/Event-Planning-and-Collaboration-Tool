package bg.sofia.uni.fmi.web.project.controller.security;

import bg.sofia.uni.fmi.web.project.dto.AuthenticationDto;
import bg.sofia.uni.fmi.web.project.dto.TokenUserIdDto;
import bg.sofia.uni.fmi.web.project.service.security.AuthenticationManagerService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/login")
@Validated
public class AuthenticationManagerController {

    private final AuthenticationManagerService authenticationManagerService;

    @Autowired
    public AuthenticationManagerController(AuthenticationManagerService authenticationManagerService) {
        this.authenticationManagerService = authenticationManagerService;
    }

    @PostMapping
    public TokenUserIdDto login(@RequestBody @NotNull(message = "Login details cannot be null")
                            AuthenticationDto authenticationDto) {
        return authenticationManagerService.login(authenticationDto);
    }
}
