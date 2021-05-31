package SpringBoot.Policy_Module_Pro_Lite.repositories;

import SpringBoot.Policy_Module_Pro_Lite.models.Policy;
import SpringBoot.Policy_Module_Pro_Lite.models.Risk;
import SpringBoot.Policy_Module_Pro_Lite.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RiskRepository extends JpaRepository<Risk, Long> {
    List<Risk> findAllByCreator(User creator);
    Page<Risk> findAllByCreator(User creator, Pageable pageable);
    List<Risk> findAllByCreatorAndStatus(User creator, Boolean status);
    Page<Risk> findAllByCreatorAndStatus(User creator, Boolean status, Pageable pageable);
    Set<Risk> findAllByCreatorAndStatusAndPoliciesIncludedInContaining(User creator, Boolean status, Policy policy);
    Page<Risk> findAllByCreatorAndStatusAndPoliciesIncludedInContaining(User creator, Boolean status, Policy policy, Pageable pageable);
}
