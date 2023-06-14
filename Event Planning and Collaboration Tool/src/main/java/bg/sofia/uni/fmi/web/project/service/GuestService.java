package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.enums.AttendanceType;
import bg.sofia.uni.fmi.web.project.enums.GuestType;
import bg.sofia.uni.fmi.web.project.model.Guest;
import bg.sofia.uni.fmi.web.project.repository.GuestRepository;
import bg.sofia.uni.fmi.web.project.validation.MethodNotAllowed;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@AllArgsConstructor
public class GuestService {
    private final GuestRepository guestRepository;

    public long addGuest(@NotNull(message = "The given guest cannot be null!")
                         Guest guestToSave) {
        Guest guest = guestRepository.save(guestToSave);
        checkForSaveException(guest);

        return guest.getId();
    }

//    private boolean validateForExistingGuestByNameAndSurname(Guest guest) {
//        long foundGuests = guestRepository.findGuestByNameAndSurnameEquals(guest.getName(), guest.getSurname()).stream()
//            .filter(g -> g.equals(guest))
//            .count();
//
//        return foundGuests == 0;
//    }
//
//    private boolean validateForExistingGuestByEventId(Guest guest) {
//        long foundGuests = guestRepository.findGuestByEventIdEquals(guest.getEvent().getId()).stream()
//            .filter(g -> g.equals(guest))
//            .count();
//
//        return foundGuests == 0;
//    }

    public List<Guest> getAllGuests() {
        List<Guest> guests = guestRepository.findAll().parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();

        validateGuestsList(guests);
        return guests;
    }

    public Guest getGuestById(@Positive(message = "The given id cannot be 0 or less!") long id) {
        Guest guest = guestRepository.findGuestByIdEquals(id);
        validateGuest(guest);

        if (!guest.isDeleted()) {
            return guest;
        }

        throw new MethodNotAllowed("The current record has already been deleted!");
    }

    public Guest getGuestByEmail(@NotNull(message = "The given email cannot be null!")
                                 @NotEmpty(message = "The given email cannot be empty!")
                                 @NotBlank(message = "The given email cannot be blank!")
                                 String email) {

        Guest guest = guestRepository.findGuestByEmailEquals(email);
        validateGuest(guest);

        if (!guest.isDeleted()) {
            return guest;
        }

        throw new MethodNotAllowed("The current record has already been deleted!");
    }

    public List<Guest> getGuestByNameAndSurname(@NotNull(message = "The given name cannot be null!")
                                                @NotEmpty(message = "The given name cannot be empty!")
                                                @NotBlank(message = "The given name cannot be blank!")
                                                String name,
                                                @NotNull(message = "The given surname cannot be null!")
                                                @NotEmpty(message = "The given surname cannot be empty!")
                                                @NotBlank(message = "The given surname cannot be blank!")
                                                String surname) {

        List<Guest> guests = guestRepository.findGuestByNameAndSurnameEquals(name, surname).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();

        validateGuestsList(guests);
        return guests;
    }

    public List<Guest> getGuestByEventId(@Positive(message = "The given id cannot be 0 or less!") long id) {
        List<Guest> guests = guestRepository.findGuestByEventIdEquals(id).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();

        validateGuestsList(guests);
        return guests;
    }

    public List<Guest> getGuestsByGuestType(@NotNull(message = "The given guest type cannot be null!")
                                            GuestType guestType) {

        List<Guest> guests = guestRepository.findGuestsByGuestTypeEquals(guestType).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();

        validateGuestsList(guests);
        return guests;
    }

    public List<Guest> getGuestsBytAttendanceType(@NotNull(message = "The given attendance type cannot be null!")
                                                  AttendanceType attendanceType) {

        List<Guest> guests = guestRepository.findGuestsByAttendanceTypeEquals(attendanceType).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();

        validateGuestsList(guests);
        return guests;
    }

//    public boolean updateName(@NotNull(message = "The name cannot be null!")
//                              @NotEmpty(message = "The name cannot be empty!")
//                              @NotBlank(message = "The name cannot be blank!")
//                              String name,
//                              @Positive(message = "The given ID cannot be less than zero!")
//                              long guestId) {
//        Guest guest = guestRepository.findGuestByIdEquals(guestId);
//
//        if (guest != null && !guest.isDeleted()) {
//            guest.setName(name);
//            guestRepository.save(guest);
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean updateSurname(@NotNull(message = "The surname cannot be null!")
//                                 @NotEmpty(message = "The surname cannot be empty!")
//                                 @NotBlank(message = "The surname cannot be blank!")
//                                 String surname,
//                                 @Positive(message = "The given ID cannot be less than zero!")
//                                 long guestId) {
//        Guest guest = guestRepository.findGuestByIdEquals(guestId);
//
//        if (guest != null && !guest.isDeleted()) {
//            guest.setSurname(surname);
//            guestRepository.save(guest);
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean updateEmail(@NotNull(message = "The email cannot be null!")
//                               @NotEmpty(message = "The email cannot be empty!")
//                               @NotBlank(message = "The email cannot be blank!")
//                               String email,
//                               @Positive(message = "The given ID cannot be less than zero!")
//                               long guestId) {
//        Guest guest = guestRepository.findGuestByIdEquals(guestId);
//
//        if (guest != null && !guest.isDeleted()) {
//            guest.setEmail(email);
//            guestRepository.save(guest);
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean updateGuestType(@NotNull(message = "The guest type cannot be null!")
//                                   GuestType guestType,
//                                   @Positive(message = "The given ID cannot be less than zero!")
//                                   long guestId) {
//        Guest guest = guestRepository.findGuestByIdEquals(guestId);
//
//        if (guest != null && !guest.isDeleted()) {
//            guest.setGuestType(guestType);
//            guestRepository.save(guest);
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean updateAttendanceType(@NotNull(message = "The attendance type cannot be null!")
//                                        AttendanceType attendanceType,
//                                        @Positive(message = "The given ID cannot be less than zero!")
//                                        long guestId) {
//        Guest guest = guestRepository.findGuestByIdEquals(guestId);
//
//        if (guest != null && !guest.isDeleted()) {
//            guest.setAttendanceType(attendanceType);
//            guestRepository.save(guest);
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean updateInvitationIsSent(boolean isSent,
//                                          @Positive(message = "The given ID cannot be less than zero!") long guestId) {
//        Guest guest = guestRepository.findGuestByIdEquals(guestId);
//
//        if (guest != null && !guest.isDeleted()) {
//            guest.setInvitationSent(isSent);
//            guestRepository.save(guest);
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean updateCreatedBy(@NotNull(message = "The createdBy cannot be null!")
//                                   @NotEmpty(message = "The createdBy cannot be empty!")
//                                   @NotBlank(message = "The createdBy cannot be blank!")
//                                   String createdBy,
//                                   @Positive(message = "The given ID cannot be less than zero!")
//                                   long guestId) {
//        Guest guest = guestRepository.findGuestByIdEquals(guestId);
//
//        if (guest != null && !guest.isDeleted()) {
//            guest.setCreatedBy(createdBy);
//            guestRepository.save(guest);
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean updateCreationTime(
//        @NotNull(message = "The creation date cannot be null!") LocalDateTime creationTime,
//        @Positive(message = "The given ID cannot be less than zero!") long guestId) {
//        Guest guest = guestRepository.findGuestByIdEquals(guestId);
//
//        if (guest != null && !guest.isDeleted()) {
//            guest.setCreationTime(creationTime);
//            guestRepository.save(guest);
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean updateUpdatedBy(@NotNull(message = "The updatedBy cannot be null!")
//                                   @NotEmpty(message = "The updatedBy cannot be empty!")
//                                   @NotBlank(message = "The updatedBy cannot be blank!")
//                                   String updatedBy,
//                                   @Positive(message = "The given ID cannot be less than zero!")
//                                   long guestId) {
//        Guest guest = guestRepository.findGuestByIdEquals(guestId);
//
//        if (guest != null && !guest.isDeleted()) {
//            guest.setUpdatedBy(updatedBy);
//            guestRepository.save(guest);
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean updateLastUpdatedTime(@NotNull(message = "The last updated time cannot be null!")
//                                         LocalDateTime lastUpdatedTime,
//                                         @Positive(message = "The given ID cannot be less than zero!")
//                                         long guestId) {
//        Guest guest = guestRepository.findGuestByIdEquals(guestId);
//
//        if (guest != null && !guest.isDeleted()) {
//            guest.setLastUpdatedTime(lastUpdatedTime);
//            guestRepository.save(guest);
//            return true;
//        }
//
//        return false;
//    }

    public boolean delete(boolean deleted,
                          @Positive(message = "The given ID cannot be less than zero!") long guestId) {
        Guest guest = guestRepository.findGuestByIdEquals(guestId);
        validateGuest(guest);

        if (!guest.isDeleted()) {
            guest.setDeleted(deleted);
            guestRepository.save(guest);
            return true;
        }

        throw new MethodNotAllowed("The current record has already been deleted!");
    }

    private void validateGuest(Guest guest) {
        if (guest == null) {
            throw new ResourceNotFoundException("There is no such guest in the database!");
        }
    }

    private void validateGuestsList(List<Guest> guests) {
        if (guests == null) {
            throw new ResourceNotFoundException("There are no such guests in the database or have been deleted!");
        }
    }

    private void checkForSaveException(Guest guest) {
        if (guest == null) {
            throw new RuntimeException("There was problem while saving the guest in the database!");
        }
    }
}