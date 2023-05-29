package bg.sofia.uni.fmi.web.project.controller;

import bg.sofia.uni.fmi.web.project.service.GuestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("guests")
@AllArgsConstructor
public class GuestController {
    private final GuestService guestService;
}
