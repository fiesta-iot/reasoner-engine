package eu.fiestaiot.reasoner.service.repository;

import eu.fiestaiot.reasoner.service.domain.Execution;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Execution entity.
 */
@SuppressWarnings("unused")
public interface ExecutionRepository extends JpaRepository<Execution,Long> {

    Page<Execution> findAllByUserId(String userID, Pageable pageable);

}
