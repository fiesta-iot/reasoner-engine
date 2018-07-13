package eu.fiestaiot.reasoner.service.service;

import eu.fiestaiot.reasoner.service.domain.Execution;
import eu.fiestaiot.reasoner.service.domain.RegisterRule;
import eu.fiestaiot.reasoner.service.web.rest.vm.ReExecuteRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RegisterRule.
 */
public interface RegisterRuleService {

    /**
     * Save a registerRule.
     *
     * @param registerRule the entity to save
     * @return the persisted entity
     */
    RegisterRule save(RegisterRule registerRule);

    /**
     *  Get all the registerRules.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RegisterRule> findAll(Pageable pageable);

    /**
     *  Get the "id" registerRule.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RegisterRule findOne(Long id);

    /**
     *  Delete the "id" registerRule.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    RegisterRule executeReasoning(RegisterRule registerRule);

    Execution excuteReasoningWithTime(ReExecuteRule reExecuteRule, String token, String userId);

    Page<RegisterRule> findAllByUserId(String userID, Pageable pageable);
}
