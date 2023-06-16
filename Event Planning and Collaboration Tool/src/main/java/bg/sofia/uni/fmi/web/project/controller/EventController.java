package bg.sofia.uni.fmi.web.project.controller;

import bg.sofia.uni.fmi.web.project.dto.EventDto;
import bg.sofia.uni.fmi.web.project.mapper.EventMapper;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.service.EventParticipantBudgetExpenseFacadeService;
import bg.sofia.uni.fmi.web.project.service.EventService;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/events")
@Validated
public class EventController {

    private final EventService eventService;
    private final EventParticipantBudgetExpenseFacadeService eventParticipantBudgetExpenseFacadeService;
    private final EventMapper eventMapper;

    @Autowired
    public EventController(EventService eventService, EventParticipantBudgetExpenseFacadeService eventParticipantBudgetExpenseFacadeService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventParticipantBudgetExpenseFacadeService = eventParticipantBudgetExpenseFacadeService;
        this.eventMapper = eventMapper;
    }

    @GetMapping
    public List<EventDto> getAllEvents() {

        return eventMapper.toDtoList(eventService.getAllEvents());
    }

    @PostMapping
    public Long addEvent(@NotNull(message = "The provided event dto as body of the query cannot be null")
                             @RequestBody EventDto eventDto) {

        //TODO: Only if authorized, user will be able to create an event.
        //      For now, everybody can create events and createdBy field will not be filled
        //      When user creates event, record in participant table with user role CREATOR will be created

        Event potentialEventToCreate = eventService.createEvent(eventMapper.toEntity(eventDto));

        if (potentialEventToCreate != null) {
            return potentialEventToCreate.getId();
        }

        return -1L;
    }

    @DeleteMapping(params = {"event_id"})
    public boolean removeEventById(@RequestParam("event_id")
                                       @NotNull(message = "The provided event id cannot be null")
                                       @Positive(message = "The provided event id must be positive")
                                       Long eventId) {
        return eventParticipantBudgetExpenseFacadeService.deleteEventWithParticipants(eventId);
    }

    @GetMapping(value = "/search", params = {"id"})
    public ResponseEntity<EventDto> searchEventById(
        @NotNull(message = "The provided event id cannot be null")
        @Positive(message = "The provided event id must be positive")
        @RequestParam("id") Long id) {
        Optional<Event> optionalEventToReturn = eventService.getEventById(id);

        return ResponseEntity.ok(eventMapper.toDto(optionalEventToReturn.get()));
    }

    @GetMapping(value = "/search", params = {"name"})
    public ResponseEntity<List<EventDto>> searchEventByName(
        @NotNull(message = "The provided event name cannot be null")
        @NotBlank(message = "The provided event name cannot be blank")
        @RequestParam("name") String eventName) {

        List<Event> potentialEventsToReturn = eventService.getEventsByName(eventName);

        if (potentialEventsToReturn == null || potentialEventsToReturn.isEmpty()) {
            throw new ResourceNotFoundException("There are not events with such a name");
        }

        return ResponseEntity.ok(
            potentialEventsToReturn.stream().map(eventMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "/search", params = {"date"})
    public ResponseEntity<List<EventDto>> searchEventByDate(
        @NotNull(message = "The provided event date cannot be null")
        @RequestParam("date")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime date) {

        List<Event> potentialEventsToReturn = eventService.getEventsByDate(date);

        if (potentialEventsToReturn == null || potentialEventsToReturn.isEmpty()) {
            throw new ResourceNotFoundException("There are not events with such a date");
        }

        return ResponseEntity.ok(
            potentialEventsToReturn.stream().map(eventMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "/search", params = {"location"})
    public ResponseEntity<List<EventDto>> searchEventByLocation(
        @NotNull(message = "The provided location cannot be null")
        @NotBlank(message = "The provided location cannot be blank")
        @RequestParam("location") String location) {

        List<Event> potentialEventsToReturn = eventService.getEventsByLocation(location);

        if (potentialEventsToReturn == null || potentialEventsToReturn.isEmpty()) {
            throw new ResourceNotFoundException("There are not events with such a location");
        }

        return ResponseEntity.ok(
            potentialEventsToReturn.stream().map(eventMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "/search", params = {"created_by"})
    public ResponseEntity<List<EventDto>> searchEventByCreatedBy(
        @NotNull(message = "The provided created by name cannot be null")
        @NotBlank(message = "The provided created by name cannot be blank")
        @RequestParam("created_by") String createdBy) {

        List<Event> potentialEventsToReturn = eventService.getEventsByCreatedBy(createdBy);

        if (potentialEventsToReturn == null || potentialEventsToReturn.isEmpty()) {
            throw new ResourceNotFoundException("There are not events with such a creator");
        }

        return ResponseEntity.ok(
            potentialEventsToReturn.stream().map(eventMapper::toDto).collect(Collectors.toList()));
    }

    @PutMapping(value = "/set", params = {"event_id"})
    public boolean setEventByEventId(@RequestParam("event_id")
                                            @NotNull(message = "The provided event id cannot be null")
                                            @Positive(message = "The provided event id must be positive")
                                            Long eventId,
                                            @RequestBody
                                            @NotNull(message = "The provided event dto as body of the query cannot be null")
                                            EventDto eventToUpdate) {
        //TODO: Authorization in order to update event
        return eventService.setEventById(eventToUpdate, eventId);
    }
}
