package bg.sofia.uni.fmi.web.project.controller;

import bg.sofia.uni.fmi.web.project.dto.BudgetDto;
import bg.sofia.uni.fmi.web.project.dto.EventDto;
import bg.sofia.uni.fmi.web.project.enums.ExpenditureCategory;
import bg.sofia.uni.fmi.web.project.mapper.BudgetMapper;
import bg.sofia.uni.fmi.web.project.mapper.EventMapper;
import bg.sofia.uni.fmi.web.project.model.Budget;
import bg.sofia.uni.fmi.web.project.service.BudgetEventFacadeService;
import bg.sofia.uni.fmi.web.project.service.BudgetService;
import jakarta.validation.Valid;
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
@RequestMapping(path = "/budgets")
@Validated
public class BudgetController {
    private final BudgetService budgetService;
    private final BudgetEventFacadeService budgetEventFacadeService;
    private final BudgetMapper budgetMapper;

    @Autowired
    public BudgetController(BudgetService budgetService, BudgetEventFacadeService budgetEventFacadeService,
                            BudgetMapper budgetMapper) {
        this.budgetService = budgetService;
        this.budgetEventFacadeService = budgetEventFacadeService;
        this.budgetMapper = budgetMapper;
    }

    @GetMapping
    public List<BudgetDto> getAllBudgets() {
        final List<Budget> toReturnList = budgetService.getBudgets();

        return toReturnList.stream()
            .map(budgetMapper::toDto)
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/search", params = {"id"})
    public BudgetDto getBudgetById(@RequestParam("id") Long id)  {

        Optional<Budget> potentialBudget = budgetService.getBudgetById(id);

        if (potentialBudget.isPresent()) {
            return budgetMapper.toDto(potentialBudget.get());
        }

        return null;
    }

    @GetMapping(value = "/description", params = {"id"})
    public String getDescriptionById(@RequestParam("id") Long id)  {

        return budgetService.getDescriptionByBudgetId(id);
    }

    @GetMapping(value = "/expenditure_category", params = {"id"})
    public ExpenditureCategory getBudgetExpenditureCategoryById(@RequestParam("id") Long id)  {

        return budgetService.getExpenditureCategoryByBudgetId(id);
    }

    @GetMapping(value = "/amount", params = {"id"})
    public BigDecimal getBudgetAmountById(@RequestParam("id") Long id)  {

        return budgetService.getAmountByBudgetId(id);
    }

    @GetMapping(value = "/event", params = {"id"})
    public EventDto getBudgetEventById(@RequestParam("id") Long id)  {

        return EventMapper.INSTANCE.toDto(budgetService.getEventByBudgetId(id));
    }

    @PostMapping(params = {"assigned_event_id"})
    public Long addBudget(@Valid @NotNull @RequestBody BudgetDto budgetDto,
                               @Valid @NotNull @RequestParam("assigned_event_id") Long assignedEventId) {
        Budget potentialBudgetToCreate = budgetEventFacadeService
            .createBudgetWithEvent(budgetMapper.toEntity(budgetDto), assignedEventId);

        if (potentialBudgetToCreate != null) {
            return potentialBudgetToCreate.getId();
        }

        return -1L;
    }

    @DeleteMapping(params = {"id"})
    public boolean removeBudgetById(@RequestParam("id") Long budgetId) {
        return budgetService.deleteBudget(budgetId);
    }

    @PutMapping(value = "/set", params = {"budget_id"})
    public boolean setBudgetByBudgetId(@RequestParam("budget_id")
                                                 @NotNull(message = "Budget id cannot be null")
                                                 @Positive(message = "Budget id must be positive")
                                                 Long budgetId,
                                                 @RequestBody
                                                 @NotNull(message = "Budget record cannot be null")
                                                 BudgetDto budgetToUpdate) {
        //TODO: Authorization in order to update budget
        return budgetService.setBudgetById(budgetToUpdate, budgetId);
    }
}
