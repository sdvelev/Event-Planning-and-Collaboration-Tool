package bg.sofia.uni.fmi.web.project.controller;

import bg.sofia.uni.fmi.web.project.dto.EventDto;
import bg.sofia.uni.fmi.web.project.dto.UserDto;
import bg.sofia.uni.fmi.web.project.enums.UserRole;
import bg.sofia.uni.fmi.web.project.mapper.EventMapper;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.User;
import bg.sofia.uni.fmi.web.project.service.EventService;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
        Event potentialEventToCreate = eventService.createEvent(eventMapper.toEntity(eventDto));

        if (potentialEventToCreate != null) {
            return potentialEventToCreate.getId();
        }

        return -1L;
    }

    @DeleteMapping
    public boolean removeEventById(@RequestParam("id") Long eventId) {
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

    @PutMapping("/name")
    public boolean setEventNameByEventId(@RequestParam("event_id") Long eventId,
                                              @RequestParam("event_name") String eventName) {
        return eventService.updateNameById(eventName, eventId);
    }

    @PutMapping("/location")
    public boolean setLocationByEventId(@RequestParam("event_id") Long eventId,
                                         @RequestParam("event_location") String eventLocation) {
        return eventService.updateLocationById(eventLocation, eventId);
    }
}
