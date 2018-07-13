package eu.fiestaiot.reasoner.service.service;

import eu.fiestaiot.reasoner.service.domain.Execution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Execution.
 */
public interface ExecutionService {

    /**
     * Save a execution.
     *
     * @param execution the entity to save
     * @return the persisted entity
     */
    Execution save(Execution execution);

    /**
     *  Get all the excutions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Execution> findAll(Pageable pageable);

    /**
     *  Get the "id" excution.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Execution findOne(Long id);

    /**
     *  Delete the "id" excution.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    Page<Execution> findAllByUserId(String userID, Pageable pageable);
}
