package de.roamingthings.expenses;

import de.roamingthings.expenses.business.userprofile.domain.UserProfile;
import de.roamingthings.expenses.business.userprofile.repository.UserProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserProfileDetailsService implements UserDetailsService {
    Logger LOGGER = LoggerFactory.getLogger(UserProfileDetailsService.class);

    private UserProfileRepository userRepository;

    public UserProfileDetailsService(UserProfileRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.debug("Check username " + username);
        UserProfile user = userRepository.findByGithubId(username);
        if (user == null) {
            user = userRepository.findByFacebookId(username);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(user.getEmail(), "[PROTECTED]", grantedAuthorities);
    }

}
