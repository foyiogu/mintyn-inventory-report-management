package org.mintyn.app.configuration.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class OrderResponse {

    private Long id;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate orderCreatedDate;

    private String customerName;

    private String customerPhoneNumber;

    private String productName;

    private Integer orderQuantity;

    private BigDecimal productPrice;

    private BigDecimal totalProductPrice;
}
