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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Validated
@AllArgsConstructor
public class GuestService {
    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    public long addGuest(@NotNull(message = "The given guest cannot be null!") Guest guest) {
        if (validateForExistingGuest(guest)) {
            return guestRepository.save(guest).getId();
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

    public List<Guest> getGuestByNameAndSurname(@NotNull(message = "The given name cannot be null!")
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

    public List<Guest> getGuestByEventId(@Positive(message = "The given id cannot be 0 or less!") long id) {
        return guestRepository.findGuestByEventIdEquals(id).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();
    }

    public List<Guest> getGuestsByGuestType(@NotNull(message = "The given guest type cannot be null!")
                                            GuestType guestType) {

        return guestRepository.findGuestsByGuestTypeEquals(guestType).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();
    }

    public List<Guest> getGuestsBytAttendanceType(@NotNull(message = "The given attendance type cannot be null!")
                                                  AttendanceType attendanceType) {

        return guestRepository.findGuestsByAttendanceTypeEquals(attendanceType).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();
    }

    public boolean updateName(@NotNull(message = "The name cannot be null!")
                              @NotEmpty(message = "The name cannot be empty!")
                              @NotBlank(message = "The name cannot be blank!")
                              String name,
                              @Positive(message = "The given ID cannot be less than zero!")
                              long guestId) {
        Guest guest = guestRepository.findGuestByIdEquals(guestId);

        if (guest != null && !guest.isDeleted()) {
            guest.setName(name);
            guestRepository.save(guest);
            return true;
        }

        return false;
    }

    public boolean updateSurname(@NotNull(message = "The surname cannot be null!")
                                 @NotEmpty(message = "The surname cannot be empty!")
                                 @NotBlank(message = "The surname cannot be blank!")
                                 String surname,
                                 @Positive(message = "The given ID cannot be less than zero!")
                                 long guestId) {
        Guest guest = guestRepository.findGuestByIdEquals(guestId);

        if (guest != null && !guest.isDeleted()) {
            guest.setSurname(surname);
            guestRepository.save(guest);
            return true;
        }

        return false;
    }

    public boolean updateEmail(@NotNull(message = "The email cannot be null!")
                               @NotEmpty(message = "The email cannot be empty!")
                               @NotBlank(message = "The email cannot be blank!")
                               String email,
                               @Positive(message = "The given ID cannot be less than zero!")
                               long guestId) {
        Guest guest = guestRepository.findGuestByIdEquals(guestId);

        if (guest != null && !guest.isDeleted()) {
            guest.setEmail(email);
            guestRepository.save(guest);
            return true;
        }

        return false;
    }

    public boolean updateGuestType(@NotNull(message = "The guest type cannot be null!")
                                   GuestType guestType,
                                   @Positive(message = "The given ID cannot be less than zero!")
                                   long guestId) {
        Guest guest = guestRepository.findGuestByIdEquals(guestId);

        if (guest != null && !guest.isDeleted()) {
            guest.setGuestType(guestType);
            guestRepository.save(guest);
            return true;
        }

        return false;
    }

    public boolean updateAttendanceType(@NotNull(message = "The attendance type cannot be null!")
                                        AttendanceType attendanceType,
                                        @Positive(message = "The given ID cannot be less than zero!")
                                        long guestId) {
        Guest guest = guestRepository.findGuestByIdEquals(guestId);

        if (guest != null && !guest.isDeleted()) {
            guest.setAttendanceType(attendanceType);
            guestRepository.save(guest);
            return true;
        }

        return false;
    }

    public boolean updateInvitationIsSent(boolean isSent,
                                          @Positive(message = "The given ID cannot be less than zero!") long guestId) {
        Guest guest = guestRepository.findGuestByIdEquals(guestId);

        if (guest != null && !guest.isDeleted()) {
            guest.setInvitationSent(isSent);
            guestRepository.save(guest);
            return true;
        }

        return false;
    }

    public boolean updateCreatedBy(@NotNull(message = "The createdBy cannot be null!")
                                   @NotEmpty(message = "The createdBy cannot be empty!")
                                   @NotBlank(message = "The createdBy cannot be blank!")
                                   String createdBy,
                                   @Positive(message = "The given ID cannot be less than zero!")
                                   long guestId) {
        Guest guest = guestRepository.findGuestByIdEquals(guestId);

        if (guest != null && !guest.isDeleted()) {
            guest.setCreatedBy(createdBy);
            guestRepository.save(guest);
            return true;
        }

        return false;
    }

    public boolean updateCreationTime(
        @NotNull(message = "The creation date cannot be null!") LocalDateTime creationTime,
        @Positive(message = "The given ID cannot be less than zero!") long guestId) {
        Guest guest = guestRepository.findGuestByIdEquals(guestId);

        if (guest != null && !guest.isDeleted()) {
            guest.setCreationTime(creationTime);
            guestRepository.save(guest);
            return true;
        }

        return false;
    }

    public boolean updateUpdatedBy(@NotNull(message = "The updatedBy cannot be null!")
                                   @NotEmpty(message = "The updatedBy cannot be empty!")
                                   @NotBlank(message = "The updatedBy cannot be blank!")
                                   String updatedBy,
                                   @Positive(message = "The given ID cannot be less than zero!")
                                   long guestId) {
        Guest guest = guestRepository.findGuestByIdEquals(guestId);

        if (guest != null && !guest.isDeleted()) {
            guest.setUpdatedBy(updatedBy);
            guestRepository.save(guest);
            return true;
        }

        return false;
    }

    public boolean updateLastUpdatedTime(@NotNull(message = "The last updated time cannot be null!")
                                         LocalDateTime lastUpdatedTime,
                                         @Positive(message = "The given ID cannot be less than zero!")
                                         long guestId) {
        Guest guest = guestRepository.findGuestByIdEquals(guestId);

        if (guest != null && !guest.isDeleted()) {
            guest.setLastUpdatedTime(lastUpdatedTime);
            guestRepository.save(guest);
            return true;
        }

        return false;
    }

    public boolean delete(boolean deleted,
                          @Positive(message = "The given ID cannot be less than zero!") long guestId) {
        Guest guest = guestRepository.findGuestByIdEquals(guestId);

        if (guest != null && !guest.isDeleted()) {
            guest.setDeleted(deleted);
            guestRepository.save(guest);
            return true;
        }

        return false;
    }
}