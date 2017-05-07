package de.roamingthings.expenses.user.service;

import de.roamingthings.expenses.user.domain.Role;
import de.roamingthings.expenses.user.domain.User;
import de.roamingthings.expenses.user.repository.UserRepository;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/07
 */
public class UserServiceImplTest {
    @Test
    public void should_add_user_if_not_existing_in_repository() throws Exception {
        final UserRepository userRepositoryMock = mock(UserRepository.class);
        when(userRepositoryMock.existsByUsername("test")).thenReturn(false);
        final PasswordEncoder passwordEncoderMock = mock(PasswordEncoder.class);
        when(passwordEncoderMock.encode(any())).thenReturn("encoded");

        UserServiceImpl userService = new UserServiceImpl(userRepositoryMock, passwordEncoderMock);

        userService.addEnabledUserWithRolesIfNotExists("test", "password", new Role("ADMIN"), new Role("USER"));

        ArgumentCaptor<String> usernameCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepositoryMock).existsByUsername(usernameCaptor.capture());
        assertThat(usernameCaptor.getValue(), is("test"));

        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        verify(passwordEncoderMock).encode(passwordCaptor.capture());
        assertThat(passwordCaptor.getValue(), is("password"));

        ArgumentCaptor<User> userEntityCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepositoryMock, times(1)).save(userEntityCaptor.capture());
        final User createdUser = userEntityCaptor.getValue();
        assertNotNull(createdUser);
        assertThat(createdUser.getUsername(), is("test"));
        assertThat(createdUser.getPasswordHash(), is("encoded"));
        assertThat(createdUser.getRoles(), containsInAnyOrder(
                hasProperty("role", is("ADMIN")),
                hasProperty("role", is("USER"))
        ));
    }

    @Test
    public void should_not_add_user_if_existing_in_repository() throws Exception {
        final UserRepository userRepositoryMock = mock(UserRepository.class);
        when(userRepositoryMock.existsByUsername("test")).thenReturn(true);
        final PasswordEncoder passwordEncoderMock = mock(PasswordEncoder.class);

        UserServiceImpl userService = new UserServiceImpl(userRepositoryMock, passwordEncoderMock);

        userService.addEnabledUserWithRolesIfNotExists("test", "password", new Role("ADMIN"), new Role("USER"));

        ArgumentCaptor<String> usernameCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepositoryMock).existsByUsername(usernameCaptor.capture());
        assertThat(usernameCaptor.getValue(), is("test"));

        verify(userRepositoryMock, never()).save(any(User.class));
    }

}