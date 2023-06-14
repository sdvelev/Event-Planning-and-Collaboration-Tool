package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.model.Review;
import bg.sofia.uni.fmi.web.project.model.Vendor;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ReviewVendorFacadeService {
    private final ReviewService reviewService;
    private final VendorService vendorService;

    @Transactional
    public long addReview(@NotNull(message = "The given vendor cannot be null!")
                          Review reviewToSave,
                          @NotNull(message = "The vendor id cannot be null!")
                          @Positive(message = "The vendor id must be above 0!")
                          Long vendorId) {
//        if (!validateForExistingGuestByNameAndSurname(guestToSave) || !validateForExistingGuestByEventId(guestToSave)) {
//            throw new ApiBadRequest("There is already a guest with the same credentials");
//        }

        Vendor vendor = vendorService.getVendorById(vendorId);

//        if (event == null) {
//            throw new ApiBadRequest("There is no such event with this ID!");
//        }

        reviewToSave.setAssociatedVendor(vendor);
        reviewToSave.setCreatedBy("a");
        reviewToSave.setCreationTime(LocalDateTime.now());
        reviewToSave.setDeleted(false);

        vendor.getVendorReviews().add(reviewToSave);

        return reviewService.addReview(reviewToSave);
    }
}