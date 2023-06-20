package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.dto.GuestDto;
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

import java.time.LocalDateTime;
import java.util.List;

@Service
@Validated
@AllArgsConstructor
public class GuestService {
    private final GuestRepository guestRepository;

    public long addGuest(@NotNull(message = "The given guest cannot be null!")
                         Guest guestToSave) {

        return guestRepository.save(guestToSave).getId();
    }

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
        validateForDeletedGuest(guest);

        return guest;
    }

    public Guest getGuestByEmail(@NotNull(message = "The given email cannot be null!")
                                 @NotEmpty(message = "The given email cannot be empty!")
                                 @NotBlank(message = "The given email cannot be blank!")
                                 String email) {

        Guest guest = guestRepository.findGuestByEmailEquals(email);
        validateGuest(guest);
        validateForDeletedGuest(guest);

        return guest;
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
        List<Guest> guests = guestRepository.findGuestByAssociatedEventIdEquals(id).parallelStream()
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

    public boolean setGuestByGuestId(@Positive(message = "The guest id must be positive!")
                                     long guestId,
                                     @NotNull(message = "The given guest dto cannot be null!")
                                     GuestDto guestDto) {
        Guest guest = getGuestById(guestId);
        validateGuest(guest);
        validateForDeletedGuest(guest);

        Guest newGuestToSave = updateFields(guestDto, guest);
        newGuestToSave.setUpdatedBy("b");
        newGuestToSave.setLastUpdatedTime(LocalDateTime.now());

        guestRepository.save(newGuestToSave);

        return true;
    }

    public boolean delete(boolean deleted,
                          @Positive(message = "The given ID cannot be less than zero!") long guestId) {
        Guest guest = guestRepository.findGuestByIdEquals(guestId);
        validateGuest(guest);
        validateForDeletedGuest(guest);

        guest.setDeleted(deleted);
        guestRepository.save(guest);
        return true;
    }

    private Guest updateFields(GuestDto guestDto, Guest newGuestToSave) {
        if (guestDto.getName() != null && !guestDto.getName().equals(newGuestToSave.getName())) {
            newGuestToSave.setName(guestDto.getName());
        }
        if (guestDto.getSurname() != null && !guestDto.getSurname().equals(newGuestToSave.getSurname())) {
            newGuestToSave.setSurname(guestDto.getSurname());
        }
        if (guestDto.getEmail() != null && !guestDto.getEmail().equals(newGuestToSave.getEmail())) {
            newGuestToSave.setEmail(guestDto.getEmail());
        }
        if (guestDto.getGuestType() != null && !guestDto.getGuestType().equals(newGuestToSave.getGuestType())) {
            newGuestToSave.setGuestType(guestDto.getGuestType());
        }
        if (guestDto.getAttendanceType() != null &&
            !guestDto.getAttendanceType().equals(newGuestToSave.getAttendanceType())) {

            newGuestToSave.setAttendanceType(guestDto.getAttendanceType());
        }
        if (guestDto.isInvitationSent() != newGuestToSave.isInvitationSent()) {
            newGuestToSave.setInvitationSent(guestDto.isInvitationSent());
        }

        return newGuestToSave;
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

    private void validateForDeletedGuest(Guest guest) {
        if (guest.isDeleted()) {
            throw new MethodNotAllowed("The current record has already been deleted!");
        }
    }
}