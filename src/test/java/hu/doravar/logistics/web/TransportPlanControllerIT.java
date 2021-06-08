package hu.doravar.logistics.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import hu.doravar.logistics.dto.DelayDto;
import hu.doravar.logistics.model.Address;
import hu.doravar.logistics.model.LogisticsUser;
import hu.doravar.logistics.model.Milestone;
import hu.doravar.logistics.model.Section;
import hu.doravar.logistics.model.TransportPlan;
import hu.doravar.logistics.repository.AddressRepository;
import hu.doravar.logistics.repository.MilestoneRepository;
import hu.doravar.logistics.repository.SectionRepository;
import hu.doravar.logistics.repository.TransportPlanRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class TransportPlanControllerIT {

	private static final String BASE_URI = "/api/transportplans";

	@Autowired
	WebTestClient webTestClient;

	@Autowired
	TransportPlanRepository transportPlanRepository;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	MilestoneRepository milestoneRepository;
	@Autowired
	SectionRepository sectionRepository;

	private String jwtToken;

	@BeforeEach
	public void init() {
		LogisticsUser testUser = new LogisticsUser();
		testUser.setUsername("admin");
		testUser.setPassword("pass");
		jwtToken = webTestClient.post().uri("/api/login").bodyValue(testUser).exchange().expectBody(String.class)
				.returnResult().getResponseBody();
	}

	@Test
	void testAddDelayToFirstMilestoneInSection() throws Exception {
		Address a = addressRepository.save(new Address());
		Milestone m1 = milestoneRepository.save(new Milestone(a, LocalDateTime.of(2021, 01, 01, 8, 00)));
		Milestone m2 = milestoneRepository.save(new Milestone(a, LocalDateTime.of(2021, 01, 01, 9, 00)));
		Milestone m3 = milestoneRepository.save(new Milestone(a, LocalDateTime.of(2021, 01, 01, 10, 00)));
		Milestone m4 = milestoneRepository.save(new Milestone(a, LocalDateTime.of(2021, 01, 01, 11, 00)));
		Section s1 = sectionRepository.save(new Section(m1, m2));
		Section s2 = sectionRepository.save(new Section(m3, m4));

		List<Section> sections = new ArrayList<>(List.of(s1, s2));
		TransportPlan transportPlan = transportPlanRepository.save(new TransportPlan(sections, 100));

		sectionRepository.save(s1);
		sectionRepository.save(s2);
		milestoneRepository.save(m1);
		milestoneRepository.save(m2);
		milestoneRepository.save(m3);
		milestoneRepository.save(m4);

		LocalDateTime plannedTimeOfFirstMilestoneBefore = m1.getPlannedTime();
		LocalDateTime plannedTimeOfSecondMilestoneBefore = m2.getPlannedTime();
		long delayInMinutes = 30L;
		addDelay(transportPlan.getId(), m1.getId(), delayInMinutes);
		LocalDateTime plannedTimeOfFirstMilestoneAfter = milestoneRepository.findById(m1.getId()).get()
				.getPlannedTime();
		LocalDateTime plannedTimeOfSecondMilestoneAfter = milestoneRepository.findById(m2.getId()).get()
				.getPlannedTime();

		assertThat(plannedTimeOfFirstMilestoneBefore.plus(delayInMinutes, ChronoUnit.MINUTES))
				.isEqualTo(plannedTimeOfFirstMilestoneAfter);
		assertThat(plannedTimeOfSecondMilestoneBefore.plus(delayInMinutes, ChronoUnit.MINUTES))
				.isEqualTo(plannedTimeOfSecondMilestoneAfter);

	}

	@Test
	void testAddDelayToSecondMilestoneInSection() throws Exception {
		Address a = addressRepository.save(new Address());
		Milestone m1 = milestoneRepository.save(new Milestone(a, LocalDateTime.of(2021, 01, 01, 8, 00)));
		Milestone m2 = milestoneRepository.save(new Milestone(a, LocalDateTime.of(2021, 01, 01, 9, 00)));
		Milestone m3 = milestoneRepository.save(new Milestone(a, LocalDateTime.of(2021, 01, 01, 10, 00)));
		Milestone m4 = milestoneRepository.save(new Milestone(a, LocalDateTime.of(2021, 01, 01, 11, 00)));
		Section s1 = sectionRepository.save(new Section(m1, m2));
		Section s2 = sectionRepository.save(new Section(m3, m4));

		List<Section> sections = new ArrayList<>(List.of(s1, s2));
		TransportPlan transportPlan = transportPlanRepository.save(new TransportPlan(sections, 100));

		sectionRepository.save(s1);
		sectionRepository.save(s2);
		milestoneRepository.save(m1);
		milestoneRepository.save(m2);
		milestoneRepository.save(m3);
		milestoneRepository.save(m4);

		LocalDateTime plannedTimeOfFirstSectionSecondMilestoneBefore = m2.getPlannedTime();
		LocalDateTime plannedTimeOfNextSectionFirstMilestoneBefore = m3.getPlannedTime();
		long delayInMinutes = 30L;
		addDelay(transportPlan.getId(), m2.getId(), delayInMinutes);
		LocalDateTime plannedTimeOfFirstSectionSecondMilestoneAfter = milestoneRepository.findById(m2.getId()).get()
				.getPlannedTime();
		LocalDateTime plannedTimeOfNextSectionFirstMilestoneAfter = milestoneRepository.findById(m3.getId()).get()
				.getPlannedTime();

		assertThat(plannedTimeOfFirstSectionSecondMilestoneBefore.plus(delayInMinutes, ChronoUnit.MINUTES))
				.isEqualTo(plannedTimeOfFirstSectionSecondMilestoneAfter);
		assertThat(plannedTimeOfNextSectionFirstMilestoneBefore.plus(delayInMinutes, ChronoUnit.MINUTES))
				.isEqualTo(plannedTimeOfNextSectionFirstMilestoneAfter);
	}

	@Test
	void testAddDelayToLastMilestoneInTransportplan() throws Exception {
		Address a = addressRepository.save(new Address());
		Milestone m1 = milestoneRepository.save(new Milestone(a, LocalDateTime.of(2021, 01, 01, 8, 00)));
		Milestone m2 = milestoneRepository.save(new Milestone(a, LocalDateTime.of(2021, 01, 01, 9, 00)));
		Milestone m3 = milestoneRepository.save(new Milestone(a, LocalDateTime.of(2021, 01, 01, 10, 00)));
		Milestone m4 = milestoneRepository.save(new Milestone(a, LocalDateTime.of(2021, 01, 01, 11, 00)));
		Section s1 = sectionRepository.save(new Section(m1, m2));
		Section s2 = sectionRepository.save(new Section(m3, m4));

		List<Section> sections = new ArrayList<>(List.of(s1, s2));
		TransportPlan transportPlan = transportPlanRepository.save(new TransportPlan(sections, 100));

		sectionRepository.save(s1);
		sectionRepository.save(s2);
		milestoneRepository.save(m1);
		milestoneRepository.save(m2);
		milestoneRepository.save(m3);
		milestoneRepository.save(m4);

		LocalDateTime plannedTimeOfLastMilestoneBefore = m4.getPlannedTime();
		long delayInMinutes = 30L;
		addDelay(transportPlan.getId(), m4.getId(), delayInMinutes);
		LocalDateTime plannedTimeOfLastMilestoneAfter = milestoneRepository.findById(m4.getId()).get().getPlannedTime();

		assertThat(plannedTimeOfLastMilestoneBefore.plus(delayInMinutes, ChronoUnit.MINUTES))
				.isEqualTo(plannedTimeOfLastMilestoneAfter);
	}

	@Test
	void testThatIsDelayReducesExpectedRevenue() throws Exception {
		Address a = addressRepository.save(new Address());
		Milestone m1 = milestoneRepository.save(new Milestone(a, LocalDateTime.of(2021, 01, 01, 8, 00)));
		Milestone m2 = milestoneRepository.save(new Milestone(a, LocalDateTime.of(2021, 01, 01, 9, 00)));
		Milestone m3 = milestoneRepository.save(new Milestone(a, LocalDateTime.of(2021, 01, 01, 10, 00)));
		Milestone m4 = milestoneRepository.save(new Milestone(a, LocalDateTime.of(2021, 01, 01, 11, 00)));
		Section s1 = sectionRepository.save(new Section(m1, m2));
		Section s2 = sectionRepository.save(new Section(m3, m4));

		List<Section> sections = new ArrayList<>(List.of(s1, s2));

		int expectedRevenueBefore = 100;
		TransportPlan transportPlan = transportPlanRepository.save(new TransportPlan(sections, expectedRevenueBefore));
		long transportPlanId = transportPlan.getId();

		sectionRepository.save(s1);
		sectionRepository.save(s2);
		milestoneRepository.save(m1);
		milestoneRepository.save(m2);
		milestoneRepository.save(m3);
		milestoneRepository.save(m4);

		long delayInMinutes = 29L;
		addDelay(transportPlanId, m1.getId(), delayInMinutes);
		int expectedRevenueAfter = transportPlanRepository.findById(transportPlanId).get().getExpectedRevenue();
		assertThat(expectedRevenueBefore).isEqualTo(expectedRevenueAfter);

		transportPlan.setExpectedRevenue(expectedRevenueBefore);
		transportPlanRepository.save(transportPlan);
		delayInMinutes = 30L;
		addDelay(transportPlanId, m1.getId(), delayInMinutes);
		expectedRevenueAfter = transportPlanRepository.findById(transportPlanId).get().getExpectedRevenue();
		assertThat(expectedRevenueBefore * 0.9).isEqualTo(expectedRevenueAfter);

		transportPlan.setExpectedRevenue(expectedRevenueBefore);
		transportPlanRepository.save(transportPlan);
		delayInMinutes = 60L;
		addDelay(transportPlanId, m1.getId(), delayInMinutes);
		expectedRevenueAfter = transportPlanRepository.findById(transportPlanId).get().getExpectedRevenue();
		assertThat(expectedRevenueBefore * 0.8).isEqualTo(expectedRevenueAfter);

		transportPlan.setExpectedRevenue(expectedRevenueBefore);
		transportPlanRepository.save(transportPlan);
		delayInMinutes = 120L;
		addDelay(transportPlanId, m1.getId(), delayInMinutes);
		expectedRevenueAfter = transportPlanRepository.findById(transportPlanId).get().getExpectedRevenue();
		assertThat(expectedRevenueBefore * 0.7).isEqualTo(expectedRevenueAfter);

	}

	private ResponseSpec addDelay(long transportPlanId, long milestoneId, long durationOfDelay) {
		DelayDto delay = new DelayDto(milestoneId, durationOfDelay);
		String path = BASE_URI + "/" + transportPlanId + "/delay";
		return webTestClient.post().uri(path).headers(headers -> headers.setBearerAuth(jwtToken)).bodyValue(delay)
				.exchange().expectStatus().isOk();
	}
}