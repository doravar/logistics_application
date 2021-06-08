package hu.doravar.logistics.web;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.doravar.logistics.dto.DelayDto;
import hu.doravar.logistics.mapper.TransportPlanMapper;
import hu.doravar.logistics.repository.MilestoneRepository;
import hu.doravar.logistics.repository.TransportPlanRepository;
import hu.doravar.logistics.service.TransportPlanService;

@RestController
@RequestMapping("/api/transportplans")
public class TransportPlanController {

	@Autowired
	TransportPlanService transportPlanService;

	@Autowired
	TransportPlanMapper transportPlanMapper;

	@Autowired
	TransportPlanRepository transportPlanRepository;

	@Autowired
	MilestoneRepository milestoneRepository;

	@PostMapping("/{id}/delay")
	public HttpStatus addDelay(@PathVariable long id, @RequestBody DelayDto delay) {
		try {
			transportPlanService.addDelay(id, delay);
			return HttpStatus.OK;
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

}
