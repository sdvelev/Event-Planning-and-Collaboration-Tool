package bg.sofia.uni.fmi.web.project.model;

import bg.sofia.uni.fmi.web.project.stub.EventStub;
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
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "guests")
@Table(name = "guests")
@DynamicUpdate
@DynamicInsert
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String surname;

    @Column(length = 255, nullable = false)
    private String email;

    @Column(nullable = false)
    private GuestType guestType;

    @Column(nullable = false)
    private AttendanceType attendanceType;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean invitationSent;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    // private Event event;
    private EventStub event;

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