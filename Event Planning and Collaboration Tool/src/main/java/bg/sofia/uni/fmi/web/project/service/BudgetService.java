package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.dto.BudgetDto;
import bg.sofia.uni.fmi.web.project.enums.ExpenditureCategory;
import bg.sofia.uni.fmi.web.project.model.Budget;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.repository.BudgetRepository;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;

    @Autowired
    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public Budget createBudget(
        @NotNull(message = "Expense cannot be null")
        Budget budgetToSave) {
        budgetToSave.setCreationTime(LocalDateTime.now());
        budgetToSave.setDeleted(false);

        return budgetRepository.save(budgetToSave);
    }

    public List<Budget> getBudgets() {
        return budgetRepository.findAll()
            .parallelStream()
            .filter(budget -> !budget.isDeleted())
            .collect(Collectors.toList());
    }

    public Optional<Budget> getBudgetById(
        @NotNull(message = "Id cannot be null")
        @Positive(message = "Id must be positive.")
        Long id) {
        Optional<Budget> potentialBudgetToReturn = budgetRepository.findById(id);

        if (potentialBudgetToReturn.isPresent() && !potentialBudgetToReturn.get().isDeleted()) {
            return potentialBudgetToReturn;
        }

        throw new ResourceNotFoundException("Budget with such an id cannot be found");
    }

    public String getDescriptionByBudgetId(Long id) {
        Optional<Budget> optionalBudget = this.getBudgetById(id);

        if (optionalBudget.isPresent() && !optionalBudget.get().isDeleted()) {
            return optionalBudget.get().getDescription();
        }
        return null;
    }

    public ExpenditureCategory getExpenditureCategoryByBudgetId(Long id) {
        Optional<Budget> optionalBudget = this.getBudgetById(id);

        if (optionalBudget.isPresent() && !optionalBudget.get().isDeleted()) {
            return optionalBudget.get().getExpenditureCategory();
        }
        return null;
    }

    public BigDecimal getAmountByBudgetId(Long id) {
        Optional<Budget> optionalBudget = this.getBudgetById(id);

        if (optionalBudget.isPresent() && !optionalBudget.get().isDeleted()) {
            return optionalBudget.get().getAmount();
        }
        return null;
    }

    public Event getEventByBudgetId(Long id) {
        Optional<Budget> optionalBudget = this.getBudgetById(id);

        if (optionalBudget.isPresent() && !optionalBudget.get().isDeleted()) {
            return optionalBudget.get().getAssociatedEvent();
        }
        return null;
    }

    public boolean setBudgetById(
        @NotNull(message = "Budget record cannot be null")
        BudgetDto budgetFieldsToChange,
        @NotNull(message = "Budget id cannot be null")
        @Positive(message = "Budget id must be positive")
        Long budgetId) {

        Optional<Budget> optionalBudgetToUpdate = budgetRepository.findById(budgetId);

        if (optionalBudgetToUpdate.isPresent() && !optionalBudgetToUpdate.get().isDeleted()) {

            Budget budgetToUpdate = setBudgetNonNullFields(budgetFieldsToChange,
                optionalBudgetToUpdate.get());;
            budgetToUpdate.setLastUpdatedTime(LocalDateTime.now());
            budgetRepository.save(budgetToUpdate);
            return true;
        }

        throw new ResourceNotFoundException("There is not a budget with such an id");
    }

    public boolean deleteBudget(
        @NotNull(message = "Id cannot be null")
        @Positive(message = "Id must be positive.")
        Long budgetId) {

        Optional<Budget> optionalBudgetToDelete = budgetRepository.findById(budgetId);
        if (optionalBudgetToDelete.isPresent() && !optionalBudgetToDelete.get().isDeleted()) {
            Budget budgetToDelete = optionalBudgetToDelete.get();
            budgetToDelete.setLastUpdatedTime(LocalDateTime.now());
            budgetToDelete.setDeleted(true);
            budgetRepository.save(budgetToDelete);
            return true;
        }

        throw new ResourceNotFoundException("There is not a budget with such an id");
    }

    private Budget setBudgetNonNullFields(BudgetDto budgetFieldsToChange, Budget budgetToUpdate) {

        if (budgetFieldsToChange.getDescription() != null) {
            budgetToUpdate.setDescription(budgetFieldsToChange.getDescription());
        }

        if (budgetFieldsToChange.getAmount() != null) {
            budgetToUpdate.setAmount(budgetFieldsToChange.getAmount());
        }

        if (budgetFieldsToChange.getExpenditureCategory() != null) {
            budgetToUpdate.setExpenditureCategory(budgetFieldsToChange.getExpenditureCategory());
        }

        if (budgetFieldsToChange.isApproved()) {
            budgetToUpdate.setApproved(budgetFieldsToChange.isApproved());
        }

        return budgetToUpdate;
    }
}
