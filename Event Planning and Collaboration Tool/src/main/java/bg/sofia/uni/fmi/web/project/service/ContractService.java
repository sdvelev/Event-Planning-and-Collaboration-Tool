package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.model.Contract;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Vendor;
import bg.sofia.uni.fmi.web.project.repository.ContractRepository;
import bg.sofia.uni.fmi.web.project.validation.MethodNotAllowed;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Validated
@AllArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final EventService eventService;
    private final VendorService vendorService;

    public long addContract(@NotNull(message = "The given vendor cannot be null!")
                            Contract contractToSave,
                            @NotNull(message = "The event id cannot be null!")
                            Long eventId,
                            @NotNull(message = "The vendor id cannot be null!")
                            Long vendorId) {
//        if (!validateForExistingGuestByNameAndSurname(guestToSave) || !validateForExistingGuestByEventId(guestToSave)) {
//            throw new ApiBadRequest("There is already a guest with the same credentials");
//        }

        Event event = eventService.getEventById(eventId);
        Vendor vendor = vendorService.getVendorById(vendorId);

//        if (event == null) {
//            throw new ApiBadRequest("There is no such event with this ID!");
//        }

        contractToSave.setAssociatedEvent(event);
        contractToSave.setAssociatedVendor(vendor);
        contractToSave.setCreatedBy("a");
        contractToSave.setCreationTime(LocalDateTime.now());
        contractToSave.setDeleted(false);
        return contractRepository.save(contractToSave).getId();
    }

    public List<Contract> getAllContracts() {
        List<Contract> contracts = contractRepository.findAll().parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();

        validateContractsList(contracts);
        return contracts;
    }

    public Contract getContractById(@Positive(message = "The given id cannot be 0 or less!") long id) {
        Contract contract = contractRepository.findContractByIdEquals(id);
        validateContract(contract);

        if (!contract.isDeleted()) {
            return contract;
        }

        throw new MethodNotAllowed("The current record has already been deleted!");
    }

    public List<Contract> getContractsByTotalPrice(@NotNull(message = "The given total price cannot be null!")
                                                   @Positive(message = "The given total price must be above 0!")
                                                   BigDecimal totalPrice) {
        List<Contract> contracts = contractRepository.findContractsByTotalPriceEquals(totalPrice).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();

        validateContractsList(contracts);
        return contracts;
    }

    public List<Contract> getContractsByFinished(boolean finished) {
        List<Contract> contracts = contractRepository.findContractsByFinishedEquals(finished).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();

        validateContractsList(contracts);
        return contracts;
    }

    public List<Contract> getContractsByAssociatedEventId(@Positive(message = "The given event id must be above 0!")
                                                          long eventId) {
        List<Contract> contracts = contractRepository.findContractsByAssociatedEventIdEquals(eventId).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();

        validateContractsList(contracts);
        return contracts;
    }

    public List<Contract> getContractsByAssociatedVendorId(@Positive(message = "The given vendor id must be above 0!")
                                                           long vendorId) {
        List<Contract> contracts = contractRepository.findContractsByAssociatedVendorIdEquals(vendorId).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();

        validateContractsList(contracts);
        return contracts;
    }

    public boolean delete(boolean deleted,
                          @Positive(message = "The given ID cannot be less than zero!") long vendorId) {
        Contract contract = contractRepository.findContractByIdEquals(vendorId);
        validateContract(contract);

        if (!contract.isDeleted()) {
            contract.setDeleted(deleted);
            contractRepository.save(contract);
            return true;
        }

        throw new MethodNotAllowed("The current record has already been deleted!");
    }

    private void validateContract(Contract contract) {
        if (contract == null) {
            throw new ResourceNotFoundException("There is no such contract in the database!");
        }
    }

    private void validateContractsList(List<Contract> contracts) {
        if (contracts == null) {
            throw new ResourceNotFoundException("There are no such contracts in the database or have been deleted!");
        }
    }
}