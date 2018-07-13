package eu.fiestaiot.reasoner.service.service.impl;

import eu.fiestaiot.reasoner.service.domain.Execution;
import eu.fiestaiot.reasoner.service.service.ExecutionService;
import eu.fiestaiot.reasoner.service.repository.ExecutionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Service Implementation for managing Execution.
 */
@Service
@Transactional
public class ExecutionServiceImpl implements ExecutionService{

    private final Logger log = LoggerFactory.getLogger(ExecutionServiceImpl.class);

    @Inject
    private ExecutionRepository excutionRepository;

    /**
     * Save a execution.
     *
     * @param execution the entity to save
     * @return the persisted entity
     */
    public Execution save(Execution execution) {
        log.debug("Request to save Execution : {}", execution);
        Execution result = excutionRepository.save(execution);
        return result;
    }

    /**
     *  Get all the excutions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Execution> findAll(Pageable pageable) {
        log.debug("Request to get all Excutions");
        Page<Execution> result = excutionRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one excution by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Execution findOne(Long id) {
        log.debug("Request to get Execution : {}", id);
        Execution execution = excutionRepository.findOne(id);
        return execution;
    }

    /**
     *  Delete the  excution by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Execution : {}", id);
        excutionRepository.delete(id);
    }

    @Override
    public Page<Execution> findAllByUserId(String userID, Pageable pageable) {
        return excutionRepository.findAllByUserId(userID, pageable);
    }
}
