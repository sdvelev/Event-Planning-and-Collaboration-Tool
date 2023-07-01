package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.model.Budget;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Expense;
import bg.sofia.uni.fmi.web.project.model.Participant;
import bg.sofia.uni.fmi.web.project.model.User;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class EventParticipantBudgetExpenseFacadeService {

    private EventService eventService;
    private ParticipantService participantService;
    private BudgetService budgetService;
    private ExpenseService expenseService;

    @Autowired
    public EventParticipantBudgetExpenseFacadeService(EventService eventService, ParticipantService participantService,
                                                      BudgetService budgetService, ExpenseService expenseService) {
        this.eventService = eventService;
        this.participantService = participantService;
        this.budgetService = budgetService;
        this.expenseService = expenseService;
    }

    @Transactional
    public boolean deleteEventWithParticipants(
        @NotNull(message = "The provided event id cannot be null")
        @Positive(message = "The provided event id must be positive.")
        Long eventToDeleteId,
        @NotNull(message = "The user who makes changes cannot be null")
        User userToMakeChanges) {

        Optional<Event> eventToDelete = eventService.getEventById(eventToDeleteId);

        if (eventToDelete.isEmpty()) {
            throw new ResourceNotFoundException("There is not an event with such an id");
        }

        List<Participant> participantsCopy = eventToDelete.get().getAssociatedParticipants().stream().toList();
        List<Budget> budgetsCopy = eventToDelete.get().getAssociatedBudgets().stream().toList();
        List<Expense> expensesCopy = eventToDelete.get().getAssociatedExpenses().stream().toList();

//        List<Contract> contractsCopy = eventToDelete.getAssociatedContracts().stream().toList();
//        List<Guest> guestsCopy = eventToDelete.getAssociatedGuests().stream().toList();
//        List<Task> tasksCopy = eventToDelete.getAssociatedTasks().stream().toList();


        for (Participant currentParticipant : participantsCopy) {
            if (!currentParticipant.isDeleted() && participantService.getParticipantById(currentParticipant.getId()).isPresent()) {
                participantService.deleteParticipant(currentParticipant.getId(), userToMakeChanges);
            }
        }

        for (Budget currentBudget : budgetsCopy) {
            if (!currentBudget.isDeleted() && budgetService.getBudgetById(currentBudget.getId()).isPresent()) {
                budgetService.deleteBudget(currentBudget.getId(), userToMakeChanges);
            }
        }

        for (Expense currentExpense : expensesCopy) {
            if (!currentExpense.isDeleted() && expenseService.getExpenseById(currentExpense.getId()).isPresent()) {
                expenseService.deleteExpense(currentExpense.getId(), userToMakeChanges);
            }
        }

//        for (Contract currentContract : contractsCopy) {
//            if (!currentContract.isDeleted() && contractService.getContractById(currentContract.getId()).isPresent()) {
//                contractService.deleteContract(currentContract.getId());
//            }
//        }
//
//        for (Guest currentGuest : guestsCopy) {
//            if (!currentGuest.isDeleted() && guestService.getExpenseById(currentGuest.getId()).isPresent()) {
//                guestService.deleteGuest(currentGuest.getId());
//            }
//        }
//
//        for (Task currentTask : tasksCopy) {
//            if (!currentTask.isDeleted() && taskService.getTaskById(currentTask.getId()).isPresent()) {
//                taskService.deleteTask(currentTask.getId());
//            }
//        }

        eventService.deleteEvent(eventToDeleteId, userToMakeChanges);
        return true;
    }

}
