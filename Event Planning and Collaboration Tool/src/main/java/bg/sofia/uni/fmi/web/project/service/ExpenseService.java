package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.dto.ExpenseDto;
import bg.sofia.uni.fmi.web.project.enums.ExpenseExpenditureCategory;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Expense;
import bg.sofia.uni.fmi.web.project.model.User;
import bg.sofia.uni.fmi.web.project.repository.ExpenseRepository;
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
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense createExpense(
        @NotNull(message = "The provided expense cannot be null")
        Expense expenseToSave,
        @NotNull(message = "The user who makes changes cannot be null")
        User userToMakeChanges) {
        expenseToSave.setCreationTime(LocalDateTime.now());
        expenseToSave.setCreatedBy(userToMakeChanges.getUsername());

        return expenseRepository.save(expenseToSave);
    }

    public List<Expense> getExpenses() {
        return expenseRepository.findAllByDeletedFalse();
    }

    public List<Expense> getExpensesByEvent(
        @NotNull(message = "The provided event be null")
        Event event) {
        return getExpenses().stream()
            .filter(expense -> expense.getAssociatedEvent().equals(event))
            .collect(Collectors.toList());
    }

    public Optional<Expense> getExpenseById(
        @NotNull(message = "The provided expense id cannot be null")
        @Positive(message = "The provided expense id must be positive")
        Long id) {

        Optional<Expense> potentialExpenseToReturn = expenseRepository.findByIdAndDeletedFalse(id);

        if (potentialExpenseToReturn.isPresent()) {
            return potentialExpenseToReturn;
        }

        throw new ResourceNotFoundException("Expense with such an id cannot be found");
    }

    public String getDescriptionByExpenseId(
        @NotNull(message = "The provided expense id cannot be null")
        @Positive(message = "The provided expense id must be positive")
        Long id) {
        Optional<Expense> optionalExpense = this.getExpenseById(id);

        if (optionalExpense.isPresent()) {
            return optionalExpense.get().getDescription();
        }
        throw new ResourceNotFoundException("Expense with such an id cannot be found");
    }

    public ExpenseExpenditureCategory getExpenditureCategoryByExpenseId(
        @NotNull(message = "The provided expense id cannot be null")
        @Positive(message = "The provided expense id must be positive")
        Long id) {
        Optional<Expense> optionalExpense = this.getExpenseById(id);

        if (optionalExpense.isPresent()) {
            return optionalExpense.get().getExpenditureCategory();
        }
        throw new ResourceNotFoundException("Expense with such an id cannot be found");
    }

    public BigDecimal getAmountByExpenseId(
        @NotNull(message = "The provided expense id cannot be null")
        @Positive(message = "The provided expense id must be positive")
        Long id) {
        Optional<Expense> optionalExpense = this.getExpenseById(id);

        if (optionalExpense.isPresent()) {
            return optionalExpense.get().getAmount();
        }
        throw new ResourceNotFoundException("Expense with such an id cannot be found");
    }

    public Event getEventByExpenseId(
        @NotNull(message = "The provided expense id cannot be null")
        @Positive(message = "The provided expense id must be positive")
        Long id) {
        Optional<Expense> optionalExpense = this.getExpenseById(id);

        if (optionalExpense.isPresent()) {
            return optionalExpense.get().getAssociatedEvent();
        }
        throw new ResourceNotFoundException("Expense with such an id cannot be found");
    }

    public boolean setExpenseById(
        @NotNull(message = "The provided expense dto cannot be null")
        ExpenseDto expenseFieldsToChange,
        @NotNull(message = "The provided expense id cannot be null")
        @Positive(message = "The provided expense id must be positive")
        Long expenseId,
        @NotNull(message = "The user who makes changes cannot be null")
        User userToMakeChanges) {

        Optional<Expense> optionalExpenseToUpdate = expenseRepository.findByIdAndDeletedFalse(expenseId);

        if (optionalExpenseToUpdate.isPresent()) {

            Expense expenseToUpdate = setExpenseNonNullFields(expenseFieldsToChange,
                optionalExpenseToUpdate.get());;
            expenseToUpdate.setLastUpdatedTime(LocalDateTime.now());
            expenseToUpdate.setUpdatedBy(userToMakeChanges.getUsername());
            expenseRepository.save(expenseToUpdate);
            return true;
        }

        throw new ResourceNotFoundException("There is not an expense with such an id");
    }

    public boolean deleteExpense(
        @NotNull(message = "The provided expense id cannot be null")
        @Positive(message = "The provided expense id must be positive")
        Long expenseById,
        @NotNull(message = "The user who made changes cannot be null")
        User userToMakeChanges) {

        Optional<Expense> optionalExpenseToDelete = expenseRepository.findByIdAndDeletedFalse(expenseById);

        if (optionalExpenseToDelete.isPresent()) {
            Expense expenseToDelete = optionalExpenseToDelete.get();
            expenseToDelete.setLastUpdatedTime(LocalDateTime.now());
            expenseToDelete.setUpdatedBy(userToMakeChanges.getUsername());
            expenseToDelete.setDeleted(true);
            expenseRepository.save(expenseToDelete);
            return true;
        }

        throw new ResourceNotFoundException("There is not an expense with such an id");
    }

    private Expense setExpenseNonNullFields(
        @NotNull(message = "The provided expense dto cannot be null")
        ExpenseDto expenseFieldsToChange,
        @NotNull(message = "The provided expense cannot be null")
        Expense expenseToUpdate) {

        if (expenseFieldsToChange.getDescription() != null) {
            expenseToUpdate.setDescription(expenseFieldsToChange.getDescription());
        }

        if (expenseFieldsToChange.getAmount() != null) {
            expenseToUpdate.setAmount(expenseFieldsToChange.getAmount());
        }

        if (expenseFieldsToChange.isApproved()) {
            expenseToUpdate.setApproved(expenseFieldsToChange.isApproved());
        }

        return expenseToUpdate;
    }
}
