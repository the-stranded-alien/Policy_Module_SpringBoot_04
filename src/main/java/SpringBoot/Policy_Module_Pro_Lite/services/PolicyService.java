package SpringBoot.Policy_Module_Pro_Lite.services;

import SpringBoot.Policy_Module_Pro_Lite.models.Policy;
import SpringBoot.Policy_Module_Pro_Lite.models.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PolicyService {
    List<Policy> getAllPoliciesByCreator(User creator);
    Policy savePolicy(Policy policy);
    Policy getPolicyById(Long id);
    void deletePolicyById(Long id);
    Policy updatePolicy(Policy policy);
    Page<Policy> findPaginated(Integer pageNo, Integer pageSize, String sortField, String sortDirection);
}
