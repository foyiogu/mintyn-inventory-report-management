package org.mintyn.order.report.payload;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreatedOrder {

    private LocalDateTime orderCreatedDate;

    private String customerName;

    private String customerPhoneNumber;

    private String productName;

    private Integer orderQuantity;

    private BigDecimal productPrice;

    private BigDecimal totalProductPrice;
}
