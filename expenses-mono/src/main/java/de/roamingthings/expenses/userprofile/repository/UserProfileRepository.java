package de.roamingthings.expenses.userprofile.repository;

import de.roamingthings.expenses.userprofile.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/03
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
