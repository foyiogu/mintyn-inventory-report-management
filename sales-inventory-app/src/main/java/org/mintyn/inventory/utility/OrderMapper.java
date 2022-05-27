package org.mintyn.inventory.utility;

import org.mintyn.inventory.model.ProductOrder;
import org.mintyn.inventory.model.Product;
import org.mintyn.inventory.payload.request.NewOrderRequest;
import org.mintyn.inventory.payload.response.OrderResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderMapper {

    public ProductOrder mapToOrder(NewOrderRequest orderRequest, Product product){
        ProductOrder order = new ProductOrder();
        order.setCustomerName(orderRequest.getCustomerName().toLowerCase());
        order.setCustomerPhoneNumber(orderRequest.getCustomerPhoneNumber());
        order.setProductName(orderRequest.getProductName().toLowerCase());
        order.setOrderQuantity(orderRequest.getOrderQuantity());
        order.setProductPrice(product.getPrice());
        order.setTotalProductPrice(product.getPrice().multiply(BigDecimal.valueOf(orderRequest.getOrderQuantity())));
        order.setProduct(product);

        return order;
    }

    public OrderResponse mapToOrderResponse(ProductOrder order){
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setCustomerName(order.getCustomerName());
        response.setCustomerPhoneNumber(order.getCustomerPhoneNumber());
        response.setProductName(order.getProductName());
        response.setCreatedAt(order.getCreatedAt());
        response.setOrderQuantity(order.getOrderQuantity());
        response.setProductPrice(order.getProductPrice());
        response.setTotalProductPrice(order.getTotalProductPrice());

        return response;
    }
}
