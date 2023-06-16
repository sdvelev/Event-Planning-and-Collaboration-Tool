package bg.sofia.uni.fmi.web.project.controller;

import bg.sofia.uni.fmi.web.project.dto.GuestDto;
import bg.sofia.uni.fmi.web.project.enums.AttendanceType;
import bg.sofia.uni.fmi.web.project.enums.GuestType;
import bg.sofia.uni.fmi.web.project.mapper.GuestMapper;
import bg.sofia.uni.fmi.web.project.service.GuestEventFacadeService;
import bg.sofia.uni.fmi.web.project.service.GuestService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/guests")
@Validated
@AllArgsConstructor
public class GuestController {
    private final GuestService guestService;
    private final GuestEventFacadeService guestEventFacadeService;
    private final GuestMapper mapper;

    @PostMapping(params = {"assigned_event_id", "guest_type"})
    public long addGuest(@Valid @NotNull(message = "The guestDto cannot be null!") @RequestBody GuestDto guestDto,
                         @Valid @NotNull(message = "The event id cannot be null!") @RequestParam("assigned_event_id")
                         Long eventId,
                         @Valid
                         @NotNull(message = "The guest type cannot be null!")
                         @NotEmpty(message = "The guest type cannot be empty!")
                         @NotBlank(message = "The guest type cannot be blank!")
                         @RequestParam("guest_type")
                         String guestType,
                         @Valid
                         @NotNull(message = "The attendance type cannot be null!")
                         @NotEmpty(message = "The attendance type cannot be empty!")
                         @NotBlank(message = "The attendance type cannot be blank!")
                         @RequestParam("attendance_type")
                         String attendanceType) {
        return guestEventFacadeService.addGuest(mapper.toEntity(guestDto), eventId, guestType, attendanceType);
    }

    @GetMapping
    public List<GuestDto> getAllGuests() {
        return mapper.toDtoCollection(guestService.getAllGuests());
    }

    @GetMapping(value = "/search", params = {"id"})
    public ResponseEntity<GuestDto> findById(@Valid
                                             @Positive(message = "UserID must be positive")
                                             @RequestParam("id") long id) {
        return ResponseEntity.ok(mapper.toDto(guestService.getGuestById(id)));
    }

    @GetMapping(value = "/search", params = {"email"})
    public ResponseEntity<GuestDto> findByEmail(@Valid
                                                @RequestParam("email")
                                                @NotNull(message = "The given email cannot be null!")
                                                @NotEmpty(message = "The given email cannot be empty!")
                                                @NotBlank(message = "The given email cannot be blank!")
                                                String email) {
        return ResponseEntity.ok(mapper.toDto(guestService.getGuestByEmail(email)));
    }


    @GetMapping(value = "/search", params = {"name", "surname"})
    public ResponseEntity<List<GuestDto>> findByNameAndSurname(@Valid
                                                               @RequestParam("name")
                                                               @NotNull(message = "The given name cannot be null!")
                                                               @NotEmpty(message = "The given name cannot be empty!")
                                                               @NotBlank(message = "The given name cannot be blank!")
                                                               String name,
                                                               @Valid
                                                               @RequestParam("surname")
                                                               @NotNull(message = "The given surname cannot be null!")
                                                               @NotEmpty(message = "The given surname cannot be empty!")
                                                               @NotBlank(message = "The given surname cannot be blank!")
                                                               String surname) {
        return ResponseEntity.ok(mapper.toDtoCollection(guestService.getGuestByNameAndSurname(name, surname)));
    }

    @GetMapping(value = "/search", params = {"event_id"})
    public ResponseEntity<List<GuestDto>> findByEventId(@Valid
                                                        @RequestParam("event_id")
                                                        @Positive(message = "The given event id cannot be 0 or less!")
                                                        long eventId) {
        return ResponseEntity.ok(mapper.toDtoCollection(guestService.getGuestByEventId(eventId)));
    }

    @GetMapping(value = "/search", params = {"guest_type"})
    public ResponseEntity<List<GuestDto>> findByGuestType(@Valid
                                                          @RequestParam("guest_type")
                                                          @NotEmpty(message = "The given guest type cannot be empty!")
                                                          @NotBlank(message = "The given guest type cannot be blank!")
                                                          @NotNull(message = "The given guest type cannot be null!")
                                                          String guestType) {
        return ResponseEntity.ok(
            mapper.toDtoCollection(guestService.getGuestsByGuestType(GuestType.valueOf(guestType.toUpperCase()))));
    }

    @GetMapping(value = "/search", params = {"attendance_type"})
    public ResponseEntity<List<GuestDto>> findByAttendanceType(@Valid
                                                               @RequestParam("attendance_type")
                                                               @NotEmpty(message = "The given attendance type cannot be empty!")
                                                               @NotBlank(message = "The given attendance type cannot be blank!")
                                                               @NotNull(message = "The given attendance type cannot be null!")
                                                               String attendanceType) {
        return ResponseEntity.ok(mapper.toDtoCollection(
            guestService.getGuestsBytAttendanceType(AttendanceType.valueOf(attendanceType.toUpperCase()))));
    }


    @PutMapping(value = "/set", params = {"guest_id"})
    public boolean setGuestByGuestId(@Valid
                                     @RequestParam("guest_id")
                                     @Positive(message = "The guest id must be positive!")
                                     long guestId,
                                     @RequestBody
                                     @NotNull(message = "The given guest dto cannot be null!")
                                     GuestDto guestDto) {
        return guestService.setGuestByGuestId(guestId, guestDto);
    }

    @DeleteMapping(value = "/delete", params = {"deleted", "id"})
    public boolean deleteGuest(@RequestParam("deleted")
                               boolean deleted,
                               @Positive(message = "The given ID cannot be less than zero!")
                               @RequestParam("id")
                               long guestId) {
        return guestService.delete(deleted, guestId);
    }
}