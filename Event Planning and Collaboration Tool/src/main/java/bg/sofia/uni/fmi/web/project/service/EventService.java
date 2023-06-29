package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.dto.EventDto;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.User;
import bg.sofia.uni.fmi.web.project.repository.EventRepository;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(@NotNull(message = "The provided event cannot be null") Event eventToSave,
                             @NotNull(message = "The user who makes changes cannot be null")
                             User userToMakeChanges) {

        eventToSave.setCreationTime(LocalDateTime.now());
        eventToSave.setCreatedBy(userToMakeChanges.getUsername());

        return eventRepository.save(eventToSave);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAllByDeletedFalse();
    }

    public Optional<Event> getEventById(
        @NotNull(message = "The provided event id cannot be null")
        @Positive(message = "The provided event id must be positive")
        Long id) {

        Optional<Event> potentialEvent = eventRepository.findByIdAndDeletedFalse(id);

        if (potentialEvent.isPresent()) {
            return potentialEvent;
        }

        throw new ResourceNotFoundException("There is not an event with such an id");
    }

    public List<Event> getEventsByName(
        @NotNull(message = "The provided event name cannot be null")
        @NotBlank(message = "The provided event name cannot be blank")
        String name) {

        return eventRepository.getEventsByNameAndDeletedFalse(name);
    }

    public List<Event> getEventsByDate(
        @NotNull(message = "The provided event date cannot be null")
        LocalDateTime date) {

        return eventRepository.getEventsByDateAndDeletedFalse(date);
    }

    public List<Event> getEventsByLocation(
        @NotNull(message = "The provided event location cannot be null")
        @NotBlank(message = "The provided event location cannot be blank")
        String location) {

        return eventRepository.getEventsByLocationAndDeletedFalse(location);
    }

    public List<Event> getEventsByCreatedBy(
        @NotNull(message = "The provided created by name cannot be null")
        @NotBlank(message = "The provided created by name cannot be blank")
        String createdBy) {

        return eventRepository.getEventsByCreatedByAndDeletedFalse(createdBy);
    }

    public boolean setEventById(
        @NotNull(message = "The provided event dto cannot be null")
        EventDto eventFieldsToChange,
        @NotNull(message = "The provided event id cannot be null")
        @Positive(message = "The provided event id must be positive")
        Long eventId,
        @NotNull(message = "The user who makes changes cannot be null")
        User userToMakeChanges) {

        Optional<Event> optionalEventToUpdate = eventRepository.findByIdAndDeletedFalse(eventId);

        if (optionalEventToUpdate.isPresent()) {

            Event eventToUpdate = setEventNonNullFields(eventFieldsToChange, optionalEventToUpdate.get());;
            eventToUpdate.setLastUpdatedTime(LocalDateTime.now());
            eventToUpdate.setUpdatedBy(userToMakeChanges.getUsername());
            eventRepository.save(eventToUpdate);
            return true;
        }

        throw new ResourceNotFoundException("There is not an event with such an id");
    }

    public Event deleteEvent(
        @NotNull(message = "The provided event id cannot be null")
        @Positive(message = "The provided event id must be positive.")
        Long eventToDeleteId,
        @NotNull(message = "The user who makes changes cannot be null")
        User userToMakeChanges) {

        Optional<Event> optionalEventToDelete = eventRepository.findByIdAndDeletedFalse(eventToDeleteId);

        if (optionalEventToDelete.isPresent()) {

            Event eventToDelete = optionalEventToDelete.get();
            eventToDelete.setLastUpdatedTime(LocalDateTime.now());
            eventToDelete.setUpdatedBy(userToMakeChanges.getUsername());
            eventToDelete.setDeleted(true);
            eventRepository.save(eventToDelete);
            return eventToDelete;
        }

        throw new ResourceNotFoundException("There is not an event with such an id");
    }

    private Event setEventNonNullFields(
        @NotNull(message = "The provided event dto cannot be null")
        EventDto eventFieldsToChange,
        @NotNull(message = "The provided event cannot be null")
        Event eventToUpdate) {

        if (eventFieldsToChange.getName() != null) {
            eventToUpdate.setName(eventFieldsToChange.getName());
        }

        if (eventFieldsToChange.getDate() != null) {
            eventToUpdate.setDate(eventFieldsToChange.getDate());
        }

        if (eventFieldsToChange.getLocation() != null) {
            eventToUpdate.setLocation(eventFieldsToChange.getLocation());
        }

        if (eventFieldsToChange.getDescription() != null) {
            eventToUpdate.setDescription(eventFieldsToChange.getDescription());
        }

        if (eventFieldsToChange.getPictureLink() != null) {
            eventToUpdate.setPictureLink(eventFieldsToChange.getPictureLink());
        }

        return eventToUpdate;
    }
}