package hu.doravar.logistics.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import hu.doravar.logistics.model.Address;
import hu.doravar.logistics.repository.AddressRepository;

@Service
public class AddressService {

	@Autowired
	AddressRepository addressRepository;

	@Transactional
	public Address save(Address address) {
		return addressRepository.save(address);
	}

	@Transactional
	public Address update(Address address) {
		if (!addressRepository.existsById(address.getId()))
			return null;
		else
			return addressRepository.save(address);
	}

	public List<Address> findAll() {
		return addressRepository.findAll();
	}

	public Optional<Address> findById(long id) {
		return addressRepository.findById(id);
	}

	public void deleteById(long id) {
		addressRepository.deleteById(id);
	}

	public Page<Address> findAddressByExample(Address example, Pageable pageable) {
		String isoCode = example.getIsoCode();
		String city = example.getCity();
		String street = example.getStreet();
		String zipCode = example.getZipCode();
		Specification<Address> spec = Specification.where(null);
		if (StringUtils.hasText(isoCode))
			spec = spec.and(AddressSpecifications.hasIsoCode(isoCode));
		if (StringUtils.hasText(zipCode))
			spec = spec.and(AddressSpecifications.hasZipCode(zipCode));
		if (StringUtils.hasText(city))
			spec = spec.and(AddressSpecifications.hasCity(city));
		if (StringUtils.hasText(street))
			spec = spec.and(AddressSpecifications.hasStreet(street));

		return addressRepository.findAll(spec, pageable);
	}
}
