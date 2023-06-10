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
    List<Task> findTasksByNameEquals(String name);
    List<Task> findTasksByEventIdIs(long id);
    List<Task> findTasksByParticipantIdEquals(long id);
    List<Task> findTaskByCreatedByEquals(String name);
    List<Task> findTaskByDueDateAfter(LocalDateTime localDateTime);
    List<Task> findTaskByDueDateBefore(LocalDateTime localDateTime);
    List<Task> findTaskByDueDateBetween(LocalDateTime localDateTimeAfter, LocalDateTime localDateTimeBefore);
}