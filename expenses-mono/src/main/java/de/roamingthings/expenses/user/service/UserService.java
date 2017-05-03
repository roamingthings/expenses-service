package de.roamingthings.expenses.user.service;

import de.roamingthings.expenses.user.domain.User;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/03
 */
public interface UserService {
    User findUserByAuthProviderId(String authProviderId);
}
