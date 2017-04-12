package de.roamingthings.expenses;

import javax.persistence.PrePersist;
import java.util.Date;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/12
 */
public class CreatableEntityListener {
    @PrePersist
    public void initCreationalMetadata(Creatable entity) {
        entity.setCreatedAt(new Date());
    }
}
