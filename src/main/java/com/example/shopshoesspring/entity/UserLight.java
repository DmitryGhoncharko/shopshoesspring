package com.example.shopshoesspring.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.sql.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "user_light")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserLight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_light_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "light_id", nullable = false)
    private Light light;

    @Column(name = "user_light_completed", nullable = false)
    private Boolean userLightCompleted = false;

    @Column(name = "phone", nullable = false, length = 500)
    private String phone;

    @Column(name = "message", nullable = false, length = 500)
    private String message;
    @Column(name = "_date",nullable = false)
    private Date date;
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        UserLight userLight = (UserLight) o;
        return getId() != null && Objects.equals(getId(), userLight.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}