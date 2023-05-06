package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.envers.Audited;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class that represents data from gift certificate database in object
 * @author yegorchevardin
 * @version 0.0.1
 */
@Entity
@Table(name = "gift_certificates")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Audited
public class GiftCertificateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price", nullable = false)
    private Float price;
    @Column(name = "duration", nullable = false)
    private Integer duration;
    @Column(name = "create_date", nullable = false)
    private Timestamp createDate;
    @Column(name = "last_update_date", nullable = false)
    private Timestamp lastUpdateDate;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "gift_certificates_tags",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @ToString.Exclude
    private List<TagEntity> tags = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GiftCertificateEntity that = (GiftCertificateEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
