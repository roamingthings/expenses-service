package de.roamingthings.expenses.contract.useraccount.repository;

import de.roamingthings.expenses.user.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/25
 */
public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
    Optional<UserAccount> findByUsername(String username);
    boolean existsByUsername(String username);
}
