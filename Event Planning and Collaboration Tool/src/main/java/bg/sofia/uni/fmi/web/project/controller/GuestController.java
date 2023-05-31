package bg.sofia.uni.fmi.web.project.controller;

import bg.sofia.uni.fmi.web.project.dto.GuestDto;
import bg.sofia.uni.fmi.web.project.enums.AttendanceType;
import bg.sofia.uni.fmi.web.project.enums.GuestType;
import bg.sofia.uni.fmi.web.project.mapper.GuestMapper;
import bg.sofia.uni.fmi.web.project.service.GuestService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("guests")
@AllArgsConstructor
public class GuestController {
    private final GuestService guestService;
    private final GuestMapper mapper;

    @PostMapping
    public long addGuest(@RequestBody @Valid GuestDto guestDto) {
        return guestService.addGuest(mapper.toEntity(guestDto));
    }

    @GetMapping
    public List<GuestDto> getAllGuests() {
        return mapper.toDtoCollection(guestService.getAllGuests());
    }

    @GetMapping(value = "/search", params = {"id"})
    public ResponseEntity<GuestDto> findById(@RequestParam("id") long id) {
        return ResponseEntity.ok(mapper.toDto(guestService.getGuestById(id)));
    }

    @GetMapping(value = "/search", params = {"email"})
    public ResponseEntity<GuestDto> findByEmail(@RequestParam("email")
                                                @NotNull(message = "The given email cannot be null!")
                                                @NotEmpty(message = "The given email cannot be empty!")
                                                @NotBlank(message = "The given email cannot be blank!")
                                                String email) {
        return ResponseEntity.ok(mapper.toDto(guestService.getGuestByEmail(email)));
    }


    @GetMapping(value = "/search", params = {"name", "surname"})
    public ResponseEntity<List<GuestDto>> findbyNameAndSurname(@RequestParam("name")
                                                               @NotNull(message = "The given name cannot be null!")
                                                               @NotEmpty(message = "The given name cannot be empty!")
                                                               @NotBlank(message = "The given name cannot be blank!")
                                                               String name,
                                                               @RequestParam("surname")
                                                               @NotNull(message = "The given surname cannot be null!")
                                                               @NotEmpty(message = "The given surname cannot be empty!")
                                                               @NotBlank(message = "The given surname cannot be blank!")
                                                               String surname) {
        return ResponseEntity.ok(mapper.toDtoCollection(guestService.getGuestByNameAndSurname(name, surname)));
    }

    @GetMapping(value = "/search", params = {"event_id"})
    public ResponseEntity<List<GuestDto>> findByEventId(@RequestParam("event_id")
                                                        @Positive(message = "The given id cannot be 0 or less!")
                                                        long eventId) {
        return ResponseEntity.ok(mapper.toDtoCollection(guestService.getGuestByEventId(eventId)));
    }

    @GetMapping(value = "/search", params = {"guest_type"})
    public ResponseEntity<List<GuestDto>> findByGuestType(@RequestParam("guest_type")
                                                          @NotNull(message = "The given guest type cannot be null!")
                                                          GuestType guestType) {
        return ResponseEntity.ok(mapper.toDtoCollection(guestService.getGuestsByGuestType(guestType)));
    }

    @GetMapping(value = "/search", params = {"attendance_type"})
    public ResponseEntity<List<GuestDto>> findByAttendanceType(@RequestParam("attendance_type")
                                                               @NotNull(message = "The given attendance type cannot be null!")
                                                               AttendanceType attendanceType) {
        return ResponseEntity.ok(mapper.toDtoCollection(guestService.getGuestsBytAttendanceType(attendanceType)));
    }


    @PatchMapping(value = "/settings", params = {"name", "id"})
    public boolean updateName(@NotNull(message = "The name cannot be null!")
                              @NotEmpty(message = "The name cannot be empty!")
                              @NotBlank(message = "The name cannot be blank!")
                              @RequestParam("name")
                              String name,
                              @RequestParam("id")
                              @Positive(message = "The given ID cannot be less than zero!")
                              long guestId) {
        return guestService.updateName(name, guestId);
    }

    @PatchMapping(value = "/settings", params = {"surnmae", "id"})
    public boolean updateSurname(@NotNull(message = "The surname cannot be null!")
                                 @NotEmpty(message = "The surname cannot be empty!")
                                 @NotBlank(message = "The surname cannot be blank!")
                                 @RequestParam("surname")
                                 String surname,
                                 @Positive(message = "The given ID cannot be less than zero!")
                                 @RequestParam("id")
                                 long guestId) {
        return guestService.updateSurname(surname, guestId);
    }

    @PatchMapping(value = "/settings", params = {"email", "id"})
    public boolean updateEmail(@NotNull(message = "The email cannot be null!")
                               @NotEmpty(message = "The email cannot be empty!")
                               @NotBlank(message = "The email cannot be blank!")
                               @RequestParam("email")
                               String email,
                               @Positive(message = "The given ID cannot be less than zero!")
                               @RequestParam("id")
                               long guestId) {
        return guestService.updateEmail(email, guestId);
    }

    @PatchMapping(value = "/settings", params = {"guest_type", "id"})
    public boolean updateGuestType(@NotNull(message = "The guest type cannot be null!")
                                   @RequestParam("guest_type")
                                   GuestType guestType,
                                   @Positive(message = "The given ID cannot be less than zero!")
                                   @RequestParam("id")
                                   long guestId) {
        return guestService.updateGuestType(guestType, guestId);
    }

    @PatchMapping(value = "/settings", params = {"attendance_type", "id"})
    public boolean updateAttendanceType(@NotNull(message = "The attendance type cannot be null!")
                                        @RequestParam("attendance_type")
                                        AttendanceType attendanceType,
                                        @Positive(message = "The given ID cannot be less than zero!")
                                        @RequestParam("id")
                                        long guestId) {
        return guestService.updateAttendanceType(attendanceType, guestId);
    }

    @PatchMapping(value = "/settings", params = {"is_sent", "id"})
    public boolean updateInvitationIsSent(@RequestParam("is_sent")
                                          boolean isSent,
                                          @Positive(message = "The given ID cannot be less than zero!")
                                          @RequestParam("id")
                                          long guestId) {
        return guestService.updateInvitationIsSent(isSent, guestId);
    }

//    @PatchMapping(value = "/settings", params = {"is_sent", "id"})
//    public boolean updateEvent(@NotNull(message = "The event cannot be null!")
//                                   long event,
//                               @Positive(message = "The given ID cannot be less than zero!")
//                               long guestId) {
//        return guestService.updateInvitationIsSent(isSent, guestId);
//    }

    @PatchMapping(value = "/settings", params = {"created_by", "id"})
    public boolean updateCreatedBy(@RequestParam("created_by")
                                   @NotNull(message = "The createdBy cannot be null!")
                                   @NotEmpty(message = "The createdBy cannot be empty!")
                                   @NotBlank(message = "The createdBy cannot be blank!")
                                   String createdBy,
                                   @Positive(message = "The given ID cannot be less than zero!")
                                   @RequestParam("id")
                                   long guestId) {
        return guestService.updateCreatedBy(createdBy, guestId);
    }

    @PatchMapping(value = "/settings", params = {"creation_time", "id"})
    public boolean updateCreationTime(@NotNull(message = "The creation_time cannot be null!")
                                      @NotEmpty(message = "The creation_time cannot be empty!")
                                      @NotBlank(message = "The creation_time cannot be blank!")
                                      @RequestParam("created_by")
                                      String creationTime,
                                      @Positive(message = "The given ID cannot be less than zero!")
                                      @RequestParam("id")
                                      long guestId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return guestService.updateCreationTime(LocalDateTime.parse(creationTime, formatter), guestId);
    }

    @PatchMapping(value = "/settings", params = {"created_by", "id"})
    public boolean updateUpdatedBy(@NotNull(message = "The updatedBy cannot be null!")
                                   @NotEmpty(message = "The updatedBy cannot be empty!")
                                   @NotBlank(message = "The updatedBy cannot be blank!")
                                   @RequestParam("updated_by")
                                   String updatedBy,
                                   @Positive(message = "The given ID cannot be less than zero!")
                                   @RequestParam("id")
                                   long guestId) {
        return guestService.updateUpdatedBy(updatedBy, guestId);
    }

    @PatchMapping(value = "/settings", params = {"last_updated_time", "id"})
    public boolean updateLastUpdatedTime(@NotNull(message = "The creation_time cannot be null!")
                                         @NotEmpty(message = "The creation_time cannot be empty!")
                                         @NotBlank(message = "The creation_time cannot be blank!")
                                         @RequestParam("last_updated_time")
                                         String lastUpdatedTime,
                                         @Positive(message = "The given ID cannot be less than zero!")
                                         @RequestParam("id")
                                         long guestId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return guestService.updateLastUpdatedTime(LocalDateTime.parse(lastUpdatedTime, formatter), guestId);
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