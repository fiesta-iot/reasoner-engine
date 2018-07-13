package eu.fiestaiot.reasoner.service.repository;

import eu.fiestaiot.reasoner.service.domain.RegisterRule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RegisterRule entity.
 */
@SuppressWarnings("unused")

public interface RegisterRuleRepository extends JpaRepository<RegisterRule,Long> {

    Page<RegisterRule> findAllByUserId(String userID, Pageable pageable);
}
