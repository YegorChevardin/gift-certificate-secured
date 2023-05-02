package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.envers.Audited;

import java.util.Objects;

/**
 * Roles representation for roles table in database
 * @author yegorchevardin
 * @version 0.0.1
 */
@Entity
@Table(name = "roles")
@Audited
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RoleEntity that = (RoleEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
