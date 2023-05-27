package bg.sofia.uni.fmi.web.project.dto;

import bg.sofia.uni.fmi.web.project.model.TaskProgress;
import bg.sofia.uni.fmi.web.project.model.stub.EventStub;
import bg.sofia.uni.fmi.web.project.model.stub.ParticipantStub;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private long id;
    private String name;
    private String description;
    private TaskProgress taskProgress;
    private LocalDateTime dueDate;
    private LocalDateTime lastNotified;
    // Event
    private EventStub event;
    // Participant
    private ParticipantStub participant;
    private String createdBy;
    private LocalDateTime creationTime;
    private String updatedBy;
    private LocalDateTime lastUpdatedTime;
    private boolean deleted;
}