package bg.sofia.uni.fmi.web.project.controller;

import bg.sofia.uni.fmi.web.project.dto.BudgetDto;
import bg.sofia.uni.fmi.web.project.dto.EventDto;
import bg.sofia.uni.fmi.web.project.dto.ExpenseDto;
import bg.sofia.uni.fmi.web.project.enums.BudgetExpenditureCategory;
import bg.sofia.uni.fmi.web.project.enums.ExpenseExpenditureCategory;
import bg.sofia.uni.fmi.web.project.mapper.EventMapper;
import bg.sofia.uni.fmi.web.project.mapper.ExpenseMapper;
import bg.sofia.uni.fmi.web.project.model.Expense;
import bg.sofia.uni.fmi.web.project.service.ExpenseEventFacadeService;
import bg.sofia.uni.fmi.web.project.service.ExpenseService;
import bg.sofia.uni.fmi.web.project.validation.ApiBadRequest;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/expenses")
@Validated
public class ExpenseController {
    private final ExpenseService expenseService;
    private final ExpenseEventFacadeService expenseEventFacadeService;
    private final ExpenseMapper expenseMapper;

    @Autowired
    public ExpenseController(ExpenseService expenseService, ExpenseEventFacadeService expenseEventFacadeService,
                             ExpenseMapper expenseMapper) {
        this.expenseService = expenseService;
        this.expenseEventFacadeService = expenseEventFacadeService;
        this.expenseMapper = expenseMapper;
    }

    @GetMapping
    public List<ExpenseDto> getAllExpenses() {

        return expenseMapper.toDtoList(expenseService.getExpenses());
    }

    @GetMapping(value = "/search", params = {"id"})
    public ExpenseDto getExpenseById(@RequestParam("id")
                                     @NotNull(message = "The provided expense id cannot be null")
                                     @Positive(message = "The provided expense id must be positive")
                                     Long id) {

        Optional<Expense> potentialExpense = expenseService.getExpenseById(id);

        if (potentialExpense.isPresent()) {
            return expenseMapper.toDto(potentialExpense.get());
        }

        throw new ResourceNotFoundException("Expense with such an id cannot be found");
    }

    @GetMapping(value = "/search", params = {"event_id"})
    public List<ExpenseDto> getAllExpensesByEventId(@RequestParam("event_id")
                                                  @NotNull(message = "The provided event id cannot be null")
                                                  @Positive(message = "The provided event id must be positive")
                                                  Long eventId) {

        return expenseMapper.toDtoList(expenseEventFacadeService.getAllExpensesForEvent(eventId));
    }

    @GetMapping(value = "/search", params = {"event_id", "expenditure_category"})
    public List<ExpenseDto> getAllExpensesWithExpenditureCategoryByEventId(@RequestParam("event_id")
                                                    @NotNull(message = "The provided event id cannot be null")
                                                    @Positive(message = "The provided event id must be positive")
                                                    Long eventId,
                                                    @RequestParam("expenditure_category")
                                                    @NotNull(message = "The provided expenditure category cannot be null")
                                                    ExpenseExpenditureCategory expenditureCategory) {
        return expenseMapper.toDtoList(expenseEventFacadeService.getAllExpensesForEvent(eventId)).stream()
            .filter(expenseDto -> expenseDto.getExpenditureCategory().equals(expenditureCategory))
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/expenditure_category", params = {"id"})
    public ExpenseExpenditureCategory getExpenseExpenditureCategoryById(@RequestParam("id")
                                                                        @NotNull(message = "The provided expense id cannot be null")
                                                                        @Positive(message = "The provided expense id must be positive")
                                                                        Long id) {

        return expenseService.getExpenditureCategoryByExpenseId(id);
    }

    @GetMapping(value = "/description", params = {"id"})
    public String getDescriptionById(@RequestParam("id")
                                     @NotNull(message = "The provided expense id cannot be null")
                                     @Positive(message = "The provided expense id must be positive")
                                     Long id) {

        return expenseService.getDescriptionByExpenseId(id);
    }

    @GetMapping(value = "/amount", params = {"id"})
    public BigDecimal getExpenseAmountById(@RequestParam("id")
                                           @NotNull(message = "The provided expense id cannot be null")
                                           @Positive(message = "The provided expense id must be positive")
                                           Long id) {

        return expenseService.getAmountByExpenseId(id);
    }

    @GetMapping(value = "/event", params = {"id"})
    public EventDto getExpenseEventById(@RequestParam("id")
                                        @NotNull(message = "The provided expense id cannot be null")
                                        @Positive(message = "The provided expense id must be positive")
                                        Long id) {

        return EventMapper.INSTANCE.toDto(expenseService.getEventByExpenseId(id));
    }

    @PostMapping(params = {"assigned_event_id"})
    public Long addExpense(@NotNull(message = "The provided expense dto as body of the query cannot be null")
                           @RequestBody ExpenseDto expenseDto,
                           @NotNull(message = "The provided assigned event id cannot be null")
                           @Positive(message = "The provided assigned event id must be positive")
                           @RequestParam("assigned_event_id") Long assignedEventId) {
        Expense potentialExpenseToCreate = expenseEventFacadeService
            .createExpenseWithEvent(expenseMapper.toEntity(expenseDto), assignedEventId);

        if (potentialExpenseToCreate != null) {
            return potentialExpenseToCreate.getId();
        }

        throw new ApiBadRequest("There was a problem in creating an expense");
    }

    @DeleteMapping(params = {"id"})
    public boolean removeExpenseById(@RequestParam("id")
                                     @NotNull(message = "The provided expense id cannot be null")
                                     @Positive(message = "The provided expense id must be positive")
                                     Long expenseId) {
        return expenseService.deleteExpense(expenseId);
    }

    @PutMapping(value = "/set", params = {"expense_id"})
    public boolean setExpenseByExpenseId(@RequestParam("expense_id")
                                         @NotNull(message = "The provided expense id cannot be null")
                                         @Positive(message = "The provided expense id must be positive")
                                         Long expenseId,
                                         @RequestBody
                                         @NotNull(message = "The provided expense dto as body of the query cannot be null")
                                         ExpenseDto expenseToUpdate) {
        //TODO: Authorization in order to update expense
        return expenseService.setExpenseById(expenseToUpdate, expenseId);
    }
}
