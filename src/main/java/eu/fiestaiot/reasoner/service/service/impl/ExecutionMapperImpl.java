package eu.fiestaiot.reasoner.service.service.impl;

import eu.fiestaiot.reasoner.service.domain.Execution;
import eu.fiestaiot.reasoner.service.service.mapper.ExecutionMapper;
import eu.fiestaiot.reasoner.service.web.rest.vm.dto.ExecutionDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ExecutionMapperImpl implements ExecutionMapper {
    @Override
    public ExecutionDTO executionToExecutionDTO(Execution execution) {
        ExecutionDTO dto = new ExecutionDTO();

        dto.setCreated(execution.getCreated());
        dto.setExecuteType(execution.getType());
        dto.setFullData(execution.getFullData());
        dto.setInfferedData(execution.getInfferedData());
        dto.setOriginalData(execution.getOriginalData());
        dto.setId(execution.getId());
        dto.setRuleContent(execution.getRuleContent());
        dto.setRegisterRuleId(execution.getRegisterRule().getId());
        dto.setSensor(execution.getSensor());
        dto.setStarted(execution.getStarted());
        dto.setEnded(execution.getEnded());
        dto.setUserId(execution.getUserId());
        dto.setStatus(execution.getStatus());
        dto.setUpdated(execution.getUpdated());

        return dto;
    }

    @Override
    public List<ExecutionDTO> executionsToExecutionDTOs(List<Execution> executions) {
        if ( executions == null ) {
            return null;
        }
        List<ExecutionDTO> list = new ArrayList<>();
        for ( Execution execution : executions ) {
            list.add( executionToExecutionDTO( execution ) );
        }
        return list;
    }
}
