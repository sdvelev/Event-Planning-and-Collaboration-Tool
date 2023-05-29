package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.enums.AttendanceType;
import bg.sofia.uni.fmi.web.project.enums.GuestType;
import bg.sofia.uni.fmi.web.project.mapper.GuestMapper;
import bg.sofia.uni.fmi.web.project.model.Guest;
import bg.sofia.uni.fmi.web.project.repository.GuestRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;

@Service
@Validated
public class GuestService {
    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    @Autowired
    public GuestService(GuestRepository guestRepository, GuestMapper guestMapper) {
        this.guestRepository = guestRepository;
        this.guestMapper = guestMapper;
    }

    public long addGuest(@NotNull(message = "The given guest cannot be null!") Guest guest) {
        if (validateForExistingGuest(guest)) {
            guestRepository.save(guest).getId();
        }

        return -1;
    }

    private boolean validateForExistingGuest(Guest guest) {
        long foundTasks = guestRepository.findGuestByNameAndSurnameEquals(guest.getName(), guest.getSurname()).stream()
            .filter(g -> g.equals(guest))
            .count();

        return foundTasks == 0;
    }

//    public List<Long> addGuests(List<Guest> guestList) {
//        return guestRepository.saveAll(guestList).stream()
//            .map(Guest::getId)
//            .toList();
//    }

    public List<Guest> getAllGuests() {
        return guestRepository.findAll().parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();
    }

    public Guest getGuestById(@Positive(message = "The given id cannot be 0 or less!") long id) {
        Guest guest = guestRepository.findGuestByIdEquals(id);

        if (guest != null && !guest.isDeleted()) {
            return guest;
        }

        return null;
    }

    public Guest getGuestByEmail(@NotNull(message = "The given email cannot be null!")
                                 @NotEmpty(message = "The given email cannot be empty!")
                                 @NotBlank(message = "The given email cannot be blank!")
                                 String email) {

        Guest guest = guestRepository.findGuestByEmailEquals(email);

        if (guest != null && !guest.isDeleted()) {
            return guest;
        }

        return null;
    }

    public Collection<Guest> getGuestByNameAndSurname(@NotNull(message = "The given name cannot be null!")
                                                      @NotEmpty(message = "The given name cannot be empty!")
                                                      @NotBlank(message = "The given name cannot be blank!")
                                                      String name,
                                                      @NotNull(message = "The given surname cannot be null!")
                                                      @NotEmpty(message = "The given surname cannot be empty!")
                                                      @NotBlank(message = "The given surname cannot be blank!")
                                                      String surname) {

        return guestRepository.findGuestByNameAndSurnameEquals(name, surname).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();
    }

    public Collection<Guest> getGuestByEventId(@Positive(message = "The given id cannot be 0 or less!") long id) {
        return guestRepository.findGuestByEventIdEquals(id).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();
    }

    public Collection<Guest> getGuestsByGuestType(@NotNull(message = "The given guest type cannot be null!")
                                                  GuestType guestType) {

        return guestRepository.findGuestsByGuestTypeEquals(guestType).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();
    }

    public Collection<Guest> getGuestsBytAttendanceType(@NotNull(message = "The given attendance type cannot be null!")
                                                        AttendanceType attendanceType) {

        return guestRepository.findGuestsByAttendanceTypeEquals(attendanceType).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();
    }
}