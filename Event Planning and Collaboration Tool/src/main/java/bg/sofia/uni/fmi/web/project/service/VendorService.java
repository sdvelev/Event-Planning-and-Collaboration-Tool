package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.enums.VendorType;
import bg.sofia.uni.fmi.web.project.model.Vendor;
import bg.sofia.uni.fmi.web.project.repository.VendorRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Validated
@AllArgsConstructor
public class VendorService {
    private final VendorRepository vendorRepository;

    public long addVendor(@NotNull(message = "The given vendor cannot be null!")
                          Vendor vendorToSave,
                          @NotNull(message = "The vendor type cannot be null!")
                          @NotEmpty(message = "The vendor type cannot be empty!")
                          @NotBlank(message = "The vendor type cannot be blank!")
                          String vendorType) {
//        if (!validateForExistingGuestByNameAndSurname(guestToSave) || !validateForExistingGuestByEventId(guestToSave)) {
//            throw new ApiBadRequest("There is already a guest with the same credentials");
//        }

        VendorType newVendorType = VendorType.valueOf(vendorType.toUpperCase());

//        if (event == null) {
//            throw new ApiBadRequest("There is no such event with this ID!");
//        }

        vendorToSave.setVendorType(newVendorType);
        vendorToSave.setCreatedBy("a");
        vendorToSave.setCreationTime(LocalDateTime.now());
        vendorToSave.setDeleted(false);
        return vendorRepository.save(vendorToSave).getId();
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll().parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();
    }

    public Vendor getVendorById(@Positive(message = "The given id cannot be 0 or less!") long id) {
        Vendor vendor = vendorRepository.findVendorByIdEquals(id);

        if (vendor != null && !vendor.isDeleted()) {
            return vendor;
        }

        return null;
    }

    public Vendor getVendorByPhoneNumber(@NotNull(message = "The given phone number cannot be null!")
                                         @NotEmpty(message = "The given phone number cannot be empty!")
                                         @NotBlank(message = "The given phone number cannot be blank!")
                                         String phoneNumber) {
        Vendor vendor = vendorRepository.findVendorByPhoneNumberEquals(phoneNumber);

        if (vendor != null && !vendor.isDeleted()) {
            return vendor;
        }

        return null;
    }

    public Vendor getVendorByEmail(@NotNull(message = "The given email cannot be null!")
                                   @NotEmpty(message = "The given email cannot be empty!")
                                   @NotBlank(message = "The given email cannot be blank!")
                                   String email) {
        Vendor vendor = vendorRepository.findVendorByEmailEquals(email);

        if (vendor != null && !vendor.isDeleted()) {
            return vendor;
        }

        return null;
    }

    public List<Vendor> getVendorsByVendorType(@NotNull(message = "The given vendor type cannot be null!")
                                               VendorType vendorType) {
        return vendorRepository.findVendorsByVendorTypeEquals(vendorType).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();
    }

    public boolean delete(boolean deleted,
                          @Positive(message = "The given ID cannot be less than zero!") long vendorId) {
        Vendor vendor = vendorRepository.findVendorByIdEquals(vendorId);

        if (vendor != null && !vendor.isDeleted()) {
            vendor.setDeleted(deleted);
            vendorRepository.save(vendor);
            return true;
        }

        return false;
    }
}