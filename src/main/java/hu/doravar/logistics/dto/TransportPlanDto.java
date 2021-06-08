package hu.doravar.logistics.dto;

import java.util.ArrayList;
import java.util.List;

public class TransportPlanDto {

	private long id;

	private List<SectionDto> sections = new ArrayList<>();

	private int expectedRevenue;

	public TransportPlanDto() {
	}

	public TransportPlanDto(List<SectionDto> sections, int expectedRevenue) {
		this.sections = sections;
		this.expectedRevenue = expectedRevenue;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<SectionDto> getSections() {
		return sections;
	}

	public void setSections(List<SectionDto> sections) {
		this.sections = sections;
	}

	public int getExpectedRevenue() {
		return expectedRevenue;
	}

	public void setExpectedRevenue(int expectedRevenue) {
		this.expectedRevenue = expectedRevenue;
	}

}
