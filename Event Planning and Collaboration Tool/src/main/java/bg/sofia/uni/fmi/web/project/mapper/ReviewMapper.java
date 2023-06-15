package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.ReviewDto;
import bg.sofia.uni.fmi.web.project.model.Review;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    ReviewDto toDto(Review reviewEntity);

    Review toEntity(ReviewDto reviewDto);

    default List<ReviewDto> toDtoCollection(Collection<Review> reviewsEntities) {
        if (reviewsEntities == null) {
            return Collections.emptyList();
        }

        return reviewsEntities.stream()
            .map(this::toDto)
            .toList();
    }

    default List<Review> toEntityCollection(Collection<ReviewDto> reviewDtos) {
        if (reviewDtos == null) {
            return Collections.emptyList();
        }

        return reviewDtos.stream()
            .map(this::toEntity)
            .toList();
    }
}