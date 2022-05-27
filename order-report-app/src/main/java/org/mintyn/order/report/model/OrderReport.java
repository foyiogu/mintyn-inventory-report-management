package org.mintyn.order.report.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "order_report")
public class OrderReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "order_created_date")
    private LocalDate orderCreatedDate;

    private String customerName;

    private String customerPhoneNumber;

    private String productName;

    private Integer orderQuantity;

    private BigDecimal productPrice;

    private BigDecimal totalProductPrice;
}
