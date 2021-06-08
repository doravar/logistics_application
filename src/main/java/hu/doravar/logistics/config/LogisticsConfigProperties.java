package hu.doravar.logistics.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "logistics")
@Component
public class LogisticsConfigProperties {

	private RevenueDecrease revenueDecrease = new RevenueDecrease();

	public RevenueDecrease getRevenueDecrease() {
		return revenueDecrease;
	}

	public void setRevenueDecrease(RevenueDecrease revenueDecrease) {
		this.revenueDecrease = revenueDecrease;
	}

	public static class RevenueDecrease {
		private Map<Integer, Integer> decreasingIntervals;

		public Map<Integer, Integer> getDecreasingIntervals() {
			return decreasingIntervals;
		}

		public void setDecreasingIntervals(Map<Integer, Integer> decreasingIntervals) {
			this.decreasingIntervals = decreasingIntervals;
		}

	}
}
