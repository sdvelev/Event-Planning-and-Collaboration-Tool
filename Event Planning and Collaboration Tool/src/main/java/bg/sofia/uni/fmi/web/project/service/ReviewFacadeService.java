package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.model.Review;
import bg.sofia.uni.fmi.web.project.model.Vendor;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Service
@Validated
@AllArgsConstructor
public class ReviewFacadeService {
    private final ReviewService reviewService;
    private final VendorService vendorService;

    @Transactional
    public long addReview(@NotNull(message = "The given vendor cannot be null!")
                          Review reviewToSave,
                          @NotNull(message = "The vendor id cannot be null!")
                          @Positive(message = "The vendor id must be above 0!")
                          Long vendorId) {
        Vendor vendor = vendorService.getVendorById(vendorId);
        validateVendor(vendor);

        reviewToSave.setAssociatedVendor(vendor);

        reviewToSave.setCreatedBy("a");
        reviewToSave.setCreationTime(LocalDateTime.now());

        vendor.getVendorReviews().add(reviewToSave);

        return reviewService.addReview(reviewToSave);
    }

    private void validateVendor(Vendor vendor) {
        if (vendor == null) {
            throw new ResourceNotFoundException("There is no vendor with such id!");
        }
    }
}