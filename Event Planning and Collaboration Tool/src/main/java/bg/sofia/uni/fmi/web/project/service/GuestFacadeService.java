package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.enums.AttendanceType;
import bg.sofia.uni.fmi.web.project.enums.GuestType;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Guest;
import bg.sofia.uni.fmi.web.project.validation.ConflictException;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Validated
@AllArgsConstructor
public class GuestFacadeService {
    private final GuestService guestService;
    private final EventService eventService;

    @Transactional
    public long addGuest(@NotNull(message = "The given guest cannot be null!")
                         Guest guestToSave,
                         @NotNull(message = "The event id cannot be null!")
                         Long eventId) {
        validateForExistingGuest(guestToSave.getName(), guestToSave.getSurname(), eventId);

        Optional<Event> event = eventService.getEventById(eventId);
        validateEvent(event.get());

        guestToSave.setAssociatedEvent(event.get());

        guestToSave.setCreatedBy("a");
        guestToSave.setCreationTime(LocalDateTime.now());

        event.get().getAssociatedGuests().add(guestToSave);

        return guestService.addGuest(guestToSave);
    }

    private void validateEvent(Event event) {
        if (event == null) {
            throw new ResourceNotFoundException("There is no event with such id!");
        }
    }

    private void validateForExistingGuest(String name, String surname, long eventId) {
        if (!validateForExistingGuestByNameAndSurname(name, surname) && !validateForExistingGuestByEventId(eventId)) {
            throw new ConflictException("There is already such guest in the database!");
        }
    }

    private boolean validateForExistingGuestByNameAndSurname(String name, String surname) {
        return guestService.getGuestByNameAndSurname(name, surname).isEmpty();
    }

    private boolean validateForExistingGuestByEventId(long eventId) {
        return guestService.getGuestByEventId(eventId).isEmpty();
    }
}