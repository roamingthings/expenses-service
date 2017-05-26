package de.roamingthings.expenses.useraccount.service;


import de.roamingthings.expenses.useraccount.domain.Role;
import de.roamingthings.expenses.useraccount.domain.UserAccount;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/03
 */
public interface UserService {
    UserAccount findByUsername(String username);

    void addEnabledUserWithRolesIfNotExists(String username, String password, Role... roles);
}
