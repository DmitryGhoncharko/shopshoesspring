package com.example.shopshoesspring.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "_light")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Light {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "light_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "light_type_id", nullable = false)
    private LightType lightType;

    @Column(name = "light_image_link", nullable = false, length = 600)
    private String lightImageLink;

    @Column(name = "light_name", nullable = false, length = 600)
    private String lightName;

    @Column(name = "light_power", nullable = false, length = 500)
    private String lightPower;

    @Column(name = "light_supply_voltage", nullable = false, length = 500)
    private String lightSupplyVoltage;

    @Column(name = "light_color_temperature", nullable = false, length = 500)
    private String lightColorTemperature;

    @Column(name = "light_degree_of_protection", nullable = false, length = 500)
    private String lightDegreeOfProtection;

    @Column(name = "light_temperature_regime", nullable = false, length = 500)
    private String lightTemperatureRegime;

    @Column(name = "light_overal_dimenssions", nullable = false, length = 500)
    private String lightOveralDimenssions;

    @Column(name = "light_mass", nullable = false, length = 500)
    private String lightMass;

    @Column(name = "light_corpus_material", nullable = false, length = 500)
    private String lightCorpusMaterial;

    @OneToMany(mappedBy = "light")
    private Set<UserLight> userLights = new LinkedHashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Light light = (Light) o;
        return getId() != null && Objects.equals(getId(), light.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "lightType = " + lightType + ", " +
                "lightImageLink = " + lightImageLink + ", " +
                "lightName = " + lightName + ", " +
                "lightPower = " + lightPower + ", " +
                "lightSupplyVoltage = " + lightSupplyVoltage + ", " +
                "lightColorTemperature = " + lightColorTemperature + ", " +
                "lightDegreeOfProtection = " + lightDegreeOfProtection + ", " +
                "lightTemperatureRegime = " + lightTemperatureRegime + ", " +
                "lightOveralDimenssions = " + lightOveralDimenssions + ", " +
                "lightMass = " + lightMass + ", " +
                "lightCorpusMaterial = " + lightCorpusMaterial + ")";
    }
}