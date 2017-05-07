package de.roamingthings.expenses.security;

import de.roamingthings.expenses.user.domain.Role;
import de.roamingthings.expenses.user.domain.User;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/07
 */
public class UserToUserDetailsTest {
    @Test
    public void should_convert_User_to_UserDetails() throws Exception {
        final Set<Role> roles = Arrays.stream("ADMIN,USER".split(","))
                .map(Role::new)
                .collect(toSet());
        final User user = new User("testuser", null, true, roles);

        UserToUserDetails converter = new UserToUserDetails();
        final UserDetails convertedUserDetails = converter.convert(user);

        assertNotNull(convertedUserDetails);

        assertThat(convertedUserDetails.getUsername(), is(user.getUsername()));
        assertThat(convertedUserDetails.getPassword(), is(user.getPasswordHash()));
        final Collection<? extends GrantedAuthority> authorities = convertedUserDetails.getAuthorities();
        assertNotNull(authorities);

        assertThat(authorities, hasSize(2));
        assertThat(authorities,
                containsInAnyOrder(
                        hasProperty("authority", is("ADMIN")),
                        hasProperty("authority", is("USER"))
                )
        );
    }

}