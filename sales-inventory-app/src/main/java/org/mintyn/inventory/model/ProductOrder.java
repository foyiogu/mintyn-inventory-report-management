package org.mintyn.inventory.model;

import lombok.*;
import org.mintyn.inventory.model.audit.DateAudit;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "product_order")
public class ProductOrder extends DateAudit {

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_phone_number")
    private String customerPhoneNumber;

    @Column(name = "product_order_name")
    private String productName;

    @Column(name = "order_quantity")
    private Integer orderQuantity;

    @Column(name = "product_order_price")
    private BigDecimal productPrice;

    @Column(name = "total_product_price")
    private BigDecimal totalProductPrice;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

}
