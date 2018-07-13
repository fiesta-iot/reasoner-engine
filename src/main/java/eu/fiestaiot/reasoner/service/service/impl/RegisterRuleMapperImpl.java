package eu.fiestaiot.reasoner.service.service.impl;

import eu.fiestaiot.reasoner.service.domain.RegisterRule;
import eu.fiestaiot.reasoner.service.service.mapper.RegisterRuleMapper;
import eu.fiestaiot.reasoner.service.web.rest.vm.dto.RegisterRuleDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegisterRuleMapperImpl implements RegisterRuleMapper{
    @Override
    public RegisterRuleDTO registerRuleToRegisterRuleDTO(RegisterRule registerRule) {
        RegisterRuleDTO dto = new RegisterRuleDTO();
        dto.setCreated(registerRule.getCreated());
        dto.setDescription(registerRule.getDescription());
        dto.setId(registerRule.getId());
        dto.setLatitude(registerRule.getLatitude());
        dto.setLongitude(registerRule.getLongitude());
        dto.setName(registerRule.getName());
        dto.setRuleContent(registerRule.getRuleContent());
        dto.setSensor(registerRule.getSensor());
        dto.setUnitOfMeasurement(registerRule.getUnitOfMeasurement());
        dto.setQuantityKind(registerRule.getQuantityKind());
        dto.setUserId(registerRule.getUserId());
        dto.setUpdated(registerRule.getUpdated());
        dto.setRuleId(registerRule.getReasoning().getId());
        return dto;
    }

    @Override
    public List<RegisterRuleDTO> registerRulesToRegisterRuleDTOs(List<RegisterRule> registerRules) {
        if ( registerRules == null ) {

            return null;
        }

        List<RegisterRuleDTO> list = new ArrayList<RegisterRuleDTO>();

        for ( RegisterRule registerRule : registerRules ) {

            list.add( registerRuleToRegisterRuleDTO( registerRule ) );
        }

        return list;
    }


}
