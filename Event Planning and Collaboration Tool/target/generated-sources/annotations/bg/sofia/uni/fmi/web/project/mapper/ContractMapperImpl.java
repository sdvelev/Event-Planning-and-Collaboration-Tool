package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.ContractDto;
import bg.sofia.uni.fmi.web.project.model.Contract;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-30T12:22:33+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class ContractMapperImpl implements ContractMapper {

    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private VendorMapper vendorMapper;

    @Override
    public ContractDto toDto(Contract contractEntity) {
        if ( contractEntity == null ) {
            return null;
        }

        ContractDto.ContractDtoBuilder contractDto = ContractDto.builder();

        contractDto.associatedEventDto( eventMapper.toDto( contractEntity.getAssociatedEvent() ) );
        contractDto.associatedVendorDto( vendorMapper.toDto( contractEntity.getAssociatedVendor() ) );
        contractDto.id( contractEntity.getId() );
        contractDto.totalPrice( contractEntity.getTotalPrice() );
        contractDto.finished( contractEntity.isFinished() );
        contractDto.createdBy( contractEntity.getCreatedBy() );
        contractDto.creationTime( contractEntity.getCreationTime() );
        contractDto.updatedBy( contractEntity.getUpdatedBy() );
        contractDto.lastUpdatedTime( contractEntity.getLastUpdatedTime() );
        contractDto.deleted( contractEntity.isDeleted() );

        return contractDto.build();
    }

    @Override
    public Contract toEntity(ContractDto contractDto) {
        if ( contractDto == null ) {
            return null;
        }

        Contract.ContractBuilder contract = Contract.builder();

        contract.associatedEvent( eventMapper.toEntity( contractDto.getAssociatedEventDto() ) );
        contract.associatedVendor( vendorMapper.toEntity( contractDto.getAssociatedVendorDto() ) );
        contract.id( contractDto.getId() );
        contract.totalPrice( contractDto.getTotalPrice() );
        contract.finished( contractDto.isFinished() );
        contract.createdBy( contractDto.getCreatedBy() );
        contract.creationTime( contractDto.getCreationTime() );
        contract.updatedBy( contractDto.getUpdatedBy() );
        contract.lastUpdatedTime( contractDto.getLastUpdatedTime() );
        contract.deleted( contractDto.isDeleted() );

        return contract.build();
    }

    @Override
    public List<ContractDto> toDtoCollection(Collection<Contract> contractEntities) {
        if ( contractEntities == null ) {
            return null;
        }

        List<ContractDto> list = new ArrayList<ContractDto>( contractEntities.size() );
        for ( Contract contract : contractEntities ) {
            list.add( toDto( contract ) );
        }

        return list;
    }

    @Override
    public List<Contract> toEntityCollection(Collection<ContractDto> contractDtos) {
        if ( contractDtos == null ) {
            return null;
        }

        List<Contract> list = new ArrayList<Contract>( contractDtos.size() );
        for ( ContractDto contractDto : contractDtos ) {
            list.add( toEntity( contractDto ) );
        }

        return list;
    }
}
