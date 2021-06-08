package hu.doravar.logistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.doravar.logistics.config.LogisticsConfigProperties;
import hu.doravar.logistics.dto.DelayDto;
import hu.doravar.logistics.model.Milestone;
import hu.doravar.logistics.model.Section;
import hu.doravar.logistics.model.TransportPlan;
import hu.doravar.logistics.repository.MilestoneRepository;
import hu.doravar.logistics.repository.TransportPlanRepository;

@Service
public class TransportPlanService {

	@Autowired
	TransportPlanRepository transportPlanRepository;

	@Autowired
	MilestoneRepository milestoneRepository;

	@Autowired
	LogisticsConfigProperties logisticsConfigProperties;

	@Transactional
	public void addDelay(long transportPlanId, DelayDto delay) {
		TransportPlan transportPlan = transportPlanRepository.findById(transportPlanId).get();
		Milestone milestone = milestoneRepository.findById(delay.getMilestoneId()).get();
		Section section = milestone.getSection();
		long durationOfDelay = delay.getDurationOfDelay();
		if (transportPlan.getId() != section.getTransportPlan().getId())
			throw new IllegalArgumentException();
		if (section.getFromMilestone().equals(milestone)) {
			section.getToMilestone().addDelay(durationOfDelay);
		} else if (transportPlan.getSections().size() - 1 > section.getNumber()) { // ha második milestone, csekkoljuk
																					// hogy nem az utolsó section-ban
																					// van
			section.getTransportPlan().getSections().get(section.getNumber() + 1).getFromMilestone()
					.addDelay(durationOfDelay);
		}
		milestone.addDelay(delay.getDurationOfDelay());
		transportPlan
				.setExpectedRevenue(calculateDecreasedRevenue(transportPlan.getExpectedRevenue(), durationOfDelay));

	}

	private int calculateDecreasedRevenue(int revenue, long delay) {
		return revenue - (revenue * logisticsConfigProperties.getRevenueDecrease().getDecreasingIntervals()
				.get(logisticsConfigProperties.getRevenueDecrease().getDecreasingIntervals().keySet().stream()
						.filter(k -> delay >= k).max(Integer::compare).get())
				/ 100);
	}

}
