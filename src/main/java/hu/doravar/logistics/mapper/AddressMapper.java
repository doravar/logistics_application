package hu.doravar.logistics.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.doravar.logistics.dto.AddressDto;
import hu.doravar.logistics.dto.AddressExampleDto;
import hu.doravar.logistics.model.Address;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

	AddressDto addressToDto(Address address);
	
	Address dtoToAddress(AddressDto AddressDto);
	
	List<AddressDto> addressesToDtos(List<Address> addresses);
	
	
	List<Address> addressToDtos(List<AddressDto> addressDtos);
	
	@Mapping(target = "houseNumber", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "latitude", ignore = true)
	@Mapping(target = "longitude", ignore = true)
	Address dtoToAddress(AddressExampleDto addressExampleDto);
}
