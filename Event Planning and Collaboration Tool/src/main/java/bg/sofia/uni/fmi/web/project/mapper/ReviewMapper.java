package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.ReviewDto;
import bg.sofia.uni.fmi.web.project.model.Review;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class ReviewMapper {
    private VendorMapper vendorMapper;

    public ReviewDto toDto(Review reviewEntity) {
        if (reviewEntity == null) {
            return null;
        }

        ReviewDto newReviewDto = new ReviewDto();
        newReviewDto.setId((reviewEntity.getId()));
        newReviewDto.setRating(reviewEntity.getRating());
        newReviewDto.setComment(reviewEntity.getComment());
        newReviewDto.setPhotoLink(reviewEntity.getPhotoLink());

        if (reviewEntity.getAssociatedVendor() != null) {
            newReviewDto.setAssociatedVendorDto(vendorMapper.toDto(reviewEntity.getAssociatedVendor()));
        }

        return newReviewDto;
    }

    public Review toEntity(ReviewDto reviewDto) {
        if (reviewDto == null) {
            return null;
        }

        Review newReview = new Review();
        newReview.setId((reviewDto.getId()));
        newReview.setRating(reviewDto.getRating());
        newReview.setComment(reviewDto.getComment());
        newReview.setPhotoLink(reviewDto.getPhotoLink());

        if (reviewDto.getAssociatedVendorDto() != null) {
            newReview.setAssociatedVendor(vendorMapper.toEntity(reviewDto.getAssociatedVendorDto()));
        }

        return newReview;
    }

    public List<ReviewDto> toDtoCollection(Collection<Review> reviewsEntities) {
        if (reviewsEntities == null) {
            return Collections.emptyList();
        }

        return reviewsEntities.stream()
            .map(this::toDto)
            .toList();
    }

    public List<Review> toEntityCollection(Collection<ReviewDto> reviewDtos) {
        if (reviewDtos == null) {
            return Collections.emptyList();
        }

        return reviewDtos.stream()
            .map(this::toEntity)
            .toList();
    }
}