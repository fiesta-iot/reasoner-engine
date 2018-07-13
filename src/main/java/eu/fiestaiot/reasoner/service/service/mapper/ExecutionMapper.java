package eu.fiestaiot.reasoner.service.service.mapper;

import eu.fiestaiot.reasoner.service.domain.Execution;
import eu.fiestaiot.reasoner.service.web.rest.vm.dto.ExecutionDTO;
import org.mapstruct.Mapper;

import java.util.List;


public interface ExecutionMapper {
    ExecutionDTO executionToExecutionDTO(Execution execution);
    List<ExecutionDTO> executionsToExecutionDTOs(List<Execution> executions);
}
