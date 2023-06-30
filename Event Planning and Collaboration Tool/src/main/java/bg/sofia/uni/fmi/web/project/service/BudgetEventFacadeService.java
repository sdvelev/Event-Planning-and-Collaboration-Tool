package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.enums.BudgetExpenditureCategory;
import bg.sofia.uni.fmi.web.project.model.Budget;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Expense;
import bg.sofia.uni.fmi.web.project.model.User;
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
public class BudgetEventFacadeService {

    private final BudgetService budgetService;
    private final EventService eventService;
    private final ExpenseService expenseService;

    @Autowired
    public BudgetEventFacadeService(BudgetService budgetService, EventService eventService, ExpenseService expenseService) {
        this.budgetService = budgetService;
        this.eventService = eventService;
        this.expenseService = expenseService;
    }

    @Transactional
    public Budget createBudgetWithEvent(
        @NotNull(message = "The provided budget cannot be null")
        Budget budgetToSave,
        @NotNull(message = "The provided associated event id cannot be null")
        @Positive(message = "The provided associated event id must be positive")
        Long eventIdToAssociate,
        @NotNull(message = "The user who makes changes cannot be null")
        User userToMakeChanges) {

        Optional<Event> potentialEventToAssociate = eventService.getEventById(eventIdToAssociate);

        if (potentialEventToAssociate.isPresent() && !potentialEventToAssociate.get().isDeleted()) {
            budgetToSave.setAssociatedEvent(potentialEventToAssociate.get());

            if (!budgetToSave.getExpenditureCategory().equals(BudgetExpenditureCategory.ALL) &&
                getAllBudgetsForEvent(eventIdToAssociate).stream()
                    .noneMatch(budget -> budget.getExpenditureCategory().equals(BudgetExpenditureCategory.ALL))) {
                throw new MethodNotAllowed("There is not an overall budget for that event. You cannot create " +
                    "budgets for categories if total budget is missing");
            }

            if (budgetToSave.getExpenditureCategory().equals(BudgetExpenditureCategory.OTHER) ||
                getAllBudgetsForEvent(eventIdToAssociate).stream()
                    .noneMatch(
                        budget -> budget.getExpenditureCategory().equals(budgetToSave.getExpenditureCategory()))) {

                if (!budgetToSave.getExpenditureCategory().equals(BudgetExpenditureCategory.ALL)) {
                    BigDecimal totalBudget;

                    try {
                        totalBudget = getTotalBudgetForEvent(eventIdToAssociate).getAmount();
                    } catch (MethodNotAllowed e) {
                        totalBudget = BigDecimal.valueOf(0);
                    }

                    BigDecimal currentTotal;
                    if (getAllBudgetsForEvent(eventIdToAssociate).stream()
                        .filter(budget -> !budget.getExpenditureCategory().equals(BudgetExpenditureCategory.ALL))
                        .map(Budget::getAmount)
                        .reduce(BigDecimal::add).isPresent()) {

                        currentTotal = getAllBudgetsForEvent(eventIdToAssociate).stream()
                            .filter(budget -> !budget.getExpenditureCategory().equals(BudgetExpenditureCategory.ALL))
                            .map(Budget::getAmount)
                            .reduce(BigDecimal::add).get();
                    } else {
                        currentTotal = BigDecimal.valueOf(0);
                    }

                    if (totalBudget.compareTo(currentTotal.add(budgetToSave.getAmount())) < 0) {
                        throw new MethodNotAllowed("You can't add this budget as it is over the limit");
                    }
                }

                budgetService.createBudget(budgetToSave, userToMakeChanges);
                potentialEventToAssociate.get().getAssociatedBudgets().add(budgetToSave);
                return budgetToSave;
            }
        }
        throw new MethodNotAllowed("There is already a budget in that category for that event");
    }

    public Budget getTotalBudgetForEvent(
        @NotNull(message = "The provided associated event id cannot be null")
        @Positive(message = "The provided associated event id must be positive")
        Long eventIdToAssociate) {

        List<Budget> allBudgets = getAllBudgetsForEvent(eventIdToAssociate);

        for (Budget currentBudget : allBudgets) {
            if (currentBudget.getExpenditureCategory().equals(BudgetExpenditureCategory.ALL)) {
                return currentBudget;
            }
        }
        throw new MethodNotAllowed("There is not a total budget for that event");
    }

    public List<Budget> getAllBudgetsForEvent(
        @NotNull(message = "The provided associated event id cannot be null")
        @Positive(message = "The provided associated event id must be positive")
        Long eventIdToAssociate) {

        Optional<Event> potentialEventToAssociate = eventService.getEventById(eventIdToAssociate);
        if (potentialEventToAssociate.isPresent() && !potentialEventToAssociate.get().isDeleted()) {
            return budgetService.getBudgetsByEvent(potentialEventToAssociate.get());
        }
        throw new ResourceNotFoundException("There is not an event with such id");
    }

    @Transactional
    public boolean deleteBudgetWithCategoryLogic( @NotNull(message = "The provided associated event id cannot be null")
                                                  @Positive(message = "The provided associated event id must be positive")
                                                  Long eventIdToAssociate,
                                                  @NotNull(message = "The provided budget id cannot be null")
                                                  @Positive(message = "The provided budget id must be positive")
                                                 Long budgetId,
                                                  @NotNull(message = "The user who makes changes cannot be null")
                                                  User userToMakeChanges) {

        Optional<Budget> optionalBudgetToDelete = budgetService.getBudgetById(budgetId);

        if (optionalBudgetToDelete.isPresent() && !optionalBudgetToDelete.get().isDeleted()) {

            Budget budgetToDelete = optionalBudgetToDelete.get();
            if (eventService.getEventById(eventIdToAssociate).isPresent()) {
                List<Expense> allExpensesForEvent = expenseService.getExpensesByEvent(eventService.getEventById(eventIdToAssociate).get());

                for (Expense currentExpense : allExpensesForEvent) {
                    if (currentExpense.getExpenditureCategory().toString().equals(budgetToDelete.getExpenditureCategory().toString())) {
                        expenseService.deleteExpense(currentExpense.getId(), userToMakeChanges);
                    }
                }
            } else {
                throw new ResourceNotFoundException("There is not an event with such an id");
            }

            if (budgetToDelete.getExpenditureCategory().equals(BudgetExpenditureCategory.ALL)) {
                List<Budget> allBudgets = getAllBudgetsForEvent(eventIdToAssociate);

                if (eventService.getEventById(eventIdToAssociate).isPresent()) {
                    List<Expense> allExpensesForEvent = expenseService.getExpensesByEvent(eventService.getEventById(eventIdToAssociate).get());
                    for (Expense currentExpense : allExpensesForEvent) {
                        expenseService.deleteExpense(currentExpense.getId(), userToMakeChanges);
                    }
                } else {
                    throw new ResourceNotFoundException("There is not an event with such an id");
                }

                for (Budget currentBudget : allBudgets) {
                    budgetService.deleteBudget(currentBudget.getId(), userToMakeChanges);
                }
                return true;
            }
            return budgetService.deleteBudget(budgetToDelete.getId(), userToMakeChanges);
        }

        throw new ResourceNotFoundException("There is not a budget with such an id");
    }
}
