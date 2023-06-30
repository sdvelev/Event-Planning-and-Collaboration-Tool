package bg.sofia.uni.fmi.web.project.mapper;

import bg.sofia.uni.fmi.web.project.dto.ContractDto;
import bg.sofia.uni.fmi.web.project.model.Contract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", uses = {EventMapper.class, VendorMapper.class})
public interface ContractMapper {

    ContractMapper INSTANCE = Mappers.getMapper(ContractMapper.class);

    @Mapping(source = "associatedEvent", target = "associatedEventDto")
    @Mapping(source = "associatedVendor", target = "associatedVendorDto")
    ContractDto toDto(Contract contractEntity);

    @Mapping(source = "associatedEventDto", target = "associatedEvent")
    @Mapping(source = "associatedVendorDto", target = "associatedVendor")
    Contract toEntity(ContractDto contractDto);

    List<ContractDto> toDtoCollection(Collection<Contract> contractEntities);

    List<Contract> toEntityCollection(Collection<ContractDto> contractDtos);
}