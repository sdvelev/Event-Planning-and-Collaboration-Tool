package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.model.Review;
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
import java.util.List;

@Service
@Validated
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public long addReview(@NotNull(message = "The given vendor cannot be null!")
                          Review reviewToSave) {
        Review review = reviewRepository.save(reviewToSave);
        checkForSaveException(review);

        return review.getId();
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
        validateForDeletedReview(review);

        return review;
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
        validateForDeletedReview(review);

        review.setDeleted(deleted);
        reviewRepository.save(review);
        return true;
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

    private void checkForSaveException(Review review) {
        if (review == null) {
            throw new RuntimeException("There was problem while saving the review in the database!");
        }
    }

    private void validateForDeletedReview(Review review) {
        if (review.isDeleted()) {
            throw new MethodNotAllowed("The current record has already been deleted!");
        }
    }
}