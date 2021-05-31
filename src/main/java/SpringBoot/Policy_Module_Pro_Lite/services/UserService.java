package SpringBoot.Policy_Module_Pro_Lite.services;

import SpringBoot.Policy_Module_Pro_Lite.dto.UserRegistrationDto;
import SpringBoot.Policy_Module_Pro_Lite.models.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findByUsername(String username);
    User save(UserRegistrationDto registration);
}
