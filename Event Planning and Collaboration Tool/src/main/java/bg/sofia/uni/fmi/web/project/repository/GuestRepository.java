package bg.sofia.uni.fmi.web.project.repository;

import bg.sofia.uni.fmi.web.project.enums.AttendanceType;
import bg.sofia.uni.fmi.web.project.model.Guest;
import bg.sofia.uni.fmi.web.project.enums.GuestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Guest findGuestByIdEquals(long id);

    Guest findGuestByEmailEquals(String email);

    List<Guest> findAll();

    Collection<Guest> findGuestByNameAndSurnameEquals(String name, String surname);

    Collection<Guest> findGuestByEventIdEquals(long id);

    Collection<Guest> findGuestsByGuestTypeEquals(GuestType guestType);

    Collection<Guest> findGuestsByAttendanceTypeEquals(AttendanceType attendanceType);
}