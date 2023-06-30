package bg.sofia.uni.fmi.web.project.repository;

import bg.sofia.uni.fmi.web.project.enums.AttendanceType;
import bg.sofia.uni.fmi.web.project.enums.GuestType;
import bg.sofia.uni.fmi.web.project.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    @Query("SELECT g FROM guests g WHERE g.deleted = false AND g.id = ?1")
    Guest findGuestByIdEquals(long id);

    @Query("SELECT g FROM guests g WHERE g.deleted = false AND g.email = ?1")
    Guest findGuestByEmailEquals(String email);

    @Override
    @Query("SELECT g FROM guests g WHERE g.deleted = false")
    List<Guest> findAll();

    @Query("SELECT g FROM guests g WHERE g.deleted = false AND g.name = ?1 AND g.surname = ?2")
    List<Guest> findGuestByNameAndSurnameEquals(String name, String surname);

    @Query("SELECT g FROM guests g WHERE g.deleted = false AND g.associatedEvent.id = ?1")
    List<Guest> findGuestByAssociatedEventIdEquals(long id);

    @Query("SELECT g FROM guests g WHERE g.deleted = false AND g.guestType = ?1")
    List<Guest> findGuestsByGuestTypeEquals(GuestType guestType);

    @Query("SELECT g FROM guests g WHERE g.deleted = false AND g.attendanceType = ?1")
    List<Guest> findGuestsByAttendanceTypeEquals(AttendanceType attendanceType);
}