package bg.sofia.uni.fmi.web.project.controller;

import bg.sofia.uni.fmi.web.project.dto.ContractDto;
import bg.sofia.uni.fmi.web.project.mapper.ContractMapper;
import bg.sofia.uni.fmi.web.project.service.ContractEventVendorFacadeService;
import bg.sofia.uni.fmi.web.project.service.ContractService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/contracts")
@Validated
@AllArgsConstructor
public class ContractController {
    private final ContractService contractService;
    private final ContractEventVendorFacadeService contractEventVendorFacadeService;
    private final ContractMapper mapper;

    @PostMapping(params = {"assigned_event_id", "assigned_vendor_id"})
    public long addGuest(@Valid @NotNull(message = "The guestDto cannot be null!") @RequestBody ContractDto contractDto,
                         @Valid @NotNull(message = "The event id cannot be null!") @RequestParam("assigned_event_id")
                         Long eventId,
                         @Valid @NotNull(message = "The event id cannot be null!") @RequestParam("assigned_vendor_id")
                         Long vendorId) {
        return contractEventVendorFacadeService.addContract(mapper.toEntity(contractDto), eventId, vendorId);
    }

    @GetMapping
    public List<ContractDto> getAllContracts() {
        return mapper.toDtoCollection(contractService.getAllContracts());
    }

    @GetMapping(value = "/search", params = {"id"})
    public ResponseEntity<ContractDto> findById(@Valid
                                                @Positive(message = "ContractID must be positive")
                                                @RequestParam("id") long id) {
        return ResponseEntity.ok(mapper.toDto(contractService.getContractById(id)));
    }

    @GetMapping(value = "/search", params = {"total_price"})
    public ResponseEntity<List<ContractDto>> findByTotalPrice(@Valid
                                                              @RequestParam("total_price")
                                                              @NotNull(message = "The given total price cannot be null!")
                                                              @Positive(message = "The given total price must be above 0!")
                                                              BigDecimal totalPrice) {
        return ResponseEntity.ok(mapper.toDtoCollection(contractService.getContractsByTotalPrice(totalPrice)));
    }

    @GetMapping(value = "/search", params = {"finished"})
    public ResponseEntity<List<ContractDto>> findByFinished(@RequestParam("finished")
                                                            boolean finished) {
        return ResponseEntity.ok(mapper.toDtoCollection(contractService.getContractsByFinished(finished)));
    }

    @GetMapping(value = "/search", params = {"event_id"})
    public ResponseEntity<List<ContractDto>> findByEventId(@Valid
                                                           @RequestParam("event_id")
                                                           @Positive(message = "The given event id must be above 0!")
                                                           long eventId) {
        return ResponseEntity.ok(mapper.toDtoCollection(contractService.getContractsByAssociatedEventId(eventId)));
    }

    @GetMapping(value = "/search", params = {"vendor_id"})
    public ResponseEntity<List<ContractDto>> findByVendorId(@Valid
                                                            @RequestParam("vendor_id")
                                                            @Positive(message = "The given vendor id must be above 0!")
                                                            long vendorId) {
        return ResponseEntity.ok(mapper.toDtoCollection(contractService.getContractsByAssociatedVendorId(vendorId)));
    }

    @DeleteMapping(value = "/delete", params = {"deleted", "id"})
    public boolean deleteGuest(@RequestParam("deleted")
                               boolean deleted,
                               @Positive(message = "The given ID cannot be less than zero!")
                               @RequestParam("id")
                               long contractId) {
        return contractService.delete(deleted, contractId);
    }
}