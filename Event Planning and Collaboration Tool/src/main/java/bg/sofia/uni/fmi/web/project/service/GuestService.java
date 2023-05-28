package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.mapper.GuestMapper;
import bg.sofia.uni.fmi.web.project.model.AttendanceType;
import bg.sofia.uni.fmi.web.project.model.Guest;
import bg.sofia.uni.fmi.web.project.model.GuestType;
import bg.sofia.uni.fmi.web.project.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class GuestService {
    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    @Autowired
    public GuestService(GuestRepository guestRepository, GuestMapper guestMapper) {
        this.guestRepository = guestRepository;
        this.guestMapper = guestMapper;
    }

    public long addGuest(Guest guest) {
        return guestRepository.save(guest).getId();
    }

    public List<Long> addGuests(List<Guest> guestList) {
        return guestRepository.saveAll(guestList).stream()
            .map(Guest::getId)
            .toList();
    }

    public List<Guest> getAllGuests() {
        return guestRepository.findAll().parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();
    }

    public Guest getGuestById(long id) {
        Guest guest =  guestRepository.findGuestByIdEquals(id);
        return guest.isDeleted() ? null : guest;
    }

    public Guest getGuestByEmail(String email) {
        Guest guest = guestRepository.findGuestByEmailEquals(email);
        return guest.isDeleted() ? null : guest;
    }

    public Collection<Guest> getGuestByNameAndSurname(String name, String surname) {
        return guestRepository.findGuestByNameAndSurnameEquals(name, surname).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();
    }

    public Collection<Guest> getGuestByEventId(long id) {
        return guestRepository.findGuestByEventIdEquals(id).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();
    }

    public Collection<Guest> getGuestsByGuestType(GuestType guestType) {
        return guestRepository.findGuestsByGuestTypeEquals(guestType).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();
    }

    public Collection<Guest> getGuestsBytAttendanceType(AttendanceType attendanceType) {
        return guestRepository.findGuestsByAttendanceTypeEquals(attendanceType).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();
    }

}