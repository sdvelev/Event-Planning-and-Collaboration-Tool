package bg.sofia.uni.fmi.web.project.repository;

import bg.sofia.uni.fmi.web.project.enums.AttendanceType;
import bg.sofia.uni.fmi.web.project.enums.GuestType;
import bg.sofia.uni.fmi.web.project.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Guest findGuestByIdEquals(long id);

    Guest findGuestByEmailEquals(String email);

    List<Guest> findAll();

    List<Guest> findGuestByNameAndSurnameEquals(String name, String surname);

    List<Guest> findGuestByAssociatedEventIdEquals(long id);

    List<Guest> findGuestsByGuestTypeEquals(GuestType guestType);

    List<Guest> findGuestsByAttendanceTypeEquals(AttendanceType attendanceType);
}