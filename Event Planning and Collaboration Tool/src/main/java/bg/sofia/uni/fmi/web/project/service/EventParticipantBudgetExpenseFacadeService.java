package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.model.Budget;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Expense;
import bg.sofia.uni.fmi.web.project.model.Participant;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

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
        Long eventToDeleteId) {

        Event eventToDelete = eventService.deleteEvent(eventToDeleteId);

        List<Participant> participantsCopy = eventToDelete.getAssociatedParticipants().stream().toList();
        List<Budget> budgetsCopy = eventToDelete.getAssociatedBudgets().stream().toList();
        List<Expense> expensesCopy = eventToDelete.getAssociatedExpenses().stream().toList();

//        List<Contract> contractsCopy = eventToDelete.getAssociatedContracts().stream().toList();
//        List<Guest> guestsCopy = eventToDelete.getAssociatedGuests().stream().toList();
//        List<Task> tasksCopy = eventToDelete.getAssociatedTasks().stream().toList();


        for (Participant currentParticipant : participantsCopy) {
            if (participantService.getParticipantById(currentParticipant.getId()).isPresent()) {
                participantService.deleteParticipant(currentParticipant.getId());
            }
        }

        for (Budget currentBudget : budgetsCopy) {
            if (budgetService.getBudgetById(currentBudget.getId()).isPresent()) {
                budgetService.deleteBudget(currentBudget.getId());
            }
        }

        for (Expense currentExpense : expensesCopy) {
            if (expenseService.getExpenseById(currentExpense.getId()).isPresent()) {
                expenseService.deleteExpense(currentExpense.getId());
            }
        }

//        for (Contract currentContract : contractsCopy) {
//            if (contractService.getContractById(currentContract.getId()).isPresent()) {
//                contractService.deleteContract(currentContract.getId());
//            }
//        }
//
//        for (Guest currentGuest : guestsCopy) {
//            if (guestService.getExpenseById(currentGuest.getId()).isPresent()) {
//                guestService.deleteGuest(currentGuest.getId());
//            }
//        }
//
//        for (Task currentTask : tasksCopy) {
//            if (taskService.getTaskById(currentTask.getId()).isPresent()) {
//                taskService.deleteTask(currentTask.getId());
//            }
//        }

        return true;
    }

}
