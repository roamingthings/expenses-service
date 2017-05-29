package de.roamingthings.expenses.userprofile.domain;

import de.roamingthings.persistence.auditing.Creatable;
import de.roamingthings.persistence.auditing.Modifiable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/03
 */
@Entity
@Table(name = "USER_PROFILE")
@Data
@NoArgsConstructor
public class UserProfile implements Creatable, Modifiable {
    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_at", nullable = false)
    private Date modifiedAt;

    @Column(name = "github_id")
    private String githubId;

    @Column(name = "facebook_id")
    private String facebookId;

    @Email
    private String email;

    public UserProfile(String email) {
        this.email = email;
    }
}
