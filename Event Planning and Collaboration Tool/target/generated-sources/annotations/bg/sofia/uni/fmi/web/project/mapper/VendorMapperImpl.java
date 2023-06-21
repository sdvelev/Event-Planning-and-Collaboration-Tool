package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.ReviewDto;
import bg.sofia.uni.fmi.web.project.dto.VendorDto;
import bg.sofia.uni.fmi.web.project.model.Review;
import bg.sofia.uni.fmi.web.project.model.Vendor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-21T08:54:36+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19 (Oracle Corporation)"
)
@Component
public class VendorMapperImpl implements VendorMapper {

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public VendorDto toDto(Vendor vendorEntity) {
        if ( vendorEntity == null ) {
            return null;
        }

        VendorDto.VendorDtoBuilder vendorDto = VendorDto.builder();

        vendorDto.vendorReviewsDto( reviewSetToReviewDtoSet( vendorEntity.getVendorReviews() ) );
        vendorDto.id( vendorEntity.getId() );
        vendorDto.name( vendorEntity.getName() );
        vendorDto.surname( vendorEntity.getSurname() );
        vendorDto.address( vendorEntity.getAddress() );
        vendorDto.phoneNumber( vendorEntity.getPhoneNumber() );
        vendorDto.email( vendorEntity.getEmail() );
        vendorDto.vendorType( vendorEntity.getVendorType() );
        vendorDto.createdBy( vendorEntity.getCreatedBy() );
        vendorDto.creationTime( vendorEntity.getCreationTime() );
        vendorDto.updatedBy( vendorEntity.getUpdatedBy() );
        vendorDto.lastUpdatedTime( vendorEntity.getLastUpdatedTime() );
        vendorDto.deleted( vendorEntity.isDeleted() );

        return vendorDto.build();
    }

    @Override
    public Vendor toEntity(VendorDto vendorDto) {
        if ( vendorDto == null ) {
            return null;
        }

        Vendor.VendorBuilder vendor = Vendor.builder();

        vendor.vendorReviews( reviewDtoSetToReviewSet( vendorDto.getVendorReviewsDto() ) );
        vendor.id( vendorDto.getId() );
        vendor.name( vendorDto.getName() );
        vendor.surname( vendorDto.getSurname() );
        vendor.address( vendorDto.getAddress() );
        vendor.phoneNumber( vendorDto.getPhoneNumber() );
        vendor.email( vendorDto.getEmail() );
        vendor.vendorType( vendorDto.getVendorType() );
        vendor.createdBy( vendorDto.getCreatedBy() );
        vendor.creationTime( vendorDto.getCreationTime() );
        vendor.updatedBy( vendorDto.getUpdatedBy() );
        vendor.lastUpdatedTime( vendorDto.getLastUpdatedTime() );
        vendor.deleted( vendorDto.isDeleted() );

        return vendor.build();
    }

    @Override
    public List<VendorDto> toDtoCollection(Collection<Vendor> vendorEntities) {
        if ( vendorEntities == null ) {
            return null;
        }

        List<VendorDto> list = new ArrayList<VendorDto>( vendorEntities.size() );
        for ( Vendor vendor : vendorEntities ) {
            list.add( toDto( vendor ) );
        }

        return list;
    }

    @Override
    public List<Vendor> toEntityCollection(Collection<VendorDto> vendorDtos) {
        if ( vendorDtos == null ) {
            return null;
        }

        List<Vendor> list = new ArrayList<Vendor>( vendorDtos.size() );
        for ( VendorDto vendorDto : vendorDtos ) {
            list.add( toEntity( vendorDto ) );
        }

        return list;
    }

    protected Set<ReviewDto> reviewSetToReviewDtoSet(Set<Review> set) {
        if ( set == null ) {
            return null;
        }

        Set<ReviewDto> set1 = new LinkedHashSet<ReviewDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Review review : set ) {
            set1.add( reviewMapper.toDto( review ) );
        }

        return set1;
    }

    protected Set<Review> reviewDtoSetToReviewSet(Set<ReviewDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Review> set1 = new LinkedHashSet<Review>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( ReviewDto reviewDto : set ) {
            set1.add( reviewMapper.toEntity( reviewDto ) );
        }

        return set1;
    }
}
