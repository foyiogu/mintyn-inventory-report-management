package org.mintyn.inventory.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.mintyn.app.configuration.exception.ApiRequestUnauthorizedException;
import org.mintyn.app.configuration.exception.ApiResourceNotFoundException;
import org.mintyn.app.configuration.config.OrderResponse;
import org.mintyn.inventory.model.ProductOrder;
import org.mintyn.inventory.model.Product;
import org.mintyn.inventory.payload.request.NewOrderRequest;
import org.mintyn.inventory.repository.OrderRepository;
import org.mintyn.inventory.repository.ProductRepository;
import org.mintyn.inventory.service.OrderService;
import org.mintyn.inventory.kafka.report.ReportSenderService;
import org.mintyn.inventory.utility.OrderMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final OrderMapper orderMapper;
    private final ReportSenderService reportSenderService;


    @Override
    public List<OrderResponse> createOrder(List<NewOrderRequest> orderRequest) {

        List<OrderResponse> orderResponses = new ArrayList<>();

        for (NewOrderRequest newOrderRequest : orderRequest) {
            Product product = productRepository.findById(newOrderRequest.getProductId())
                    .orElseThrow(() ->new ApiResourceNotFoundException("Product does not exist"));
            if (product.getDisabled()){
                throw new ApiResourceNotFoundException("This product is out of stock");
            }
            if (product.getQuantity() < newOrderRequest.getOrderQuantity()){
                throw new ApiRequestUnauthorizedException("Quantity of order is higher than " + product.getQuantity());
            }

            ProductOrder order = orderMapper.mapToOrder(newOrderRequest, product);
            product.setQuantity(product.getQuantity() - order.getOrderQuantity());

            product = productRepository.saveAndFlush(product);
            if (product.getQuantity() ==0){
                product.setDisabled(Boolean.TRUE);
            }
            order = orderRepository.save(order);

            OrderResponse orderResponse = orderMapper.mapToOrderResponse(order);
            orderResponses.add(orderResponse);

            reportSenderService.sendOrderReport(orderResponse);
        }

        return orderResponses;
    }

    @Override
    public List<OrderResponse> getAllOrders() {
            List<OrderResponse> orderResponses = new ArrayList<>();
            List<ProductOrder> orders = orderRepository.findAll();

        if (orders.isEmpty()){
            throw new ApiResourceNotFoundException("Could not retrieve orders");
        }
            for (ProductOrder order : orders) {
                orderResponses.add(orderMapper.mapToOrderResponse(order));
            }
            return orderResponses;

    }

    @Override
    public OrderResponse getOrder(Long id) {
        ProductOrder order = orderRepository.findById(id)
                .orElseThrow(() ->new ApiResourceNotFoundException("Order does not exist"));
        return orderMapper.mapToOrderResponse(order);
    }
}
