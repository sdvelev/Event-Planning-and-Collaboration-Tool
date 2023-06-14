package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.model.Review;
import bg.sofia.uni.fmi.web.project.model.Vendor;
import bg.sofia.uni.fmi.web.project.validation.MethodNotAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VendorReviewFacadeService {
    private final VendorService vendorService;
    private final ReviewService reviewService;

    @Transactional
    public boolean delete(boolean deleted,
                          @Positive(message = "The given ID cannot be less than zero!")
                          long vendorId) {

        removeAllAssociatedReviews(vendorId);
        return vendorService.delete(deleted, vendorId);
    }

    private void removeAllAssociatedReviews(long vendorId) {
        List<Review> reviewList = reviewService.getReviewsByAssociatedVendorId(vendorId);
        for (Review review : reviewList) {
            if (!review.isDeleted()) {
                reviewService.delete(true, review.getId());
            }
        }
    }
}