package eu.fiestaiot.reasoner.service.repository;

import eu.fiestaiot.reasoner.service.domain.Reasoning;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Reasoning entity.
 */
@SuppressWarnings("unused")
public interface ReasoningRepository extends JpaRepository<Reasoning,Long> {

}
