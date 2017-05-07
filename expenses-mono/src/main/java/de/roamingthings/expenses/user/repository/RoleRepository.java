package de.roamingthings.expenses.user.repository;

import de.roamingthings.expenses.user.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/07
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);

    boolean existsByRole(String role);
}
