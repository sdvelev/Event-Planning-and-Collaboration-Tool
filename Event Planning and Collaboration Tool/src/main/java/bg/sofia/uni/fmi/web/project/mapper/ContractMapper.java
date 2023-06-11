package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.ContractDto;
import bg.sofia.uni.fmi.web.project.model.Contract;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class ContractMapper {
    private EventMapper eventMapper;
    private VendorMapper vendorMapper;

    public ContractDto toDto(Contract contractEntity) {
        if (contractEntity == null) {
            return null;
        }

        ContractDto newContractDto = new ContractDto();
        newContractDto.setId((contractEntity.getId()));
        newContractDto.setTotalPrice(contractEntity.getTotalPrice());
        newContractDto.setFinished(contractEntity.isFinished());

        if (contractEntity.getAssociatedEvent() != null) {
            newContractDto.setAssociatedEventDto(eventMapper.toDto(contractEntity.getAssociatedEvent()));
        }

        if (contractEntity.getAssociatedVendor() != null) {
            newContractDto.setAssociatedVendorDto(vendorMapper.toDto(contractEntity.getAssociatedVendor()));
        }

        return newContractDto;
    }

    public Contract toEntity(ContractDto contractDto) {
        if (contractDto == null) {
            return null;
        }

        Contract newContract = new Contract();
        newContract.setId((contractDto.getId()));
        newContract.setTotalPrice(contractDto.getTotalPrice());
        newContract.setFinished(contractDto.isFinished());

        if (contractDto.getAssociatedEventDto() != null) {
            newContract.setAssociatedEvent(eventMapper.toEntity(contractDto.getAssociatedEventDto()));
        }

        if (contractDto.getAssociatedVendorDto() != null) {
            newContract.setAssociatedVendor(vendorMapper.toEntity(contractDto.getAssociatedVendorDto()));
        }

        return newContract;
    }

    public List<ContractDto> toDtoCollection(Collection<Contract> contractEntities) {
        if (contractEntities == null) {
            return Collections.emptyList();
        }

        return contractEntities.stream()
            .map(this::toDto)
            .toList();
    }

    public List<Contract> toEntityCollection(Collection<ContractDto> contractDtos) {
        if (contractDtos == null) {
            return Collections.emptyList();
        }

        return contractDtos.stream()
            .map(this::toEntity)
            .toList();
    }
}