package hu.doravar.logistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.doravar.logistics.model.Section;

public interface SectionRepository extends JpaRepository<Section, Long> {

}
