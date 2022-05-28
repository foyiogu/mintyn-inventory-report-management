package org.mintyn.inventory.service;

import org.mintyn.app.configuration.config.OrderResponse;
import org.mintyn.inventory.payload.request.NewOrderRequest;
//import org.mintyn.inventory.payload.response.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> createOrder(List<NewOrderRequest> orderRequest);

    List<OrderResponse> getAllOrders();

    OrderResponse getOrder(Long id);
}
