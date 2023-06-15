package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.enums.TaskProgress;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Participant;
import bg.sofia.uni.fmi.web.project.model.Task;
import bg.sofia.uni.fmi.web.project.validation.ApiBadRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
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
//        if (!validateForExistingTaskByName(task) || !validateForExistingTaskByEventId(task)) {
//            throw new ApiBadRequest("There is already a task with the same credentials");
//        }

        TaskProgress newTaskProgress = TaskProgress.valueOf(taskProgress.toUpperCase());
        Event event = eventService.getEventById(eventId);

        Participant participant;
        if (participantService.getParticipantById(participantId).isEmpty()) {
             throw new ApiBadRequest("There was unexpected problem that occurred with getting participant!");
        }

        participant = participantService.getParticipantById(participantId).get();

        taskToSave.setTaskProgress(newTaskProgress);
        taskToSave.setAssociatedEvent(event);
        taskToSave.setParticipant(participant);
        taskToSave.setCreatedBy("a");
        taskToSave.setCreationTime(LocalDateTime.now());
        taskToSave.setDeleted(false);

        event.getAssociatedTasks().add(taskToSave);
        participant.getAssociatedTasks().add(taskToSave);

        return taskService.addTask(taskToSave);
    }
}