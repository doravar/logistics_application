package hu.doravar.logistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.doravar.logistics.model.Milestone;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

}
