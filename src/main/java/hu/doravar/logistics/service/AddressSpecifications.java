package hu.doravar.logistics.service;

import org.springframework.data.jpa.domain.Specification;

import hu.doravar.logistics.model.Address;
import hu.doravar.logistics.model.Address_;

public class AddressSpecifications {

	public static Specification<Address> hasIsoCode(String isoCode) {
		return (root, cq, cb) -> cb.like(root.get(Address_.isoCode), isoCode);
	}

	public static Specification<Address> hasZipCode(String zipCode) {
		return (root, cq, cb) -> cb.like(root.get(Address_.zipCode), zipCode);
	}

	public static Specification<Address> hasCity(String city) {
		return (root, cq, cb) -> cb.like(cb.upper(root.get(Address_.city)), city.toUpperCase() + "%");
	}

	public static Specification<Address> hasStreet(String street) {
		return (root, cq, cb) -> cb.like(cb.upper(root.get(Address_.street)), street.toUpperCase() + "%");
	}

}
