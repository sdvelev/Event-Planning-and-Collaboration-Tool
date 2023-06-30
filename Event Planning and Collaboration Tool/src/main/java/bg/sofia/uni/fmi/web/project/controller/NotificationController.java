package bg.sofia.uni.fmi.web.project.controller;

import bg.sofia.uni.fmi.web.project.service.NotificationsService;
import lombok.AllArgsConstructor;
import nl.martijndwars.webpush.Subscription;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@Endpoint
//@AnonymousAllowed
@RestController
@AllArgsConstructor
@RequestMapping("notifications")
public class NotificationController {
    private final NotificationsService notificationsService;

    @RequestMapping(value="/public_key",  produces="text/plain" , method = RequestMethod.GET)
    public ResponseEntity<String> getPublicKey() {
        return ResponseEntity.ok(notificationsService.getPublicKey());
    }

    @PostMapping(value = "/subscribe")
    public void subscribe(Subscription subscription) {
        notificationsService.subscribe(subscription);
    }

    @PostMapping(value = "/unsubscribe")
    public void unsubscribe(String endpoint) {
        notificationsService.unsubscribe(endpoint);
    }
}