package de.roamingthings.expenses.config;

import de.roamingthings.expenses.user.service.RoleService;
import de.roamingthings.expenses.user.service.UserService;
import org.springframework.context.annotation.Configuration;

/**
 * Ensure that the roles and an admin user are present in the database.
 *
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/07
 */
@Configuration
public class AuthenticationDataLoader implements DataInitializer {

    private final RoleService roleService;
    private final UserService userService;

    public AuthenticationDataLoader(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void initializeData() {
        roleService.addRoleIfNotExists("ADMIN");
        roleService.addRoleIfNotExists("USER");

        userService.addEnabledUserWithRolesIfNotExists("admin", "secret", roleService.findByRole("ADMIN"), roleService.findByRole("USER"));
    }
}
