package org.mintyn.inventory.payload.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductResponse {
    private Long id;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private Boolean disabled;
    private Integer productQuantity;
}
