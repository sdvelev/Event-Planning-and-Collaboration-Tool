package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.model.Contract;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Vendor;
import bg.sofia.uni.fmi.web.project.validation.ConflictException;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Validated
@AllArgsConstructor
public class ContractEventVendorFacadeService {
    private final ContractService contractService;
    private final EventService eventService;
    private final VendorService vendorService;

    @Transactional
    public long addContract(@NotNull(message = "The given vendor cannot be null!")
                            Contract contractToSave,
                            @NotNull(message = "The event id cannot be null!")
                            Long eventId,
                            @NotNull(message = "The vendor id cannot be null!")
                            Long vendorId) {

        validateForExistingContract(eventId, vendorId);
        Optional<Event> event = eventService.getEventById(eventId);
        validateEvent(event.get());

        Vendor vendor = vendorService.getVendorById(vendorId);
        validateVendor(vendor);

        contractToSave.setAssociatedEvent(event.get());
        contractToSave.setAssociatedVendor(vendor);

        contractToSave.setCreatedBy("a");
        contractToSave.setCreationTime(LocalDateTime.now());
        contractToSave.setDeleted(false);

        //event.setContract()
        vendor.getVendorContracts().add(contractToSave);

        return contractService.addContract(contractToSave);
    }

    private void validateEvent(Event event) {
        if (event == null) {
            throw new ResourceNotFoundException("There is no event with such id!");
        }
    }

    private void validateVendor(Vendor vendor) {
        if (vendor == null) {
            throw new ResourceNotFoundException("There is no vendor with such id!");
        }
    }

    private void validateForExistingContract(long eventId, long vendorId) {
        if (!validateForExistingContractByEventId(eventId) && !validateForExistingContractByVendorId(vendorId)) {
            throw new ConflictException("There is already such contract in the database!");
        }
    }

    private boolean validateForExistingContractByEventId(long eventId) {
        return contractService.getContractsByAssociatedEventId(eventId).isEmpty();
    }

    private boolean validateForExistingContractByVendorId(long vendorId) {
        return contractService.getContractsByAssociatedVendorId(vendorId).isEmpty();
    }
}