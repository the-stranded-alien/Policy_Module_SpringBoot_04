package SpringBoot.Policy_Module_Pro_Lite.services.customUser;

import SpringBoot.Policy_Module_Pro_Lite.models.UserInfo;
import SpringBoot.Policy_Module_Pro_Lite.repositories.UserRepository;
import SpringBoot.Policy_Module_Pro_Lite.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Resource private UserRepository userRepository;

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User domainUser = userRepository.findByUsername(username);

        if(domainUser == null) {
            throw new UsernameNotFoundException("User Not Found !");
        }

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new UserInfo(domainUser, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked);
    }
}

