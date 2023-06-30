package bg.sofia.uni.fmi.web.project.repository;

import bg.sofia.uni.fmi.web.project.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM tasks t WHERE t.deleted = false AND t.id = ?1")
    Task findTaskByIdEquals(long id);

    @Override
    @Query("SELECT t FROM tasks t WHERE t.deleted = false")
    List<Task> findAll();

    @Query("SELECT t FROM tasks t WHERE t.deleted = false AND t.name = ?1")
    List<Task> findTasksByNameEquals(String name);

    @Query("SELECT t FROM tasks t WHERE t.deleted = false AND t.associatedEvent.id = ?1")
    List<Task> findTasksByAssociatedEventIdEquals(long id);

    @Query("SELECT t FROM tasks t WHERE t.deleted = false AND t.participant.id = ?1")
    List<Task> findTasksByAndParticipantIdEquals(long id);

    @Query("SELECT t FROM tasks t WHERE t.deleted = false AND t.createdBy = ?1")
    List<Task> findTaskByCreatedByEquals(String name);

    @Query("SELECT t FROM tasks t WHERE t.deleted = false AND t.dueDate BETWEEN ?1 AND ?2")
    List<Task> findTaskByDueDateBetween(LocalDateTime localDateTimeAfter,
                                                     LocalDateTime localDateTimeBefore);
}