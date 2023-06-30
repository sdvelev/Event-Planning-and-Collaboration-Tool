package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.ReviewDto;
import bg.sofia.uni.fmi.web.project.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    ReviewDto toDto(Review reviewEntity);

    Review toEntity(ReviewDto reviewDto);

    List<ReviewDto> toDtoCollection(Collection<Review> reviewsEntities);

    List<Review> toEntityCollection(Collection<ReviewDto> reviewDtos);
}