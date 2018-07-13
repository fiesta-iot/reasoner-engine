package eu.fiestaiot.reasoner.service.service;

import eu.fiestaiot.reasoner.service.domain.Reasoning;
import eu.fiestaiot.reasoner.service.service.dto.ValidateRuleRequest;
import eu.fiestaiot.reasoner.service.service.dto.ValidateRuleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Reasoning.
 */
public interface ReasoningService {

    /**
     * Save a reasoning.
     *
     * @param reasoning the entity to save
     * @return the persisted entity
     */
    Reasoning save(Reasoning reasoning);

    /**
     *  Get all the reasonings.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Reasoning> findAll(Pageable pageable);

    /**
     *  Get the "id" reasoning.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Reasoning findOne(Long id);

    /**
     *  Delete the "id" reasoning.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /*
     *
     *
     */
    ValidateRuleResponse validateRule(ValidateRuleRequest request, String token);


}
