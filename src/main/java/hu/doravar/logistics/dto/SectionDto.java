package hu.doravar.logistics.dto;

public class SectionDto {

	private long id;

	private MilestoneDto fromMilestone;
	private MilestoneDto toMilestone;

	private int number;

	TransportPlanDto transportPlan;

	public SectionDto() {
	}

	public SectionDto(MilestoneDto fromMilestone, MilestoneDto toMilestone, int number) {
		this.fromMilestone = fromMilestone;
		this.toMilestone = toMilestone;
		this.number = number;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MilestoneDto getFromMilestone() {
		return fromMilestone;
	}

	public void setFromMilestone(MilestoneDto fromMilestone) {
		this.fromMilestone = fromMilestone;
	}

	public MilestoneDto getToMilestone() {
		return toMilestone;
	}

	public void setToMilestone(MilestoneDto toMilestone) {
		this.toMilestone = toMilestone;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public TransportPlanDto getTransportPlan() {
		return transportPlan;
	}

	public void setTransportPlan(TransportPlanDto transportPlan) {
		this.transportPlan = transportPlan;
	}

}
