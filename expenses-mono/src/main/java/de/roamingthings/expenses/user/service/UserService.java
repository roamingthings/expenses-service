package de.roamingthings.expenses.user.service;

import de.roamingthings.expenses.user.domain.Role;
import de.roamingthings.expenses.user.domain.User;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/03
 */
public interface UserService {
    User findByUsername(String username);

    void addEnabledUserWithRolesIfNotExists(String username, String password, Role... roles);
}
