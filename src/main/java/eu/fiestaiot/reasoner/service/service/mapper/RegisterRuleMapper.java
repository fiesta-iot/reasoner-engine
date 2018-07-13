package eu.fiestaiot.reasoner.service.service.mapper;


import eu.fiestaiot.reasoner.service.domain.RegisterRule;
import eu.fiestaiot.reasoner.service.web.rest.vm.dto.RegisterRuleDTO;
import org.mapstruct.Mapper;

import java.util.List;


public interface RegisterRuleMapper {

    RegisterRuleDTO registerRuleToRegisterRuleDTO(RegisterRule registerRule);

    List<RegisterRuleDTO> registerRulesToRegisterRuleDTOs(List<RegisterRule> registerRules);



}
