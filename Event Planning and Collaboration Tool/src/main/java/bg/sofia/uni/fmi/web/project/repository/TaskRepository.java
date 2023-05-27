package bg.sofia.uni.fmi.web.project.repository;

import bg.sofia.uni.fmi.web.project.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    /**
     * CREATE Tasks
     */
    Task save(Task task);

//    List<Task> saveAll(List<Task> tasks);

    List<Task> findTasksByIdEquals(long id);

    void deleteById(long id);

    void delete(Task entity);
}