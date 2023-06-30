package bg.sofia.uni.fmi.web.project.controller;

import bg.sofia.uni.fmi.web.project.dto.BudgetDto;
import bg.sofia.uni.fmi.web.project.dto.EventDto;
import bg.sofia.uni.fmi.web.project.enums.BudgetExpenditureCategory;
import bg.sofia.uni.fmi.web.project.mapper.BudgetMapper;
import bg.sofia.uni.fmi.web.project.mapper.EventMapper;
import bg.sofia.uni.fmi.web.project.model.Budget;
import bg.sofia.uni.fmi.web.project.service.BudgetEventFacadeService;
import bg.sofia.uni.fmi.web.project.service.BudgetService;
import bg.sofia.uni.fmi.web.project.service.UserService;
import bg.sofia.uni.fmi.web.project.service.security.TokenManagerService;
import bg.sofia.uni.fmi.web.project.validation.ApiBadRequest;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
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

import static bg.sofia.uni.fmi.web.project.config.security.RequestManager.getUserByRequest;

@RestController
@RequestMapping(path = "/budgets")
@Validated
public class BudgetController {
    private final BudgetService budgetService;
    private final BudgetEventFacadeService budgetEventFacadeService;
    private final BudgetMapper budgetMapper;
    private final TokenManagerService tokenManagerService;
    private final UserService userService;

    @Autowired
    public BudgetController(BudgetService budgetService, BudgetEventFacadeService budgetEventFacadeService,
                            BudgetMapper budgetMapper, TokenManagerService tokenManagerService,
                            UserService userService) {
        this.budgetService = budgetService;
        this.budgetEventFacadeService = budgetEventFacadeService;
        this.budgetMapper = budgetMapper;
        this.tokenManagerService = tokenManagerService;
        this.userService = userService;
    }

    @GetMapping
    public List<BudgetDto> getAllBudgets() {

        return budgetMapper.toDtoList(budgetService.getBudgets());
    }

    @GetMapping(value = "/search", params = {"id"})
    public BudgetDto getBudgetById(@RequestParam("id")
                                       @NotNull(message = "The provided budget id cannot be null")
                                       @Positive(message = "The provided budget id must be positive")
                                       Long id)  {

        Optional<Budget> potentialBudget = budgetService.getBudgetById(id);

        if (potentialBudget.isPresent()) {
            return budgetMapper.toDto(potentialBudget.get());
        }

        throw new ResourceNotFoundException("Budget with such an id cannot be found");
    }

    @GetMapping(value = "/search", params = {"event_id"})
    public List<BudgetDto> getAllBudgetsByEventId(@RequestParam("event_id")
                                                  @NotNull(message = "The provided event id cannot be null")
                                                  @Positive(message = "The provided event id must be positive")
                                                  Long eventId) {

        return budgetMapper.toDtoList(budgetEventFacadeService.getAllBudgetsForEvent(eventId));
    }

    @GetMapping(value = "/description", params = {"id"})
    public String getDescriptionById(@RequestParam("id")
                                         @NotNull(message = "The provided budget id cannot be null")
                                         @Positive(message = "The provided budget id must be positive")
                                         Long id)  {

        return budgetService.getDescriptionByBudgetId(id);
    }

    @GetMapping(value = "/expenditure_category", params = {"id"})
    public BudgetExpenditureCategory getBudgetExpenditureCategoryById(@RequestParam("id")
                                                                    @NotNull(message = "The provided budget id cannot be null")
                                                                    @Positive(message = "The provided budget id must be positive")
                                                                    Long id)  {

        return budgetService.getExpenditureCategoryByBudgetId(id);
    }

    @GetMapping(value = "/amount", params = {"id"})
    public BigDecimal getBudgetAmountById(@RequestParam("id")
                                              @NotNull(message = "The provided budget id cannot be null")
                                              @Positive(message = "The provided budget id must be positive")
                                              Long id)  {

        return budgetService.getAmountByBudgetId(id);
    }

    @GetMapping(value = "/event", params = {"id"})
    public EventDto getBudgetEventById(@RequestParam("id")
                                           @NotNull(message = "The provided budget id cannot be null")
                                           @Positive(message = "The provided budget id must be positive")
                                           Long id)  {

        return EventMapper.INSTANCE.toDto(budgetService.getEventByBudgetId(id));
    }

    @PostMapping(params = {"assigned_event_id"})
    public Long addBudget(@NotNull(message = "The provided budget dto as body of the query cannot be null")
                          @RequestBody BudgetDto budgetDto,
                          @NotNull(message = "The provided associated event id cannot be null")
                          @Positive(message = "The provided associated event id must be positive")
                          @RequestParam("assigned_event_id")
                               Long assignedEventId,
                          HttpServletRequest request) {
        Budget potentialBudgetToCreate = budgetEventFacadeService
            .createBudgetWithEvent(budgetMapper.toEntity(budgetDto), assignedEventId,
                getUserByRequest(request, tokenManagerService, userService));

        if (potentialBudgetToCreate != null) {
            return potentialBudgetToCreate.getId();
        }

        throw new ApiBadRequest("There was a problem in creating a budget");
    }

    @DeleteMapping(params = {"id", "assigned_event_id"})
    public boolean removeBudgetById(@RequestParam("id")
                                        @NotNull(message = "The provided budget id cannot be null")
                                        @Positive(message = "The provided budget id must be positive")
                                        Long budgetId,
                                    @NotNull(message = "The provided associated event id cannot be null")
                                    @Positive(message = "The provided associated event id must be positive")
                                    @RequestParam("assigned_event_id")
                                    Long assignedEventId,
                                    HttpServletRequest request) {
        return budgetEventFacadeService.deleteBudgetWithCategoryLogic(assignedEventId, budgetId,
            getUserByRequest(request, tokenManagerService, userService));
    }

    @PutMapping(value = "/set", params = {"budget_id"})
    public boolean setBudgetByBudgetId(@RequestParam("budget_id")
                                           @NotNull(message = "The provided budget id cannot be null")
                                           @Positive(message = "The provided budget id must be positive")
                                           Long budgetId,
                                           @RequestBody
                                           @NotNull(message = "The provided budget dto as body of the query cannot be null")
                                           BudgetDto budgetToUpdate,
                                       HttpServletRequest request) {
        return budgetService.setBudgetById(budgetToUpdate, budgetId,
            getUserByRequest(request,tokenManagerService, userService));
    }
}
