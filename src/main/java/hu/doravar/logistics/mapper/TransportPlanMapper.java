package hu.doravar.logistics.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.doravar.logistics.dto.TransportPlanDto;
import hu.doravar.logistics.model.TransportPlan;

@Mapper(componentModel = "spring")
public interface TransportPlanMapper {

	TransportPlanDto transportPlanToDto(TransportPlan transportPlan);
	
	TransportPlan dtoToTransportPlan(TransportPlanDto transportPlanDto);
	
	List<TransportPlanDto> transportPlansToDtos(List<TransportPlan> transportPlans);
	
	List<TransportPlan> dtosToTransportPlans(List<TransportPlanDto> transportPlanDtos);
}
