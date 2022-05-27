package org.mintyn.inventory.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.mintyn.inventory.model.audit.DateAudit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "product")
public class Product extends DateAudit {

    @Column(name = "product_name",unique = true)
    private String productName;

    @Column(name = "product_price",nullable = false)
    private BigDecimal price;

    @Column(name = "product_description", columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Boolean disabled = true;

    @Column(name = "product_quantity")
    private Integer quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return getId() != null && Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
