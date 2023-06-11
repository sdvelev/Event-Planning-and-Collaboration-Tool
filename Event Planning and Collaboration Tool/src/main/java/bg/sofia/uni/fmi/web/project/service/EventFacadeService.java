package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Participant;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventFacadeService {

    private EventService eventService;
    private ParticipantService participantService;

    @Autowired
    public EventFacadeService(EventService eventService, ParticipantService participantService) {
        this.eventService = eventService;
        this.participantService = participantService;
    }

    @Transactional
    public boolean deleteEventWithParticipants(
        @NotNull(message = "Id cannot be null")
        @Positive(message = "Id must be positive.")
        Long eventToDeleteId) {

        Event eventToDelete = eventService.deleteEvent(eventToDeleteId);

        List<Participant> participantsCopy = eventToDelete.getAssociatedParticipants().stream().toList();

        for (Participant currentParticipant : participantsCopy) {
            if (participantService.getParticipantById(currentParticipant.getId()).isPresent()) {
                participantService.deleteParticipant(currentParticipant.getId());
            }
        }

        return true;
//        throw new ResourceNotFoundException("There is not an event with such an id");
    }

}
