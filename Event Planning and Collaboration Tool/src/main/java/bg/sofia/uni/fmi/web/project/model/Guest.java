package bg.sofia.uni.fmi.web.project.model;

import bg.sofia.uni.fmi.web.project.enums.AttendanceType;
import bg.sofia.uni.fmi.web.project.enums.GuestType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import java.util.Objects;

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

    @Column(length = 255, nullable = false, unique = true)
    private String email;

    @Column(columnDefinition = "ENUM('FAMILY', 'FRIENDS', 'COLLEAGUES', 'CO_WORKERS', 'PARTNER')", nullable = false)
    @Enumerated(EnumType.STRING)
    private GuestType guestType;

    @Column(columnDefinition = "ENUM('GOING', 'NOT_GOING', 'CONSIDERING', 'ATTENDING')", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttendanceType attendanceType;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean invitationSent;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event associatedEvent;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return Objects.equals(name, guest.name) &&
            Objects.equals(surname, guest.surname) &&
            Objects.equals(email, guest.email) &&
            guestType == guest.guestType &&
            attendanceType == guest.attendanceType &&
            Objects.equals(createdBy, guest.createdBy) &&
            Objects.equals(creationTime, guest.creationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, email, guestType, attendanceType, createdBy,
            creationTime);
    }
}