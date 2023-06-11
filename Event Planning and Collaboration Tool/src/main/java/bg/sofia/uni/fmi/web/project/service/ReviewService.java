package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.model.Review;
import bg.sofia.uni.fmi.web.project.model.Vendor;
import bg.sofia.uni.fmi.web.project.repository.ReviewRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final VendorService vendorService;

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
        return reviewRepository.save(reviewToSave).getId();
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll().parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();
    }

    public Review getReviewById(@Positive(message = "The given id cannot be 0 or less!") long id) {
        Review review = reviewRepository.findReviewByIdEquals(id);

        if (review != null && !review.isDeleted()) {
            return review;
        }

        return null;
    }

    public Review getReviewByComment(@NotNull(message = "The comment cannot be null!")
                                     @NotEmpty(message = "The comment cannot be empty!")
                                     @NotBlank(message = "The comment cannot be blank!")
                                     String comment) {
        Review review = reviewRepository.findReviewByCommentEquals(comment);

        if (review != null && !review.isDeleted()) {
            return review;
        }

        return null;
    }

    public List<Review> getReviewsByRating(@NotNull(message = "The given rating cannot be null!")
                                           @Positive(message = "The given rating must be above 0!")
                                           BigDecimal rating) {
        return reviewRepository.findReviewsByRatingEquals(rating).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();
    }

    public List<Review> getReviewsByPhotoLink(@NotNull(message = "The photo link cannot be null!")
                                              @NotEmpty(message = "The photo link cannot be empty!")
                                              @NotBlank(message = "The photo link cannot be blank!")
                                              String photoLink) {
        return reviewRepository.findReviewsByPhotoLinkEquals(photoLink).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();
    }

    public List<Review> getReviewsByAssociatedVendorId(@Positive(message = "The given id must be above 0!")
                                                       long vendorId) {
        return reviewRepository.findReviewsByAssociatedVendorIdEquals(vendorId).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();
    }

    public boolean delete(boolean deleted,
                          @Positive(message = "The given ID cannot be less than zero!") long reviewId) {
        Review review = reviewRepository.findReviewByIdEquals(reviewId);

        if (review != null && !review.isDeleted()) {
            review.setDeleted(deleted);
            reviewRepository.save(review);
            return true;
        }

        return false;
    }
}