package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.envers.Audited;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * Class representation of order data in database
 * @author yegorchevardin
 * @version 0.0.1
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "cost", nullable = false)
    private Float cost;
    @Column(name = "purchased_at")
    private Timestamp purchasedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "orders_gift_certificates",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_id")
    )
    @ToString.Exclude
    private List<GiftCertificateEntity> giftCertificates;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderEntity that = (OrderEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
