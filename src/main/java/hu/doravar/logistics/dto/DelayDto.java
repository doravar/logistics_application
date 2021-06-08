package hu.doravar.logistics.dto;

public class DelayDto {

	private long milestoneId;
	private long durationOfDelay;

	public DelayDto(long milestoneId, long durationOfDelay) {
		this.milestoneId = milestoneId;
		this.durationOfDelay = durationOfDelay;
	}

	public long getMilestoneId() {
		return milestoneId;
	}

	public void setMilestoneId(long milestoneId) {
		this.milestoneId = milestoneId;
	}

	public long getDurationOfDelay() {
		return durationOfDelay;
	}

	public void setDurationOfDelay(long durationOfDelay) {
		this.durationOfDelay = durationOfDelay;
	}

}
