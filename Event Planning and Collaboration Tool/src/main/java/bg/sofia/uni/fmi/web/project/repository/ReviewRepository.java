package bg.sofia.uni.fmi.web.project.repository;

import bg.sofia.uni.fmi.web.project.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findReviewByIdEquals(long id);

    Review findReviewByCommentEquals(String comment);

    List<Review> findAll();

    List<Review> findReviewsByRatingEquals(BigDecimal rating);

    List<Review> findReviewsByPhotoLinkEquals(String photoLink);

    List<Review> findReviewsByAssociatedVendorIdEquals(long id);
}