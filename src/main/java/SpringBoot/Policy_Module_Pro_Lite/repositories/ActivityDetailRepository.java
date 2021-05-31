package SpringBoot.Policy_Module_Pro_Lite.repositories;

import SpringBoot.Policy_Module_Pro_Lite.models.Activity;
import SpringBoot.Policy_Module_Pro_Lite.models.ActivityDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityDetailRepository extends JpaRepository<ActivityDetail, Long> {
    Page<ActivityDetail> findAllByActivity(Activity activity, Pageable pageable);
}