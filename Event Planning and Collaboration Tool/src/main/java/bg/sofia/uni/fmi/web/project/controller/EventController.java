package bg.sofia.uni.fmi.web.project.controller;

import bg.sofia.uni.fmi.web.project.dto.EventDto;
import bg.sofia.uni.fmi.web.project.mapper.EventMapper;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.service.EventService;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/events")
@Validated
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @Autowired
    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @GetMapping
    public List<EventDto> getAllEvents() {
        final List<Event> toReturnList = eventService.getAllEvents();

        return toReturnList.stream()
            .map(eventMapper::toDto)
            .collect(Collectors.toList());
    }

    @PostMapping
    public Long addEvent(@NotNull(message = "EventDto cannot be null") @RequestBody EventDto eventDto) {

        //TODO: Only if authorized, user will be able to create an event.
        //      For now, everybody can create events and createdBy field will not be filled
        //      When user creates event, record in participant table with user role CREATOR will be created

        Event potentialEventToCreate = eventService.createEvent(eventMapper.toEntity(eventDto));

        if (potentialEventToCreate != null) {
            return potentialEventToCreate.getId();
        }

        return -1L;
    }

    @DeleteMapping
    public boolean removeEventById(@RequestParam("event_id")
                                       @NotNull(message = "Event id cannot be null")
                                       @Positive(message = "Event id must be positive")
                                       Long eventId) {
        return eventService.deleteEvent(eventId);
    }

    @GetMapping(value = "/search", params = {"id"})
    public ResponseEntity<EventDto> searchEventById(
        @Valid
        @NotNull(message = "EventID cannot be null")
        @Positive(message = "EventID must be positive")
        @RequestParam("id") Long id) {
        Event optionalEventToReturn = eventService.getEventById(id);

        return ResponseEntity.ok(eventMapper.toDto(optionalEventToReturn));
    }

    @GetMapping(value = "/search", params = {"name"})
    public ResponseEntity<List<EventDto>> searchEventByName(
        @Valid
        @NotNull(message = "Event name cannot be null")
        @NotBlank(message = "Event name cannot be blank")
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
        @Valid
        @NotNull(message = "Event date cannot be null")
        @RequestParam("date") LocalDateTime date) {

        List<Event> potentialEventsToReturn = eventService.getEventsByDate(date);

        if (potentialEventsToReturn == null || potentialEventsToReturn.isEmpty()) {
            throw new ResourceNotFoundException("There are not events with such a date");
        }

        return ResponseEntity.ok(
            potentialEventsToReturn.stream().map(eventMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "/search", params = {"location"})
    public ResponseEntity<List<EventDto>> searchEventByLocation(
        @Valid
        @NotNull(message = "Event location cannot be null")
        @NotBlank(message = "Event location cannot be blank")
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
        @Valid
        @NotNull(message = "Created by name cannot be null")
        @NotBlank(message = "Created by name cannot be blank")
        @RequestParam("created_by") String createdBy) {

        List<Event> potentialEventsToReturn = eventService.getEventsByCreatedBy(createdBy);

        if (potentialEventsToReturn == null || potentialEventsToReturn.isEmpty()) {
            throw new ResourceNotFoundException("There are not events with such a creator");
        }

        return ResponseEntity.ok(
            potentialEventsToReturn.stream().map(eventMapper::toDto).collect(Collectors.toList()));
    }

    @PatchMapping(value = "/name/{event_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean setEventNameByEventId(@PathVariable("event_id")
                                             @NotNull(message = "Event id cannot be null")
                                             @Positive(message = "Event id must be positive")
                                             Long eventId,
                                             @RequestParam("event_name")
                                             @NotNull(message = "New event name cannot be null")
                                             @NotBlank(message = "Event name cannot be blank")
                                             String eventName) {
        //TODO: Authorization in order to update event name
        return eventService.updateNameById(eventName, eventId);
    }

    @PatchMapping("/date/{event_id}")
    public boolean setDateByEventId(@PathVariable("event_id")
                                        @NotNull(message = "Event id cannot be null")
                                        @Positive(message = "Event id must be positive")
                                        Long eventId,
                                        @RequestParam("event_date")
                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                        @NotNull(message = "Event date cannot be null")
                                        LocalDateTime eventDate) {
        //TODO: Authorization in order to update event date
        return eventService.updateDateById(eventDate, eventId);
    }

    @PatchMapping("/location/{event_id}")
    public boolean setLocationByEventId(@PathVariable("event_id")
                                            @NotNull(message = "Event id cannot be null")
                                            @Positive(message = "Event id must be positive")
                                            Long eventId,
                                            @RequestParam("event_location")
                                            @NotNull(message = "Event location cannot be null")
                                            @NotBlank(message = "Event location cannot be blank")
                                            String eventLocation) {
        //TODO: Authorization in order to update event location
        return eventService.updateLocationById(eventLocation, eventId);
    }

    @PutMapping("/update")
    public boolean setNewEventByEventId(@RequestParam("event_id")
                                            @NotNull(message = "Event id cannot be null")
                                            @Positive(message = "Event id must be positive")
                                            Long eventId,
                                        @RequestBody
                                            @NotNull(message = "Event record cannot be null")
                                            EventDto eventToUpdate) {
        //TODO: Authorization in order to update event
        return eventService.updateEventById(eventToUpdate, eventId);
    }
}