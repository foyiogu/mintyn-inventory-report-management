package org.mintyn.inventory.payload.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {

    private Long id;

    private String productName;

    private String productDescription;

    private BigDecimal productPrice;

    private Boolean disabled;

    private Integer productQuantity;
}
