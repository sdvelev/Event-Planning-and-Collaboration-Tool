package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.dto.EventDto;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.repository.EventRepository;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(@NotNull(message = "Event cannot be null") Event eventToSave) {

        eventToSave.setCreationTime(LocalDateTime.now());
        eventToSave.setDeleted(false);

        return eventRepository.save(eventToSave);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll()
            .parallelStream()
            .filter(event -> !event.isDeleted())
            .collect(Collectors.toList());
    }

    public Optional<Event> getEventById(
        @NotNull(message = "Id cannot be null")
        @Positive(message = "Id must be positive")
        Long id) {

        Optional<Event> potentialEvent = eventRepository.findById(id);

        if (potentialEvent.isPresent() && !potentialEvent.get().isDeleted()) {
            return potentialEvent;
        }

        throw new ResourceNotFoundException("There is not an event with such an id");
    }

    public List<Event> getEventsByName(
        @NotNull(message = "Event name cannot be null")
        @NotBlank(message = "Event name cannot be blank")
        String name) {

        List<Event> allEventsWithName = eventRepository.getEventsByName(name);

        return allEventsWithName.parallelStream()
            .filter(event -> !event.isDeleted())
            .collect(Collectors.toList());
    }

    public List<Event> getEventsByDate(
        @NotNull(message = "Date cannot be null")
        LocalDateTime date) {

        List<Event> allEventsWithName = eventRepository.getEventsByDate(date);

        return allEventsWithName.parallelStream()
            .filter(event -> !event.isDeleted())
            .collect(Collectors.toList());
    }

    public List<Event> getEventsByLocation(
        @NotNull(message = "Location cannot be null")
        @NotBlank(message = "Location cannot be blank")
        String location) {

        List<Event> allEventsWithName = eventRepository.getEventsByLocation(location);

        return allEventsWithName.parallelStream()
            .filter(event -> !event.isDeleted())
            .collect(Collectors.toList());
    }

    public List<Event> getEventsByCreatedBy(
        @NotNull(message = "Created by name cannot be null")
        @NotBlank(message = "Created by name cannot be blank")
        String createdBy) {

        List<Event> allEventsWithName = eventRepository.getEventsByCreatedBy(createdBy);

        return allEventsWithName.parallelStream()
            .filter(event -> !event.isDeleted())
            .collect(Collectors.toList());
    }

    public boolean setEventById(
        @NotNull(message = "Event record cannot be null")
        EventDto eventFieldsToChange,
        @NotNull(message = "Event id cannot be null")
        @Positive(message = "Event id must be positive")
        Long eventId) {

        Optional<Event> optionalEventToUpdate = eventRepository.findById(eventId);

        if (optionalEventToUpdate.isPresent() && !optionalEventToUpdate.get().isDeleted()) {

            Event eventToUpdate = setEventNonNullFields(eventFieldsToChange, optionalEventToUpdate.get());;
            eventToUpdate.setLastUpdatedTime(LocalDateTime.now());
            eventRepository.save(eventToUpdate);
            return true;
        }

        throw new ResourceNotFoundException("There is not an event with such an id");
    }

    public Event deleteEvent(
        @NotNull(message = "Id cannot be null")
        @Positive(message = "Id must be positive.")
        Long eventToDeleteId) {

        Optional<Event> optionalEventToDelete = eventRepository.findById(eventToDeleteId);

        if (optionalEventToDelete.isPresent() && !optionalEventToDelete.get().isDeleted()) {

            Event eventToDelete = optionalEventToDelete.get();
            eventToDelete.setLastUpdatedTime(LocalDateTime.now());
            eventToDelete.setDeleted(true);
            eventRepository.save(eventToDelete);
            return eventToDelete;
        }

        throw new ResourceNotFoundException("There is not an event with such an id");
    }

    private Event setEventNonNullFields(EventDto eventFieldsToChange, Event eventToUpdate) {

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

        return eventToUpdate;
    }
}