package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.ContractDto;
import bg.sofia.uni.fmi.web.project.dto.ReviewDto;
import bg.sofia.uni.fmi.web.project.dto.VendorDto;
import bg.sofia.uni.fmi.web.project.model.Contract;
import bg.sofia.uni.fmi.web.project.model.Review;
import bg.sofia.uni.fmi.web.project.model.Vendor;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class VendorMapper {
    private ReviewMapper reviewMapper;
    private ContractMapper contractMapper;

    public VendorDto toDto(Vendor vendorEntity) {
        if (vendorEntity == null) {
            return null;
        }

        VendorDto newVendorDto = new VendorDto();
        newVendorDto.setId((vendorEntity.getId()));
        newVendorDto.setName(vendorEntity.getName());
        newVendorDto.setAddress(vendorEntity.getAddress());
        newVendorDto.setPhoneNumber(vendorEntity.getPhoneNumber());
        newVendorDto.setEmail(vendorEntity.getEmail());

        if (vendorEntity.getVendorType() != null) {
            newVendorDto.setVendorType(vendorEntity.getVendorType());
        }

        if (vendorEntity.getVendorReviews() != null) {
            Set<ReviewDto> reviewDtoSet = vendorEntity.getVendorReviews().stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toSet());

            newVendorDto.setVendorReviewsDto(reviewDtoSet);
        }

        if (vendorEntity.getVendorContracts() != null) {
            Set<ContractDto> contractDtoSet = vendorEntity.getVendorContracts().stream()
                .map(contractMapper::toDto)
                .collect(Collectors.toSet());

            newVendorDto.setVendorContractsDto(contractDtoSet);
        }

        return newVendorDto;
    }

    public Vendor toEntity(VendorDto vendorDto) {
        if (vendorDto == null) {
            return null;
        }

        Vendor newVendor = new Vendor();
        newVendor.setId((vendorDto.getId()));
        newVendor.setName(vendorDto.getName());
        newVendor.setAddress(vendorDto.getAddress());
        newVendor.setPhoneNumber(vendorDto.getPhoneNumber());
        newVendor.setEmail(vendorDto.getEmail());

        if (vendorDto.getVendorType() != null) {
            newVendor.setVendorType(vendorDto.getVendorType());
        }

        if (vendorDto.getVendorReviewsDto() != null) {
            Set<Review> reviewDtoSet = vendorDto.getVendorReviewsDto().stream()
                .map(reviewMapper::toEntity)
                .collect(Collectors.toSet());

            newVendor.setVendorReviews(reviewDtoSet);
        }

        if (vendorDto.getVendorContractsDto() != null) {
            Set<Contract> contractDtoSet = vendorDto.getVendorContractsDto().stream()
                .map(contractMapper::toEntity)
                .collect(Collectors.toSet());

            newVendor.setVendorContracts(contractDtoSet);
        }

        return newVendor;
    }

    public List<VendorDto> toDtoCollection(Collection<Vendor> vendorEntities) {
        if (vendorEntities == null) {
            return Collections.emptyList();
        }

        return vendorEntities.stream()
            .map(this::toDto)
            .toList();
    }

    public List<Vendor> toEntityCollection(Collection<VendorDto> vendorDtos) {
        if (vendorDtos == null) {
            return Collections.emptyList();
        }

        return vendorDtos.stream()
            .map(this::toEntity)
            .toList();
    }
}