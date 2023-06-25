package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.ReviewDto;
import bg.sofia.uni.fmi.web.project.model.Review;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-25T17:20:39+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19 (Oracle Corporation)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public ReviewDto toDto(Review reviewEntity) {
        if ( reviewEntity == null ) {
            return null;
        }

        ReviewDto.ReviewDtoBuilder reviewDto = ReviewDto.builder();

        reviewDto.id( reviewEntity.getId() );
        reviewDto.rating( reviewEntity.getRating() );
        reviewDto.comment( reviewEntity.getComment() );
        reviewDto.photoLink( reviewEntity.getPhotoLink() );
        reviewDto.createdBy( reviewEntity.getCreatedBy() );
        reviewDto.creationTime( reviewEntity.getCreationTime() );
        reviewDto.updatedBy( reviewEntity.getUpdatedBy() );
        reviewDto.lastUpdatedTime( reviewEntity.getLastUpdatedTime() );
        reviewDto.deleted( reviewEntity.isDeleted() );

        return reviewDto.build();
    }

    @Override
    public Review toEntity(ReviewDto reviewDto) {
        if ( reviewDto == null ) {
            return null;
        }

        Review.ReviewBuilder review = Review.builder();

        review.id( reviewDto.getId() );
        review.rating( reviewDto.getRating() );
        review.comment( reviewDto.getComment() );
        review.photoLink( reviewDto.getPhotoLink() );
        review.createdBy( reviewDto.getCreatedBy() );
        review.creationTime( reviewDto.getCreationTime() );
        review.updatedBy( reviewDto.getUpdatedBy() );
        review.lastUpdatedTime( reviewDto.getLastUpdatedTime() );
        review.deleted( reviewDto.isDeleted() );

        return review.build();
    }
}
