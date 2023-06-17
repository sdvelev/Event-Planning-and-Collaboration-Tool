package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.model.Budget;
import bg.sofia.uni.fmi.web.project.model.Event;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
public class BudgetEventFacadeService {

    private final BudgetService budgetService;
    private final EventService eventService;

    @Autowired
    public BudgetEventFacadeService(BudgetService budgetService, EventService eventService) {
        this.budgetService = budgetService;
        this.eventService = eventService;
    }

    @Transactional
    public Budget createBudgetWithEvent(
        @NotNull(message = "The provided budget cannot be null")
        Budget budgetToSave,
        @NotNull(message = "The provided associated event id cannot be null")
        @Positive(message = "The provided associated event id must be positive")
        Long eventIdToAssociate) {

        Optional<Event> potentialEventToAssociate = eventService.getEventById(eventIdToAssociate);

        if (potentialEventToAssociate.isPresent() && !potentialEventToAssociate.get().isDeleted()) {
            budgetToSave.setAssociatedEvent(potentialEventToAssociate.get());
            budgetService.createBudget(budgetToSave);
            potentialEventToAssociate.get().getAssociatedBudgets().add(budgetToSave);
        }
        return budgetToSave;

    }
}
