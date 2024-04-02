package com.example.shopshoesspring.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "_product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;

    @Column(name = "product_name", nullable = false, length = 500)
    private String productName;

    @Column(name = "product_manufacture_country", nullable = false, length = 500)
    private String productManufactureCountry;

    @Column(name = "product_category", nullable = false, length = 500)
    private String productCategory;

    @Column(name = "product_color", nullable = false, length = 1000)
    private String productColor;

    @Column(name = "product_material", nullable = false, length = 1000)
    private String productMaterial;

    @Column(name = "product_type", nullable = false, length = 100)
    private String productType;

    @Column(name = "product_price", nullable = false)
    private Double productPrice;

    @Column(name = "product_default_price", nullable = false)
    private Double productDefaultPrice;

    @Column(name = "product_count", nullable = false)
    private Long productCount;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Product product = (Product) o;
        return getId() != null && Objects.equals(getId(), product.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}