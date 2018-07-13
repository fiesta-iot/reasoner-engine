package eu.fiestaiot.reasoner.service.service.impl;

import eu.fiestaiot.reasoner.service.domain.Reasoning;
import eu.fiestaiot.reasoner.service.service.mapper.ReasoningMapper;
import eu.fiestaiot.reasoner.service.web.rest.vm.dto.ReasoningDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ReasoningMapperImpl implements ReasoningMapper{
    @Override
    public ReasoningDTO reasoningToReasoningDTO(Reasoning reasoning) {

        ReasoningDTO dto = new ReasoningDTO();
        dto.setId(reasoning.getId());
        dto.setName(reasoning.getName());
        dto.setUserId(reasoning.getUserId());
        dto.setCreated(reasoning.getCreated());
        dto.setUpdated(reasoning.getUpdated());
        dto.setContent(reasoning.getContent());
        dto.setSensor(reasoning.getSensor());
        dto.setDescription(reasoning.getDescription());
        dto.setLongitude(reasoning.getLongitude());
        dto.setLatitude(reasoning.getLatitude());
        dto.setQuantityKind(reasoning.getQuantityKind());
        dto.setUnitOfMeasurement(reasoning.getUnitOfMeasurement());
        dto.setRuleType(reasoning.getRuleType());
        dto.setNonExpertOriginalRules(reasoning.getNonExpertOriginalRules());

        return dto;
    }

    @Override
    public List<ReasoningDTO> reasoningsToReasoningDTOs(List<Reasoning> reasonings) {
        if(reasonings == null) {
            return null;
        }
        List<ReasoningDTO> list = new ArrayList<>();
        for(Reasoning reasoning: reasonings) {
            list.add(reasoningToReasoningDTO(reasoning));
        }
        return list;
    }
}
