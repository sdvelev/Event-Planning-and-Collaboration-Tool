package bg.sofia.uni.fmi.web.project.repository;

import bg.sofia.uni.fmi.web.project.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    /**
     * CREATE tasks group
     */
    Task save(Task task);





    /**
     * Read tasks group
     */
    List<Task> findTasksByIdEquals(long id);
    List<Task> findTasksByNameEquals(String name);








    /**
     * Update tasks group
     */
    //    List<Task> saveAll(List<Task> tasks);








    /**
     * Delete tasks group
     */
    void deleteById(long id);

    void delete(Task entity);





}