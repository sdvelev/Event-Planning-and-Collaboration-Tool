package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.dto.BudgetDto;
import bg.sofia.uni.fmi.web.project.enums.BudgetExpenditureCategory;
import bg.sofia.uni.fmi.web.project.model.Budget;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.User;
import bg.sofia.uni.fmi.web.project.repository.BudgetRepository;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class BudgetService {
    private final BudgetRepository budgetRepository;

    @Autowired
    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public Budget createBudget(
        @NotNull(message = "The provided expense cannot be null")
        Budget budgetToSave,
        @NotNull(message = "The user who makes changes cannot be null")
        User userToMakeChanges) {
        budgetToSave.setCreationTime(LocalDateTime.now());
        budgetToSave.setCreatedBy(userToMakeChanges.getUsername());

        return budgetRepository.save(budgetToSave);
    }

    public List<Budget> getBudgets() {
        return budgetRepository.findAllByDeletedFalse();
    }

    public List<Budget> getBudgetsByEvent(
        @NotNull(message = "The provided event cannot be null")
        Event event) {
        return getBudgets().stream()
            .filter(budget -> budget.getAssociatedEvent().equals(event))
            .collect(Collectors.toList());
    }

    public Optional<Budget> getBudgetById(
        @NotNull(message = "The provided budget id cannot be null")
        @Positive(message = "The provided budget id must be positive")
        Long id) {
        Optional<Budget> potentialBudgetToReturn = budgetRepository.findByIdAndDeletedFalse(id);

        if (potentialBudgetToReturn.isPresent()) {
            return potentialBudgetToReturn;
        }

        throw new ResourceNotFoundException("Budget with such an id cannot be found");
    }

    public String getDescriptionByBudgetId(
        @NotNull(message = "The provided budget id cannot be null")
        @Positive(message = "The provided budget id must be positive")
        Long id) {
        Optional<Budget> optionalBudget = this.getBudgetById(id);

        if (optionalBudget.isPresent()) {
            return optionalBudget.get().getDescription();
        }

        throw new ResourceNotFoundException("Budget with such an id cannot be found");
    }

    public BudgetExpenditureCategory getExpenditureCategoryByBudgetId(
        @NotNull(message = "The provided budget id cannot be null")
        @Positive(message = "The provided budget id must be positive")
        Long id) {
        Optional<Budget> optionalBudget = this.getBudgetById(id);

        if (optionalBudget.isPresent()) {
            return optionalBudget.get().getExpenditureCategory();
        }
        throw new ResourceNotFoundException("Budget with such an id cannot be found");
    }

    public BigDecimal getAmountByBudgetId(
        @NotNull(message = "The provided budget id cannot be null")
        @Positive(message = "The provided budget id must be positive")
        Long id) {
        Optional<Budget> optionalBudget = this.getBudgetById(id);

        if (optionalBudget.isPresent()) {
            return optionalBudget.get().getAmount();
        }
        throw new ResourceNotFoundException("Budget with such an id cannot be found");
    }

    public Event getEventByBudgetId(
        @NotNull(message = "The provided budget id cannot be null")
        @Positive(message = "The provided budget id must be positive")
        Long id) {
        Optional<Budget> optionalBudget = this.getBudgetById(id);

        if (optionalBudget.isPresent()) {
            return optionalBudget.get().getAssociatedEvent();
        }
        throw new ResourceNotFoundException("Budget with such an id cannot be found");
    }

    public boolean setBudgetById(
        @NotNull(message = "The provided budget dto cannot be null")
        BudgetDto budgetFieldsToChange,
        @NotNull(message = "The provided budget id cannot be null")
        @Positive(message = "The provided budget id must be positive")
        Long budgetId,
        @NotNull(message = "The user who makes changes cannot be null")
        User userToMakeChanges) {

        Optional<Budget> optionalBudgetToUpdate = budgetRepository.findByIdAndDeletedFalse(budgetId);

        if (optionalBudgetToUpdate.isPresent()) {

            Budget budgetToUpdate = setBudgetNonNullFields(budgetFieldsToChange,
                optionalBudgetToUpdate.get());
            budgetToUpdate.setLastUpdatedTime(LocalDateTime.now());
            budgetToUpdate.setUpdatedBy(userToMakeChanges.getUsername());
            budgetRepository.save(budgetToUpdate);
            return true;
        }

        throw new ResourceNotFoundException("There is not a budget with such an id");
    }

    public boolean deleteBudget(
        @NotNull(message = "The provided budget id cannot be null")
        @Positive(message = "The provided budget id must be positive")
        Long budgetId,
        @NotNull(message = "The user who makes changes cannot be null")
        User userToMakeChanges) {

        Optional<Budget> optionalBudgetToDelete = budgetRepository.findByIdAndDeletedFalse(budgetId);
        if (optionalBudgetToDelete.isPresent()) {
            Budget budgetToDelete = optionalBudgetToDelete.get();
            budgetToDelete.setLastUpdatedTime(LocalDateTime.now());
            budgetToDelete.setUpdatedBy(userToMakeChanges.getUsername());
            budgetToDelete.setDeleted(true);
            budgetRepository.save(budgetToDelete);
            return true;
        }

        throw new ResourceNotFoundException("There is not a budget with such an id");
    }

    private Budget setBudgetNonNullFields(
        @NotNull(message = "The provided budget dto cannot be null")
        BudgetDto budgetFieldsToChange,
        @NotNull(message = "The provided budget cannot be null")
        Budget budgetToUpdate) {

        if (budgetFieldsToChange.getDescription() != null) {
            budgetToUpdate.setDescription(budgetFieldsToChange.getDescription());
        }

        if (budgetFieldsToChange.getAmount() != null) {
            budgetToUpdate.setAmount(budgetFieldsToChange.getAmount());
        }

        if (budgetFieldsToChange.isApproved()) {
            budgetToUpdate.setApproved(budgetFieldsToChange.isApproved());
        }

        return budgetToUpdate;
    }
}
