package hu.doravar.logistics.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hu.doravar.logistics.model.Address;
import hu.doravar.logistics.model.LogisticsUser;
import hu.doravar.logistics.model.Milestone;
import hu.doravar.logistics.model.Section;
import hu.doravar.logistics.model.TransportPlan;
import hu.doravar.logistics.repository.AddressRepository;
import hu.doravar.logistics.repository.MilestoneRepository;
import hu.doravar.logistics.repository.SectionRepository;
import hu.doravar.logistics.repository.TransportPlanRepository;
import hu.doravar.logistics.repository.UserRepository;

@Service
public class InitDbService {

	@Autowired
	TransportPlanRepository transportPlanRepository;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	MilestoneRepository milestoneRepository;
	@Autowired
	SectionRepository sectionRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	public void insertTestData() {

		userRepository.save(new LogisticsUser("admin", passwordEncoder.encode("pass"),
				Set.of("AddressManager", "TransportManager")));
		userRepository.save(new LogisticsUser("amanager", passwordEncoder.encode("pass"), Set.of("AddressManager")));
		userRepository.save(new LogisticsUser("tmanager", passwordEncoder.encode("pass"), Set.of("TransportManager")));

		Address a = new Address(0L, "HUN", "Budapest", "Medve utca", "1122", 22, 47.5f, 19.0f);
		Address b = new Address(0L, "USA", "Oakhurst", "Ely Way", "3456", 31234, 37.3f, -119.7f);
		Address c = new Address(0L, "LAO", "Vang Vieng", "Khampa street", "4321", 66, 18.9f, 102.4f);
		Address d = new Address(0L, "PRT", "Porto", "R. de São Paulo", "9999", 123, 41.1f, -8.6f);
		Address e = new Address(0L, "DNK", "Copenhagen", "Pommernsgade", "5555", 102, 55.5f, 12.6f);
		Address f = new Address(0L, "FIN", "Ivalo", "Vasantie", "3333", 1, 68.6f, 28.5f);
		Address g = new Address(0L, "HUN", "Budapest", "Medve utca", "1122", 22, 47.5f, 19.0f);
		Address h = new Address(0L, "HUN", "Budapest", "Medve utca", "1122", 22, 47.5f, 19.0f);
		Address i = new Address(0L, "HUN", "Budapest", "Medve utca", "1122", 22, 47.5f, 19.0f);

		addressRepository.save(a);
		addressRepository.save(b);
		addressRepository.save(c);
		addressRepository.save(d);
		addressRepository.save(e);
		addressRepository.save(f);
		addressRepository.save(g);
		addressRepository.save(h);
		addressRepository.save(i);
		Milestone m1 = new Milestone(a, LocalDateTime.of(2021, 01, 01, 8, 00));
		Milestone m2 = new Milestone(b, LocalDateTime.of(2021, 01, 01, 9, 00));
		Milestone m3 = new Milestone(b, LocalDateTime.of(2021, 01, 01, 10, 00));
		Milestone m4 = new Milestone(c, LocalDateTime.of(2021, 01, 01, 11, 00));
		Milestone m5 = new Milestone(c, LocalDateTime.of(2021, 01, 01, 12, 00));
		Milestone m6 = new Milestone(d, LocalDateTime.of(2021, 01, 01, 13, 00));
		milestoneRepository.save(m1);
		milestoneRepository.save(m2);
		milestoneRepository.save(m3);
		milestoneRepository.save(m4);
		milestoneRepository.save(m5);
		milestoneRepository.save(m6);

		Section s1 = new Section(m1, m2); // a-ból b-be
		Section s2 = new Section(m3, m4); // b-ből c-be
		Section s3 = new Section(m5, m6); // c-ből d-be

		List<Section> sl1 = new ArrayList<>(List.of(s1, s2)); // a-ból c-be
		TransportPlan t1 = new TransportPlan(sl1, 1000); // a-ból c-be
		List<Section> sl2 = new ArrayList<>(List.of(s3));
		TransportPlan t2 = new TransportPlan(sl2, 1000);

		transportPlanRepository.save(t1);
		transportPlanRepository.save(t2);
		sectionRepository.save(s1);
		sectionRepository.save(s2);
		sectionRepository.save(s3);
		milestoneRepository.save(m1);
		milestoneRepository.save(m2);
		milestoneRepository.save(m3);
		milestoneRepository.save(m4);
		milestoneRepository.save(m5);
		milestoneRepository.save(m6);

	}

}
