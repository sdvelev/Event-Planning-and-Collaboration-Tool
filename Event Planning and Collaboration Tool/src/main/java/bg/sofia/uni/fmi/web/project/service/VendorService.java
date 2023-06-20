package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.dto.VendorDto;
import bg.sofia.uni.fmi.web.project.enums.VendorType;
import bg.sofia.uni.fmi.web.project.model.Vendor;
import bg.sofia.uni.fmi.web.project.repository.VendorRepository;
import bg.sofia.uni.fmi.web.project.validation.ConflictException;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
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
                          Vendor vendorToSave) {
        validateForExistingVendor(vendorToSave.getName(), vendorToSave.getSurname(), vendorToSave.getEmail());

        vendorToSave.setCreatedBy("a");
        vendorToSave.setCreationTime(LocalDateTime.now());

        return vendorRepository.save(vendorToSave).getId();
    }

    public List<Vendor> getAllVendors() {
        List<Vendor> vendors = vendorRepository.findAll();
        validateVendorsList(vendors);

        return vendors;
    }

    public Vendor getVendorById(@Positive(message = "The given id cannot be 0 or less!")
                                long id) {
        Vendor vendor = vendorRepository.findVendorByIdEquals(id);
        validateVendor(vendor);

        return vendor;
    }

    public Vendor getVendorByPhoneNumber(@NotNull(message = "The given phone number cannot be null!")
                                         @NotEmpty(message = "The given phone number cannot be empty!")
                                         @NotBlank(message = "The given phone number cannot be blank!")
                                         String phoneNumber) {
        Vendor vendor = vendorRepository.findVendorByPhoneNumberEquals(phoneNumber);
        validateVendor(vendor);

        return vendor;
    }

    public Vendor getVendorByEmail(@NotNull(message = "The given email cannot be null!")
                                   @NotEmpty(message = "The given email cannot be empty!")
                                   @NotBlank(message = "The given email cannot be blank!")
                                   String email) {
        Vendor vendor = vendorRepository.findVendorByEmailEquals(email);
        validateVendor(vendor);

        return vendor;
    }

    public List<Vendor> getVendorsByNameAndSurname(@NotNull(message = "The given name cannot be null!")
                                                   @NotEmpty(message = "The given name cannot be empty!")
                                                   @NotBlank(message = "The given name cannot be blank!")
                                                   String name,
                                                   @NotNull(message = "The given surname cannot be null!")
                                                   @NotEmpty(message = "The given surname cannot be empty!")
                                                   @NotBlank(message = "The given surname cannot be blank!")
                                                   String surname) {

        List<Vendor> vendors = vendorRepository.findVendorsByNameAndSurnameEquals(name, surname);
        validateVendorsList(vendors);

        return vendors;
    }

    public List<Vendor> getVendorsByVendorType(@NotNull(message = "The given vendor type cannot be null!")
                                               VendorType vendorType) {
        List<Vendor> vendors = vendorRepository.findVendorsByVendorTypeEquals(vendorType);
        validateVendorsList(vendors);

        return vendors;
    }

    public boolean setVendorByVendorId(@Positive(message = "The vendor id must be positive!")
                                       long vendorId,
                                       @NotNull(message = "The given vendor dto cannot be null!")
                                       VendorDto vendorDto) {
        Vendor vendor = getVendorById(vendorId);
        validateVendor(vendor);

        Vendor newVendorToSave = updateFields(vendorDto, vendor);
        newVendorToSave.setUpdatedBy("b");
        newVendorToSave.setLastUpdatedTime(LocalDateTime.now());

        vendorRepository.save(newVendorToSave);

        return true;
    }

    public boolean delete(@Positive(message = "The given ID cannot be less than zero!")
                          long vendorId) {
        Vendor vendor = getVendorById(vendorId);
        validateVendor(vendor);

        vendor.setDeleted(true);
        vendorRepository.save(vendor);
        return true;
    }

    private Vendor updateFields(VendorDto vendorDto, Vendor newVendorToSave) {
        if (vendorDto.getName() != null && !vendorDto.getName().equals(newVendorToSave.getName())) {
            newVendorToSave.setName(vendorDto.getName());
        }
        if (vendorDto.getSurname() != null && !vendorDto.getSurname().equals(newVendorToSave.getSurname())) {
            newVendorToSave.setSurname(vendorDto.getSurname());
        }
        if (vendorDto.getAddress() != null && !vendorDto.getAddress().equals(newVendorToSave.getAddress())) {
            newVendorToSave.setAddress(vendorDto.getAddress());
        }
        if (vendorDto.getPhoneNumber() != null &&
            !vendorDto.getPhoneNumber().equals(newVendorToSave.getPhoneNumber())) {

            newVendorToSave.setPhoneNumber(vendorDto.getPhoneNumber());
        }
        if (vendorDto.getEmail() != null && !vendorDto.getEmail().equals(newVendorToSave.getEmail())) {
            newVendorToSave.setEmail(vendorDto.getEmail());
        }
        if (vendorDto.getVendorType() != null && !vendorDto.getVendorType().equals(newVendorToSave.getVendorType())) {
            newVendorToSave.setVendorType(vendorDto.getVendorType());
        }

        return newVendorToSave;
    }

    private void validateVendor(Vendor vendor) {
        if (vendor == null) {
            throw new ResourceNotFoundException("There is no such vendor in the database!");
        }
    }

    private void validateVendorsList(List<Vendor> vendors) {
        if (vendors == null) {
            throw new ResourceNotFoundException("There are no such vendors in the database or have been deleted!");
        }
    }

    private void validateForExistingVendor(String name, String surname, String email) {
        if (!validateForExistingVendorByNameAndSurname(name, surname) && !validateForExistingVendorByEmail(email)) {
            throw new ConflictException("There is already such vendor in the database!");
        }
    }

    private boolean validateForExistingVendorByNameAndSurname(String name, String surname) {
        return getVendorsByNameAndSurname(name, surname).isEmpty();
    }

    private boolean validateForExistingVendorByEmail(String email) {
        return getVendorByEmail(email) == null;
    }
}