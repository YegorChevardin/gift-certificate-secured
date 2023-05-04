package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.envers.Audited;

import java.util.Objects;

/**
 * Class that represents information form tags database in objects
 * @author yegorchevardin
 * @version 0.0.1
 */
@Entity
@Table(name = "tags")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, length = 45)
    private String name;
}
