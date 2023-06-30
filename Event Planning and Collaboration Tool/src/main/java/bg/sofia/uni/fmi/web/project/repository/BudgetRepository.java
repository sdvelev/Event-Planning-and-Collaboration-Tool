package bg.sofia.uni.fmi.web.project.repository;

import bg.sofia.uni.fmi.web.project.model.Budget;
import bg.sofia.uni.fmi.web.project.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findByIdAndDeletedFalse(Long id);
    List<Budget> findAllByDeletedFalse();
}
