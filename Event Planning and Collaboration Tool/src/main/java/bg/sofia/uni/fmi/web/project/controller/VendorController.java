package bg.sofia.uni.fmi.web.project.controller;

import bg.sofia.uni.fmi.web.project.dto.VendorDto;
import bg.sofia.uni.fmi.web.project.enums.VendorType;
import bg.sofia.uni.fmi.web.project.mapper.VendorMapper;
import bg.sofia.uni.fmi.web.project.service.VendorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vendors")
@Validated
@AllArgsConstructor
public class VendorController {
    private final VendorService vendorService;
    private final VendorMapper mapper;

    @PostMapping(params = {"vendor_type"})
    public long addGuest(@Valid @NotNull(message = "The guestDto cannot be null!") @RequestBody VendorDto vendorDto,
                         @Valid
                         @RequestParam("vendor_type")
                         @NotNull(message = "The vendor type cannot be null!")
                         @NotEmpty(message = "The vendor type cannot be empty!")
                         @NotBlank(message = "The vendor type cannot be blank!")
                         String vendorType) {
        System.out.println(vendorDto.getSurname());
        return vendorService.addVendor(mapper.toEntity(vendorDto), vendorType);
    }

    @GetMapping
    public List<VendorDto> getAllVendors() {
        return mapper.toDtoCollection(vendorService.getAllVendors());
    }

    @GetMapping(value = "/search", params = {"id"})
    public ResponseEntity<VendorDto> findById(@Valid
                                              @Positive(message = "VendorID must be positive")
                                              @RequestParam("id") long id) {
        return ResponseEntity.ok(mapper.toDto(vendorService.getVendorById(id)));
    }

    @GetMapping(value = "/search", params = {"email"})
    public ResponseEntity<VendorDto> findByEmail(@Valid
                                                 @RequestParam("email")
                                                 @NotNull(message = "The given email cannot be null!")
                                                 @NotEmpty(message = "The given email cannot be empty!")
                                                 @NotBlank(message = "The given email cannot be blank!")
                                                 String email) {
        return ResponseEntity.ok(mapper.toDto(vendorService.getVendorByEmail(email)));
    }

    @GetMapping(value = "/search", params = {"phone_number"})
    public ResponseEntity<VendorDto> findByPhoneNumber(@Valid
                                                       @RequestParam("phone_number")
                                                       @NotNull(message = "The given phone number cannot be null!")
                                                       @NotEmpty(message = "The given phone number cannot be empty!")
                                                       @NotBlank(message = "The given phone number cannot be blank!")
                                                       String phoneNumber) {
        return ResponseEntity.ok(mapper.toDto(vendorService.getVendorByPhoneNumber(phoneNumber)));
    }

    @GetMapping(value = "/search", params = {"vendor_type"})
    public ResponseEntity<List<VendorDto>> findByVendorType(@Valid
                                                            @RequestParam("vendor_type")
                                                            @NotNull(message = "The given phone number cannot be null!")
                                                            @NotEmpty(message = "The given phone number cannot be empty!")
                                                            @NotBlank(message = "The given phone number cannot be blank!")
                                                            String vendorType) {
        return ResponseEntity.ok(
            mapper.toDtoCollection(vendorService.getVendorsByVendorType(VendorType.valueOf(vendorType.toUpperCase()))));
    }

    @GetMapping(value = "/search", params = {"name", "surname"})
    public ResponseEntity<List<VendorDto>> findByNameAndSurname(@Valid
                                                                @RequestParam("name")
                                                                @NotNull(message = "The given name cannot be null!")
                                                                @NotEmpty(message = "The given name cannot be empty!")
                                                                @NotBlank(message = "The given name cannot be blank!")
                                                                String name,
                                                                @Valid
                                                                @RequestParam("surname")
                                                                @NotNull(message = "The given surname cannot be null!")
                                                                @NotEmpty(message = "The given surname cannot be empty!")
                                                                @NotBlank(message = "The given surname cannot be blank!")
                                                                String surname) {
        return ResponseEntity.ok(mapper.toDtoCollection(vendorService.getVendorsByNameAndSurname(name, surname)));
    }

    @PutMapping(value = "/set", params = {"vendor_id"})
    public boolean setVendorByVendorId(@Valid
                                       @RequestParam("vendor_id")
                                       @Positive(message = "The vendor id must be positive!")
                                       long vendorId,
                                       @RequestBody
                                       @NotNull(message = "The given vendor dto cannot be null!")
                                       VendorDto vendorDto) {
        return vendorService.setVendorByVendorId(vendorId, vendorDto);
    }

    @DeleteMapping(value = "/delete", params = {"deleted", "id"})
    public boolean deleteGuest(@RequestParam("deleted")
                               boolean deleted,
                               @Positive(message = "The given ID cannot be less than zero!")
                               @RequestParam("id")
                               long vendorId) {
        return vendorService.delete(deleted, vendorId);
    }
}