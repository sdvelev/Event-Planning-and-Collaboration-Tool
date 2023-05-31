package bg.sofia.uni.fmi.web.project.repository;

import bg.sofia.uni.fmi.web.project.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> getEventsByName(String name);

    List<Event> getEventsByDate(LocalDateTime date);

    List<Event> getEventsByLocation(String location);

    List<Event> getEventsByCreatedBy(String createdBy);

}
