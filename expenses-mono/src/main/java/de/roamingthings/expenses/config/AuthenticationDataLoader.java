package de.roamingthings.expenses.config;

import de.roamingthings.expenses.business.expense.repository.RecurringExpenseRepository;
import de.roamingthings.expenses.user.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Ensure that the roles and an admin user are present in the database.
 *
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/07
 */
@Configuration
public class AuthenticationDataLoader {

    private final RoleService roleService;

    public AuthenticationDataLoader(RoleService roleService) {
        this.roleService = roleService;
    }

    @Bean
    @Transactional
    CommandLineRunner initData(RecurringExpenseRepository recurringExpenseRepository) {
        return args -> {
            roleService.addRoleIfNotExists("ADMIN");
            roleService.addRoleIfNotExists("USER");
        };
    }
}
