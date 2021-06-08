package hu.doravar.logistics.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Milestone {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	private Address address;
	private LocalDateTime plannedTime;

	@OneToOne
	private Section section;

	public Milestone() {
	}

	public Milestone(Address address, LocalDateTime plannedTime) {
		this.address = address;
		this.plannedTime = plannedTime;
	}

	public Milestone(Address address) {
		this.address = address;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public LocalDateTime getPlannedTime() {
		return plannedTime;
	}

	public void setPlannedTime(LocalDateTime plannedTime) {
		this.plannedTime = plannedTime;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public void addDelay(long minutesToAdd) {
		plannedTime = plannedTime.plus(minutesToAdd, ChronoUnit.MINUTES);
	}

}
