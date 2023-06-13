package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Expense;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExpenseEventFacadeService {

    private final ExpenseService expenseService;
    private final EventService eventService;

    @Autowired
    public ExpenseEventFacadeService(ExpenseService expenseService, EventService eventService) {
        this.expenseService = expenseService;
        this.eventService = eventService;
    }

    @Transactional
    public Expense createExpenseWithEvent(
        @NotNull(message = "Expense cannot be null")
        Expense expenseToSave,
        @NotNull(message = "Associated event id cannot be null")
        @Positive(message = "Associated event id must be positive.")
        Long eventIdToAssociate) {

        Optional<Event> potentialEventToAssociate = eventService.getEventById(eventIdToAssociate);

        if (potentialEventToAssociate.isPresent() && !potentialEventToAssociate.get().isDeleted()) {
            expenseToSave.setAssociatedEvent(potentialEventToAssociate.get());
            expenseService.createExpense(expenseToSave);
            potentialEventToAssociate.get().getAssociatedExpenses().add(expenseToSave);
        }
        return expenseToSave;
    }
}
