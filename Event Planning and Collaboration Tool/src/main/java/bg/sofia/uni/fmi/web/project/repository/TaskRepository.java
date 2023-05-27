package bg.sofia.uni.fmi.web.project.repository;

import bg.sofia.uni.fmi.web.project.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task save(Task task);
    Collection<Task> findTasksByIdEquals(long id);
    Collection<Task> findTasksByNameEquals(String name);
    Collection<Task> findTasksByEventIdIs(long id);
    Collection<Task> findTasksByParticipantIdEquals(long id);
}