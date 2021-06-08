package hu.doravar.logistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import hu.doravar.logistics.model.Address;


public interface AddressRepository extends JpaRepository<Address, Long>, PagingAndSortingRepository<Address, Long>, JpaSpecificationExecutor<Address> {

}
