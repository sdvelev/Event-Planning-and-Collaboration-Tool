package bg.sofia.uni.fmi.web.project.repository;

import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByIdAndDeletedFalse(Long id);

    List<Event> findAllByDeletedFalse();
    List<Event> getEventsByNameAndDeletedFalse(String name);

    List<Event> getEventsByDateAndDeletedFalse(LocalDateTime date);

    List<Event> getEventsByLocationAndDeletedFalse(String location);

    List<Event> getEventsByCreatedByAndDeletedFalse(String createdBy);

}
