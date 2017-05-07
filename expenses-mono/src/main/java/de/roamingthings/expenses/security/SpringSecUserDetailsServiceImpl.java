package de.roamingthings.expenses.security;

import de.roamingthings.expenses.user.domain.User;
import de.roamingthings.expenses.user.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/07
 */
@Service("userDetailsService")
public class SpringSecUserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;
    private final Converter<User, UserDetails> userUserDetailsConverter;

    public SpringSecUserDetailsServiceImpl(UserService userService, @Qualifier("userToUserDetails") Converter<User, UserDetails> userUserDetailsConverter) {
        this.userService = userService;
        this.userUserDetailsConverter = userUserDetailsConverter;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userUserDetailsConverter.convert(userService.findByUsername(username));
    }
}
