package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.enums.BudgetExpenditureCategory;
import bg.sofia.uni.fmi.web.project.enums.ExpenseExpenditureCategory;
import bg.sofia.uni.fmi.web.project.model.Budget;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Expense;
import bg.sofia.uni.fmi.web.project.validation.MethodNotAllowed;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ExpenseEventFacadeService {

    private final ExpenseService expenseService;
    private final EventService eventService;
    private final BudgetService budgetService;

    @Autowired
    public ExpenseEventFacadeService(ExpenseService expenseService, EventService eventService, BudgetService budgetService) {
        this.expenseService = expenseService;
        this.eventService = eventService;
        this.budgetService = budgetService;
    }

    @Transactional
    public Expense createExpenseWithEvent(
        @NotNull(message = "The provided expense cannot be null")
        Expense expenseToSave,
        @NotNull(message = "The provided associated event id cannot be null")
        @Positive(message = "The provided associated event id must be positive")
        Long eventIdToAssociate) {

        Optional<Event> potentialEventToAssociate = eventService.getEventById(eventIdToAssociate);

        if (potentialEventToAssociate.isPresent() && !potentialEventToAssociate.get().isDeleted()) {

            Budget relevantBudget = getBudgetByExpenditureCategoryForEvent(eventIdToAssociate,
                BudgetExpenditureCategory.valueOf(expenseToSave.getExpenditureCategory().toString()));

            BigDecimal totalAmountOfExpenses = getSumOfAllExpensesByExpenditureCategoryForEvent(eventIdToAssociate,
                expenseToSave.getExpenditureCategory());

            if (relevantBudget.getAmount().compareTo(totalAmountOfExpenses.add(expenseToSave.getAmount())) < 0) {
                throw new MethodNotAllowed("You cannot create expense as the budget for that category is over");
            }

            expenseToSave.setAssociatedEvent(potentialEventToAssociate.get());
            expenseService.createExpense(expenseToSave);
            potentialEventToAssociate.get().getAssociatedExpenses().add(expenseToSave);
        }
        return expenseToSave;
    }

    public Budget getBudgetByExpenditureCategoryForEvent(
        @NotNull(message = "The provided associated event id cannot be null")
        @Positive(message = "The provided associated event id must be positive")
        Long eventIdToAssociate,
        @NotNull(message = "The provided expenditureCategory cannot be null")
        BudgetExpenditureCategory expenditureCategory) {
        List<Budget> allBudgets = getAllBudgetsForEvent(eventIdToAssociate);

        for (Budget currentBudget : allBudgets) {
            if (currentBudget.getExpenditureCategory().equals(expenditureCategory)) {
                return currentBudget;
            }
        }
        throw new MethodNotAllowed("There is not a budget with that expenditure category. Expense cannot be created");
    }

    private List<Budget> getAllBudgetsForEvent(
        @NotNull(message = "The provided associated event id cannot be null")
        @Positive(message = "The provided associated event id must be positive")
        Long eventIdToAssociate) {

        Optional<Event> potentialEventToAssociate = eventService.getEventById(eventIdToAssociate);
        if (potentialEventToAssociate.isPresent() && !potentialEventToAssociate.get().isDeleted()) {
            return budgetService.getBudgetsByEvent(potentialEventToAssociate.get());
        }
        throw new ResourceNotFoundException("There is not an event with such id");
    }

    public BigDecimal getSumOfAllExpensesByExpenditureCategoryForEvent(
        @NotNull(message = "The provided associated event id cannot be null")
        @Positive(message = "The provided associated event id must be positive")
        Long eventIdToAssociate,
        @NotNull(message = "The provided expenditure category cannot be null")
        ExpenseExpenditureCategory expenditureCategory) {
        List<Expense> allExpensesForEvent = getAllExpensesForEvent(eventIdToAssociate);

        BigDecimal totalSum = BigDecimal.valueOf(0);
        for (Expense currentExpense : allExpensesForEvent) {
            if (currentExpense.getExpenditureCategory().equals(expenditureCategory)) {
                totalSum = totalSum.add(currentExpense.getAmount());
            }
        }
        return totalSum;
    }

    public List<Expense> getAllExpensesForEvent(
        @NotNull(message = "The provided associated event id cannot be null")
        @Positive(message = "The provided associated event id must be positive")
        Long eventIdToAssociate) {

        Optional<Event> potentialEventToAssociate = eventService.getEventById(eventIdToAssociate);
        if (potentialEventToAssociate.isPresent() && !potentialEventToAssociate.get().isDeleted()) {
            return expenseService.getExpensesByEvent(potentialEventToAssociate.get());
        }
        throw new ResourceNotFoundException("There is not an event with such id");
    }
}
