package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.model.Review;
import bg.sofia.uni.fmi.web.project.model.Vendor;
import bg.sofia.uni.fmi.web.project.repository.ReviewRepository;
import bg.sofia.uni.fmi.web.project.validation.MethodNotAllowed;
import bg.sofia.uni.fmi.web.project.validation.ResourceNotFoundException;
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
        List<Review> reviews = reviewRepository.findAll().parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();

        validateReviewsList(reviews);

        return reviews;
    }

    public Review getReviewById(@Positive(message = "The given id cannot be 0 or less!") long id) {
        Review review = reviewRepository.findReviewByIdEquals(id);
        validateReview(review);

        if (!review.isDeleted()) {
            return review;
        }

        throw new MethodNotAllowed("The current record has already been deleted!");
    }

    public List<Review> getReviewsByComment(@NotNull(message = "The comment cannot be null!")
                                     @NotEmpty(message = "The comment cannot be empty!")
                                     @NotBlank(message = "The comment cannot be blank!")
                                     String comment) {

        List<Review> reviews = reviewRepository.findReviewByCommentEquals(comment).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();

        validateReviewsList(reviews);

        return reviews;
    }

    public List<Review> getReviewsByRating(@NotNull(message = "The given rating cannot be null!")
                                           @Positive(message = "The given rating must be above 0!")
                                           BigDecimal rating) {
        List<Review> reviews = reviewRepository.findReviewsByRatingEquals(rating).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();

        validateReviewsList(reviews);

        return reviews;
    }

    public List<Review> getReviewsByPhotoLink(@NotNull(message = "The photo link cannot be null!")
                                              @NotEmpty(message = "The photo link cannot be empty!")
                                              @NotBlank(message = "The photo link cannot be blank!")
                                              String photoLink) {
        List<Review> reviews = reviewRepository.findReviewsByPhotoLinkEquals(photoLink).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();

        validateReviewsList(reviews);

        return reviews;
    }

    public List<Review> getReviewsByAssociatedVendorId(@Positive(message = "The given id must be above 0!")
                                                       long vendorId) {
        List<Review> reviews = reviewRepository.findReviewsByAssociatedVendorIdEquals(vendorId).parallelStream()
            .filter(g -> !g.isDeleted())
            .toList();

        validateReviewsList(reviews);

        return reviews;
    }

    public boolean delete(boolean deleted,
                          @Positive(message = "The given ID cannot be less than zero!") long reviewId) {
        Review review = reviewRepository.findReviewByIdEquals(reviewId);
        validateReview(review);

        if (!review.isDeleted()) {
            review.setDeleted(deleted);
            reviewRepository.save(review);
            return true;
        }

        throw new MethodNotAllowed("The current record has already been deleted!");
    }

    private void validateReview(Review review) {
        if (review == null) {
            throw new ResourceNotFoundException("There is no such review in the database!");
        }
    }

    private void validateReviewsList(List<Review> reviews) {
        if (reviews == null) {
            throw new ResourceNotFoundException("There are no such reviews in the database or have been deleted!");
        }
    }
}