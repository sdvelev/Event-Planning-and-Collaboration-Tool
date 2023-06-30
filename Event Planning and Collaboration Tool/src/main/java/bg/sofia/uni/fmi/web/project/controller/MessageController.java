//package bg.sofia.uni.fmi.web.project.controller;
//
//import bg.sofia.uni.fmi.web.project.dto.ReviewDto;
//import bg.sofia.uni.fmi.web.project.service.MessageService;
//import com.vaadin.flow.server.auth.AnonymousAllowed;
//import dev.hilla.Endpoint;
//import dev.hilla.Nonnull;
//import lombok.AllArgsConstructor;
//import nl.martijndwars.webpush.Subscription;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
////@Endpoint
////@AnonymousAllowed
//@RestController
//@AllArgsConstructor
//@RequestMapping("notifications")
//public class MessageController {
//    private final MessageService messageService;
//
//    @RequestMapping(value="/public_key",  produces="text/plain" , method = RequestMethod.GET)
//    public ResponseEntity<String> getPublicKey() {
//        return ResponseEntity.ok(messageService.getPublicKey());
//    }
//
//    @PostMapping(value = "/subscribe")
//    public void subscribe(Subscription subscription) {
//        messageService.subscribe(subscription);
//    }
//
//    @PostMapping(value = "/unsubscribe")
//    public void unsubscribe(String endpoint) {
//        messageService.unsubscribe(endpoint);
//    }
//}