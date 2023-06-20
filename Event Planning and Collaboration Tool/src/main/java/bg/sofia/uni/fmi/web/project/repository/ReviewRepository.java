package bg.sofia.uni.fmi.web.project.repository;

import bg.sofia.uni.fmi.web.project.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM reviews r WHERE r.deleted = false AND r.id = ?1")
    Review findReviewByIdEquals(long id);

    @Override
    @Query("SELECT r FROM reviews r WHERE r.deleted = false ORDER BY r.creationTime desc")
    List<Review> findAll();

    @Query("SELECT r FROM reviews r WHERE r.deleted = false AND r.rating = ?1")
    List<Review> findReviewsByRatingEquals(BigDecimal rating);

    @Query("SELECT r FROM reviews r WHERE r.deleted = false AND r.photoLink = ?1")
    List<Review> findReviewsByAndPhotoLinkEquals(String photoLink);

    @Query("SELECT r FROM reviews r WHERE r.deleted = false AND r.associatedVendor.id = ?1")
    List<Review> findReviewsByAssociatedVendorIdEquals(long id);
}