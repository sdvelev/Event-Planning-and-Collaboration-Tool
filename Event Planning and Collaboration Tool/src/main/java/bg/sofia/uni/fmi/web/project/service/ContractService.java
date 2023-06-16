package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.dto.ContractDto;
import bg.sofia.uni.fmi.web.project.model.Contract;
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

    public long addContract(@NotNull(message = "The given vendor cannot be null!")
                            Contract contractToSave) {
        Contract contract = contractRepository.save(contractToSave);
        checkForSaveException(contract);

        return contract.getId();
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
        validateForDeletedContract(contract);

        return contract;
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

    public boolean setContractByContractId(@Positive(message = "The contract id must be positive!")
                                           long contractId,
                                           @NotNull(message = "The given contract dto cannot be null!")
                                           ContractDto contractToUpdateDto) {
        Contract contract = getContractById(contractId);
        validateContract(contract);
        validateForDeletedContract(contract);

        Contract newContractToSave = updateFields(contractToUpdateDto, contract);
        newContractToSave.setUpdatedBy("b");
        newContractToSave.setLastUpdatedTime(LocalDateTime.now());

        contractRepository.save(newContractToSave);

        return true;
    }

    public boolean delete(boolean deleted,
                          @Positive(message = "The given ID cannot be less than zero!") long vendorId) {
        Contract contract = contractRepository.findContractByIdEquals(vendorId);
        validateContract(contract);
        validateForDeletedContract(contract);

        contract.setDeleted(deleted);
        contractRepository.save(contract);
        return true;
    }

    private Contract updateFields(ContractDto contractToUpdateDto, Contract newContractToSave) {
        if (contractToUpdateDto.getTotalPrice() != null &&
            !contractToUpdateDto.getTotalPrice().equals(newContractToSave.getTotalPrice())) {

            newContractToSave.setTotalPrice(contractToUpdateDto.getTotalPrice());
        }
        if (contractToUpdateDto.isFinished() != newContractToSave.isFinished()) {
            newContractToSave.setFinished(contractToUpdateDto.isFinished());
        }

        return newContractToSave;
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

    private void checkForSaveException(Contract contract) {
        if (contract == null) {
            throw new RuntimeException("There was problem while saving the contract in the database!");
        }
    }

    private void validateForDeletedContract(Contract contract) {
        if (contract.isDeleted()) {
            throw new MethodNotAllowed("The current record has already been deleted!");
        }
    }
}