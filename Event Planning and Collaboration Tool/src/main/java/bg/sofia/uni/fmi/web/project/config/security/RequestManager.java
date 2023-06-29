package bg.sofia.uni.fmi.web.project.config.security;

import bg.sofia.uni.fmi.web.project.model.User;
import bg.sofia.uni.fmi.web.project.service.UserService;
import bg.sofia.uni.fmi.web.project.service.security.TokenManagerService;
import bg.sofia.uni.fmi.web.project.validation.ApiBadRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.Optional;

public class RequestManager {

   private RequestManager() {}

    public static User getUserByRequest(HttpServletRequest request, TokenManagerService tokenManagerService,
                                        UserService userService) {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            String token = authorizationHeader.replace("Bearer ", "");
            String userId = tokenManagerService.getUserIdFromToken(token);

            BigDecimal userIdAsBigDecimal = new BigDecimal(userId);
            Optional<User> userToSetChanges = userService.getUserById(userIdAsBigDecimal
                .setScale(0, BigDecimal.ROUND_DOWN).longValue());

            if (userToSetChanges.isEmpty()) {
                throw new ApiBadRequest("There is an error with authentication");
            }

            return userToSetChanges.get();
        }
        throw new ApiBadRequest("There is an error with authentication");
    }
}
