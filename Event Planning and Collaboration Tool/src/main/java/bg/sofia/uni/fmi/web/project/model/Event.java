package bg.sofia.uni.fmi.web.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "event")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "associatedEvent")
    private Set<Participant> associatedParticipants;

    @OneToMany(mappedBy = "associatedEvent")
    private Set<Expense> associatedExpenses;

    @OneToMany(mappedBy = "associatedEvent")
    private Set<Expense> associatedBudgets;

    //Audit fields

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "last_updated_time")
    private LocalDateTime lastUpdatedTime;

    //Soft Deletion

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private boolean deleted;

    @OneToMany(mappedBy = "event")
    private Set<Guest> associatedGuests;

    @OneToMany(mappedBy = "event")
    private Set<Task> associatedTasks;
}
