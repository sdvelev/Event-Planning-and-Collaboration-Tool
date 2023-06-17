package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.enums.TaskProgress;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Participant;
import bg.sofia.uni.fmi.web.project.model.Task;
import bg.sofia.uni.fmi.web.project.validation.ApiBadRequest;
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
public class TaskEventParticipantFacadeService {
    private final TaskService taskService;
    private final EventService eventService;
    private final ParticipantService participantService;

    @Transactional
    public long addTask(@NotNull(message = "The given task cannot be null!") Task taskToSave,
                        @NotNull(message = "The event id cannot be null!")
                        Long eventId,
                        @NotNull(message = "The event id cannot be null!")
                        Long participantId,
                        @NotNull(message = "The guest type cannot be null!")
                        @NotEmpty(message = "The guest type cannot be empty!")
                        @NotBlank(message = "The guest type cannot be blank!")
                        String taskProgress) {
        validateForExistingReview(taskToSave.getName(), eventId, participantId);

        TaskProgress newTaskProgress = TaskProgress.valueOf(taskProgress.toUpperCase());
        Event event = eventService.getEventById(eventId);
        validateEvent(event);

        Optional<Participant> participant = participantService.getParticipantById(participantId);
        validateParticipant(participant);

        taskToSave.setTaskProgress(newTaskProgress);
        taskToSave.setAssociatedEvent(event);
        taskToSave.setParticipant(participant.get());
        taskToSave.setCreatedBy("a");
        taskToSave.setCreationTime(LocalDateTime.now());
        taskToSave.setDeleted(false);

        event.getAssociatedTasks().add(taskToSave);
        participant.get().getAssociatedTasks().add(taskToSave);

        return taskService.addTask(taskToSave);
    }

    private void validateEvent(Event event) {
        if (event == null) {
            throw new ResourceNotFoundException("There is no event with such id!");
        }
    }

    private void validateParticipant(Optional<Participant> participant) {
        if (participant.isEmpty()) {
            throw new ApiBadRequest("There was unexpected problem that occurred with getting participant!");
        }
    }

    private void validateForExistingReview(String name, long eventId, long participantId) {
        if (!validateForExistingTaskByName(name) && !validateForExistingTaskByEventId(eventId) &&
            !validateForExistingTaskByParticipantId(participantId)) {

            throw new ConflictException("There is already such task in the database!");
        }
    }

    private boolean validateForExistingTaskByName(String name) {
        return taskService.getTasksByName(name).isEmpty();
    }

    private boolean validateForExistingTaskByEventId(long eventId) {
        return taskService.getTasksByEventId(eventId).isEmpty();
    }

    private boolean validateForExistingTaskByParticipantId(long participantId) {
        return taskService.getTasksByParticipantId(participantId).isEmpty();
    }
}