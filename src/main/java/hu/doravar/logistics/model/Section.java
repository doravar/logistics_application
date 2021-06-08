package hu.doravar.logistics.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Section {

	@Id
	@GeneratedValue
	private long id;

	@OneToOne
	private Milestone fromMilestone;
	@OneToOne
	private Milestone toMilestone;

	private int number; // hányadik szakasz a szállítási tervben

	@ManyToOne
	TransportPlan transportPlan;

	public Section() {
	}

	public Section(Milestone fromMilestone, Milestone toMilestone) {
		setFromMilestone(fromMilestone);
		setToMilestone(toMilestone);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Milestone getFromMilestone() {
		return fromMilestone;
	}

	public void setFromMilestone(Milestone fromMilestone) {
		this.fromMilestone = fromMilestone;
		fromMilestone.setSection(this);
	}

	public Milestone getToMilestone() {
		return toMilestone;
	}

	public void setToMilestone(Milestone toMilestone) {
		this.toMilestone = toMilestone;
		toMilestone.setSection(this);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public TransportPlan getTransportPlan() {
		return transportPlan;
	}

	public void setTransportPlan(TransportPlan transportPlan) {
		this.transportPlan = transportPlan;
	}
	
}
