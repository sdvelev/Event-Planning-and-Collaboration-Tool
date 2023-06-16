package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.dto.ExpenseDto;
import bg.sofia.uni.fmi.web.project.enums.ExpenditureCategory;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Expense;
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
        Expense expenseToSave) {
        expenseToSave.setCreationTime(LocalDateTime.now());
        expenseToSave.setDeleted(false);

        return expenseRepository.save(expenseToSave);
    }

    public List<Expense> getExpenses() {
        return expenseRepository.findAll()
            .parallelStream()
            .filter(expense -> !expense.isDeleted())
            .collect(Collectors.toList());
    }

    public Optional<Expense> getExpenseById(
        @NotNull(message = "The provided expense id cannot be null")
        @Positive(message = "The provided expense id must be positive")
        Long id) {

        Optional<Expense> potentialExpenseToReturn = expenseRepository.findById(id);

        if (potentialExpenseToReturn.isPresent() && !potentialExpenseToReturn.get().isDeleted()) {
            return potentialExpenseToReturn;
        }

        throw new ResourceNotFoundException("Expense with such an id cannot be found");
    }

    public String getDescriptionByExpenseId(
        @NotNull(message = "The provided expense id cannot be null")
        @Positive(message = "The provided expense id must be positive")
        Long id) {
        Optional<Expense> optionalExpense = this.getExpenseById(id);

        if (optionalExpense.isPresent() && !optionalExpense.get().isDeleted()) {
            return optionalExpense.get().getDescription();
        }
        return null;
    }

    public ExpenditureCategory getExpenditureCategoryByExpenseId(
        @NotNull(message = "The provided expense id cannot be null")
        @Positive(message = "The provided expense id must be positive")
        Long id) {
        Optional<Expense> optionalExpense = this.getExpenseById(id);

        if (optionalExpense.isPresent() && !optionalExpense.get().isDeleted()) {
            return optionalExpense.get().getExpenditureCategory();
        }
        return null;
    }

    public BigDecimal getAmountByExpenseId(
        @NotNull(message = "The provided expense id cannot be null")
        @Positive(message = "The provided expense id must be positive")
        Long id) {
        Optional<Expense> optionalExpense = this.getExpenseById(id);

        if (optionalExpense.isPresent() && !optionalExpense.get().isDeleted()) {
            return optionalExpense.get().getAmount();
        }
        return null;
    }

    public Event getEventByExpenseId(
        @NotNull(message = "The provided expense id cannot be null")
        @Positive(message = "The provided expense id must be positive")
        Long id) {
        Optional<Expense> optionalExpense = this.getExpenseById(id);

        if (optionalExpense.isPresent() && !optionalExpense.get().isDeleted()) {
            return optionalExpense.get().getAssociatedEvent();
        }
        return null;
    }

    public boolean setExpenseById(
        @NotNull(message = "The provided expense dto cannot be null")
        ExpenseDto expenseFieldsToChange,
        @NotNull(message = "The provided expense id cannot be null")
        @Positive(message = "The provided expense id must be positive")
        Long expenseId) {

        Optional<Expense> optionalExpenseToUpdate = expenseRepository.findById(expenseId);

        if (optionalExpenseToUpdate.isPresent() && !optionalExpenseToUpdate.get().isDeleted()) {

            Expense expenseToUpdate = setExpenseNonNullFields(expenseFieldsToChange,
                optionalExpenseToUpdate.get());;
            expenseToUpdate.setLastUpdatedTime(LocalDateTime.now());
            expenseRepository.save(expenseToUpdate);
            return true;
        }

        throw new ResourceNotFoundException("There is not an expense with such an id");
    }

    public boolean deleteExpense(
        @NotNull(message = "The provided expense id cannot be null")
        @Positive(message = "The provided expense id must be positive")
        Long expenseById) {

        Optional<Expense> optionalExpenseToDelete = expenseRepository.findById(expenseById);

        if (optionalExpenseToDelete.isPresent() && !optionalExpenseToDelete.get().isDeleted()) {
            Expense expenseToDelete = optionalExpenseToDelete.get();
            expenseToDelete.setLastUpdatedTime(LocalDateTime.now());
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

        if (expenseFieldsToChange.getExpenditureCategory() != null) {
            expenseToUpdate.setExpenditureCategory(expenseFieldsToChange.getExpenditureCategory());
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
