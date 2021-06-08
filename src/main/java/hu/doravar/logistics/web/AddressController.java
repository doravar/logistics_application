package hu.doravar.logistics.web;

import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.doravar.logistics.dto.AddressDto;
import hu.doravar.logistics.dto.AddressExampleDto;
import hu.doravar.logistics.mapper.AddressMapper;
import hu.doravar.logistics.model.Address;
import hu.doravar.logistics.service.AddressService;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

	@Autowired
	AddressService addressService;

	@Autowired
	AddressMapper addressMapper;

	@PostMapping
	public ResponseEntity<AddressDto> createAddress(@RequestBody @Valid AddressDto addressDto, BindingResult result) {
		if (result.hasErrors() || addressDto.equals(null) || addressDto.getId() != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Address address = addressService.save(addressMapper.dtoToAddress(addressDto));
		return ResponseEntity.ok(addressMapper.addressToDto(address));
	}

	@GetMapping
	public List<AddressDto> getAddresses() {
		return addressMapper.addressesToDtos(addressService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<AddressDto> getById(@PathVariable long id) {
		try {
			return ResponseEntity.ok(addressMapper.addressToDto(addressService.findById(id).get()));
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<AddressDto> modifyAddress(@PathVariable long id, @RequestBody @Valid AddressDto addressDto,
			BindingResult result) {
		if (result.hasErrors() || addressDto.equals(null)
				|| (addressDto.getId() != null && !addressDto.getId().equals(id))) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		addressDto.setId(id);
		Address updatedAddress = addressService.update(addressMapper.dtoToAddress(addressDto));
		if (updatedAddress == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(addressMapper.addressToDto(updatedAddress));
	}

	@DeleteMapping("/{id}")
	public HttpStatus deleteAddress(@PathVariable long id) {
		try {
			addressService.deleteById(id);
		} catch (NoSuchElementException | EmptyResultDataAccessException e) {
			return HttpStatus.OK;
		}
		return HttpStatus.OK;
	}

	@PostMapping("/search")
	public ResponseEntity<List<AddressDto>> findEmployeesByExample(@RequestBody AddressExampleDto addressExampleDto,
			Pageable pageable, HttpServletResponse response) {
		if (addressExampleDto.equals(null)) { // külön dto example-re, notnull megkötések nélkül
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Page<Address> pageOfAddresses = addressService
				.findAddressByExample(addressMapper.dtoToAddress(addressExampleDto), pageable);
		long size = pageOfAddresses.getTotalElements();
		response.addHeader("X-Total Count", String.valueOf(size));
		return ResponseEntity.ok(addressMapper.addressesToDtos(pageOfAddresses.getContent()));
	}
}
