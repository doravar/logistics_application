package hu.doravar.logistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.doravar.logistics.model.LogisticsUser;

public interface UserRepository extends JpaRepository<LogisticsUser, String> {
	
}
