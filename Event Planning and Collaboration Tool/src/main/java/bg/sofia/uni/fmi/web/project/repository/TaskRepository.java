package bg.sofia.uni.fmi.web.project.repository;

import bg.sofia.uni.fmi.web.project.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findTaskByIdEquals(long id);
    List<Task> findAll();
    Collection<Task> findTasksByNameEquals(String name);
    Collection<Task> findTasksByEventIdIs(long id);
    Collection<Task> findTasksByParticipantIdEquals(long id);
    Collection<Task> findTaskByCreatedByEquals(String name);
    Collection<Task> findTaskByDueDateAfter(LocalDateTime localDateTime);
    Collection<Task> findTaskByDueDateBefore(LocalDateTime localDateTime);
    Collection<Task> findTaskByDueDateBetween(LocalDateTime localDateTimeAfter, LocalDateTime localDateTimeBefore);
}