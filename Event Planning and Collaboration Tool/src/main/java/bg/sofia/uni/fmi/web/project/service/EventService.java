package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.dto.EventDto;
import bg.sofia.uni.fmi.web.project.mapper.EventMapper;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Participant;
import bg.sofia.uni.fmi.web.project.repository.EventRepository;
import bg.sofia.uni.fmi.web.project.validation.ApiBadRequest;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final ParticipantService participantService;

    @Autowired
    public EventService(EventRepository eventRepository, EventMapper eventMapper, ParticipantService participantService) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.participantService = participantService;
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

    public Event getEventById(
        @NotNull(message = "Id cannot be null")
        @Positive(message = "Id must be positive")
        Long id) {

        Optional<Event> potentialEvent = eventRepository.findById(id);

        if (potentialEvent.isPresent() && !potentialEvent.get().isDeleted()) {
            return potentialEvent.get();
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

    public boolean updateNameById(
        @NotNull(message = "New event name cannot be null")
        @NotBlank(message = "New event name cannot be blank")
        String eventName,
        @NotNull(message = "Event id cannot be null")
        @Positive(message = "Event id must be positive")
        Long eventId) {

        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (optionalEvent.isPresent() && !optionalEvent.get().isDeleted()) {

            Event eventToUpdate = optionalEvent.get();
            eventToUpdate.setName(eventName);
            eventToUpdate.setLastUpdatedTime(LocalDateTime.now());
            eventRepository.save(eventToUpdate);
            return true;
        }

        throw new ResourceNotFoundException("There is not an event with such an id");
    }

    public boolean updateDateById(
        @NotNull(message = "Event date cannot be null")
        @NotBlank(message = "Event date cannot be blank")
        LocalDateTime eventDate,
        @NotNull(message = "Event id cannot be null")
        @Positive(message = "Event id must be positive")
        Long eventId) {

        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (optionalEvent.isPresent() && !optionalEvent.get().isDeleted()) {

            Event eventToUpdate = optionalEvent.get();
            eventToUpdate.setDate(eventDate);
            eventToUpdate.setLastUpdatedTime(LocalDateTime.now());
            eventRepository.save(eventToUpdate);
            return true;
        }

        throw new ResourceNotFoundException("There is not an event with such an id");
    }

    public boolean updateLocationById(
        @NotNull(message = "Event location name cannot be null")
        @NotBlank(message = "Event location name cannot be blank")
        String eventLocation,
        @NotNull(message = "Event id cannot be null")
        @Positive(message = "Event id must be positive")
        Long eventId) {

        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (optionalEvent.isPresent() && !optionalEvent.get().isDeleted()) {

            Event eventToUpdate = optionalEvent.get();
            eventToUpdate.setLocation(eventLocation);
            eventToUpdate.setLastUpdatedTime(LocalDateTime.now());
            eventRepository.save(eventToUpdate);
            return true;
        }

        throw new ResourceNotFoundException("There is not an event with such an id");
    }

    public boolean updateEventById(
        @NotNull(message = "Event record cannot be null")
        @NotBlank(message = "Event record cannot be blank")
        EventDto eventToChange,
        @NotNull(message = "Event id cannot be null")
        @Positive(message = "Event id must be positive")
        Long eventId) {

        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (optionalEvent.isPresent() && !optionalEvent.get().isDeleted()) {

            Event eventToUpdate = optionalEvent.get();
            eventToUpdate = eventMapper.toEntity(eventToChange);
            eventToUpdate.setId(optionalEvent.get().getId());
            eventToUpdate.setLastUpdatedTime(LocalDateTime.now());
            eventRepository.save(eventToUpdate);
            return true;
        }

        throw new ResourceNotFoundException("There is not an event with such an id");
    }

    public boolean deleteEvent(
        @NotNull(message = "Id cannot be null")
        @Positive(message = "Id must be positive.")
        Long eventToDeleteId) {

        Optional<Event> optionalEventToDelete = eventRepository.findById(eventToDeleteId);

        if (optionalEventToDelete.isPresent() && !optionalEventToDelete.get().isDeleted()) {

            Event eventToDelete = optionalEventToDelete.get();

            List<Participant> participantsCopy = eventToDelete.getAssociatedParticipants().stream().toList();

            for (Participant currentParticipant : participantsCopy) {
                try {
                    if (participantService.getParticipantById(currentParticipant.getId()).isPresent()) {
                        participantService.deleteParticipant(currentParticipant.getId());
                    }
                } catch (ResourceNotFoundException ignored) {}
            }

            eventToDelete.setLastUpdatedTime(LocalDateTime.now());
            eventToDelete.setDeleted(true);
            eventRepository.save(eventToDelete);
            return true;
        }

        throw new ResourceNotFoundException("There is not an event with such an id");
    }

}
