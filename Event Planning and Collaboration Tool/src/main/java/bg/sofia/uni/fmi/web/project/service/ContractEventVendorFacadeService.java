package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.model.Contract;
import bg.sofia.uni.fmi.web.project.model.Event;
import bg.sofia.uni.fmi.web.project.model.Vendor;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
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

        Event event = eventService.getEventById(eventId);
        Vendor vendor = vendorService.getVendorById(vendorId);

        contractToSave.setAssociatedEvent(event);
        contractToSave.setAssociatedVendor(vendor);
        contractToSave.setCreatedBy("a");
        contractToSave.setCreationTime(LocalDateTime.now());
        contractToSave.setDeleted(false);

        //event.setContract()
        vendor.getVendorContracts().add(contractToSave);

        return contractService.addContract(contractToSave);
    }
}
