package de.roamingthings.expenses.user.service;

import de.roamingthings.expenses.contract.useraccount.repository.UserAccountRepository;
import de.roamingthings.expenses.user.domain.Role;
import de.roamingthings.expenses.user.domain.UserAccount;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/03
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserAccount findByUsername(String username) {
        return userAccountRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void addEnabledUserWithRolesIfNotExists(String username, String password, Role... roles) {
        if (!userAccountRepository.existsByUsername(username)) {
            final String passwordHash = passwordEncoder.encode(password);

            final Set<Role> roleSet = Stream.of(roles).collect(toSet());
            UserAccount userAccount = new UserAccount(username, passwordHash, true, roleSet);
            userAccountRepository.save(userAccount);
        }
    }
}
