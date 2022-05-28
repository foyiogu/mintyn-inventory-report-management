package org.mintyn.inventory.payload.request;

import lombok.Data;


@Data
public class NewOrderRequest {

    private String customerName;

    private String customerPhoneNumber;

    private String productName;

    private Integer orderQuantity;


    private Long productId;

}
