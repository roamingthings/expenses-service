package de.roamingthings.expenses.user.service;

import de.roamingthings.expenses.user.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/03
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
