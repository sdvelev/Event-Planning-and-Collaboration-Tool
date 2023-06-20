package bg.sofia.uni.fmi.web.project.service;

import bg.sofia.uni.fmi.web.project.dto.ReviewDto;
import bg.sofia.uni.fmi.web.project.model.Review;
import bg.sofia.uni.fmi.web.project.repository.ReviewRepository;
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

    public long addReview(@NotNull(message = "The given vendor cannot be null!")
                          Review reviewToSave) {

        return reviewRepository.save(reviewToSave).getId();
    }

    public List<Review> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        validateReviewsList(reviews);

        return reviews;
    }

    public Review getReviewById(@Positive(message = "The given id cannot be 0 or less!") long id) {
        Review review = reviewRepository.findReviewByIdEquals(id);
        validateReview(review);

        return review;
    }

    public List<Review> getReviewsByRating(@NotNull(message = "The given rating cannot be null!")
                                           @Positive(message = "The given rating must be above 0!")
                                           BigDecimal rating) {
        List<Review> reviews = reviewRepository.findReviewsByRatingEquals(rating);
        validateReviewsList(reviews);

        return reviews;
    }

    public List<Review> getReviewsByPhotoLink(@NotNull(message = "The photo link cannot be null!")
                                              @NotEmpty(message = "The photo link cannot be empty!")
                                              @NotBlank(message = "The photo link cannot be blank!")
                                              String photoLink) {
        List<Review> reviews = reviewRepository.findReviewsByAndPhotoLinkEquals(photoLink);
        validateReviewsList(reviews);

        return reviews;
    }

    public List<Review> getReviewsByAssociatedVendorId(@Positive(message = "The given id must be above 0!")
                                                       long vendorId) {
        List<Review> reviews = reviewRepository.findReviewsByAssociatedVendorIdEquals(vendorId);
        validateReviewsList(reviews);

        return reviews;
    }

    public boolean setReviewByReviewId(@Positive(message = "The review id must be positive!")
                                       long reviewId,
                                       @NotNull(message = "The given review dto cannot be null!")
                                       ReviewDto reviewDto) {
        Review review = getReviewById(reviewId);
        validateReview(review);

        Review newReviewToSave = updateFields(reviewDto, review);
        newReviewToSave.setUpdatedBy("b");
        newReviewToSave.setLastUpdatedTime(LocalDateTime.now());

        reviewRepository.save(newReviewToSave);

        return true;
    }

    public boolean delete(@Positive(message = "The given ID cannot be less than zero!") long reviewId) {
        Review review = getReviewById(reviewId);
        validateReview(review);

        review.setDeleted(true);
        reviewRepository.save(review);
        return true;
    }

    private Review updateFields(ReviewDto reviewDto, Review newReviewToSave) {
        if (reviewDto.getRating() != null && !reviewDto.getRating().equals(newReviewToSave.getRating())) {
            newReviewToSave.setRating(reviewDto.getRating());
        }
        if (reviewDto.getComment() != null && !reviewDto.getComment().equals(newReviewToSave.getComment())) {
            newReviewToSave.setComment(reviewDto.getComment());
        }
        if (reviewDto.getPhotoLink() != null && !reviewDto.getPhotoLink().equals(newReviewToSave.getPhotoLink())) {
            newReviewToSave.setPhotoLink(reviewDto.getPhotoLink());
        }

        return newReviewToSave;
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