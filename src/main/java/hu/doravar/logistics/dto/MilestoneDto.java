package hu.doravar.logistics.dto;

import java.time.LocalDateTime;

public class MilestoneDto {

	private long id;

	private AddressDto address;
	private LocalDateTime plannedTime;

	public MilestoneDto() {
	}

	public MilestoneDto(AddressDto address, LocalDateTime plannedTime) {
		this.address = address;
		this.plannedTime = plannedTime;
	}

	public MilestoneDto(AddressDto address) {
		this.address = address;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AddressDto getAddress() {
		return address;
	}

	public void setAddress(AddressDto address) {
		this.address = address;
	}

	public LocalDateTime getPlannedTime() {
		return plannedTime;
	}

	public void setPlannedTime(LocalDateTime plannedTime) {
		this.plannedTime = plannedTime;
	}

}
