package org.mintyn.inventory.payload.request;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
public class CreateProductRequest {

    private String productName;

    private String productDescription;

    private BigDecimal productPrice;

    private Boolean disabled = true;

    private Integer productQuantity;
}
