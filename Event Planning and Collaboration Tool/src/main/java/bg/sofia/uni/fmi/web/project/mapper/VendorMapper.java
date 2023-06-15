package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.VendorDto;
import bg.sofia.uni.fmi.web.project.model.Vendor;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", uses = {ReviewMapper.class})
public interface VendorMapper {
    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    @Mapping(source = "vendorReviews", target = "vendorReviewsDto")
    VendorDto toDto(Vendor vendorEntity);

    @Mapping(source = "vendorReviewsDto", target = "vendorReviews")
    Vendor toEntity(VendorDto vendorDto);

    default List<VendorDto> toDtoCollection(Collection<Vendor> vendorEntities) {
        if (vendorEntities == null) {
            return Collections.emptyList();
        }

        return vendorEntities.stream()
            .map(this::toDto)
            .toList();
    }

    default List<Vendor> toEntityCollection(Collection<VendorDto> vendorDtos) {
        if (vendorDtos == null) {
            return Collections.emptyList();
        }

        return vendorDtos.stream()
            .map(this::toEntity)
            .toList();
    }
}