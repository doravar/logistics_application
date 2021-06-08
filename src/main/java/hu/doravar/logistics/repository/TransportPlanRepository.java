package hu.doravar.logistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.doravar.logistics.model.TransportPlan;

public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long> {

}
