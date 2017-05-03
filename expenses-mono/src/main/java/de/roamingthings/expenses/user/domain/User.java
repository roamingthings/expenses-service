package de.roamingthings.expenses.user.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/03
 */
@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String authProviderId;

    public User(String authProviderId) {
        this.authProviderId = authProviderId;
    }
}
