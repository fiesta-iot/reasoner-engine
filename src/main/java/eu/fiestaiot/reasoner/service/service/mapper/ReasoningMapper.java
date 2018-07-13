package eu.fiestaiot.reasoner.service.service.mapper;

import eu.fiestaiot.reasoner.service.domain.Reasoning;
import eu.fiestaiot.reasoner.service.web.rest.vm.dto.ReasoningDTO;
import org.mapstruct.Mapper;

import java.util.List;


public interface ReasoningMapper {

    ReasoningDTO reasoningToReasoningDTO(Reasoning reasoning);
    List<ReasoningDTO> reasoningsToReasoningDTOs(List<Reasoning> reasonings);

}
