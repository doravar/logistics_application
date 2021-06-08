package hu.doravar.logistics.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class TransportPlan {

	@Id
	@GeneratedValue
	private long id;

	@OneToMany(mappedBy = "transportPlan")
	private List<Section> sections = new ArrayList<>();

	private int expectedRevenue;

	public TransportPlan() {
	}

	public TransportPlan(List<Section> sections, int expectedRevenue) {
		if (sections != null) {
			for (Section section : sections) {
				addSection(section);
			}
		}
		this.expectedRevenue = expectedRevenue;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public int getExpectedRevenue() {
		return expectedRevenue;
	}

	public void setExpectedRevenue(int expectedRevenue) {
		this.expectedRevenue = expectedRevenue;
	}

	public void addSection(Section section) {
		if (this.sections == null)
			this.sections = new ArrayList<>();
		this.sections.add(section);
		section.setTransportPlan(this);
		section.setNumber(this.sections.size() - 1);
	}

}
