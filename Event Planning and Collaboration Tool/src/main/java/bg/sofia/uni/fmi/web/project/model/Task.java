package bg.sofia.uni.fmi.web.project.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "event_id", nullable = false)
//    private Event event;

    @Column(nullable = false)
    private TaskProgress taskProgress;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "participant_id", nullable = false)
//    private Participant participant;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    @Column
    private LocalDateTime lastNotified;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String description;

    @Column(length = 255, nullable = false)
    private String createdBy;

    @Column(nullable = false)
    private LocalDateTime creationTime;

    @Column(length = 255)
    private String updatedBy;

    @Column
    private LocalDateTime lastUpdatedTime;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean deleted;
}