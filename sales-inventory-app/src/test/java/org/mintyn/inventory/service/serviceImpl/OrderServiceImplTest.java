package org.mintyn.inventory.service.serviceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mintyn.app.configuration.config.OrderResponse;
import org.mintyn.app.configuration.exception.ApiRequestUnauthorizedException;
import org.mintyn.app.configuration.exception.ApiResourceNotFoundException;
import org.mintyn.inventory.kafka.report.ReportSenderService;
import org.mintyn.inventory.model.Product;
import org.mintyn.inventory.model.ProductOrder;
import org.mintyn.inventory.payload.request.NewOrderRequest;
import org.mintyn.inventory.repository.OrderRepository;
import org.mintyn.inventory.repository.ProductRepository;
import org.mintyn.inventory.utility.OrderMapper;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository mockOrderRepository;
    @Mock
    private ProductRepository mockProductRepository;
    @Mock
    private OrderMapper mockOrderMapper;
    @Mock
    private ReportSenderService mockReportSenderService;

    private OrderServiceImpl orderServiceImplUnderTest;

    OrderResponse orderResponse = new OrderResponse();

    Product product = new Product();

    @BeforeEach
    void setUp() {
        orderServiceImplUnderTest = new OrderServiceImpl(mockOrderRepository, mockProductRepository, mockOrderMapper,
                mockReportSenderService);
        orderResponse.setId(0L);
        orderResponse.setOrderCreatedDate(LocalDate.of(2020, 1, 1));
        orderResponse.setCustomerName("Frank");
        orderResponse.setCustomerPhoneNumber("0813");
        orderResponse.setProductName("productName");
        orderResponse.setOrderQuantity(1);
        orderResponse.setProductPrice(new BigDecimal("10.00"));
        orderResponse.setTotalProductPrice(new BigDecimal("10.00"));

        product = new Product("productName", new BigDecimal("10.00"), "description", false, 10);
        product.setId(0L);
    }

    @Test
    void testCreateOrder() {
        // Setup
        final NewOrderRequest newOrderRequest = new NewOrderRequest();
        newOrderRequest.setCustomerName("Frank");
        newOrderRequest.setCustomerPhoneNumber("0813");
        newOrderRequest.setProductName("productName");
        newOrderRequest.setOrderQuantity(1);
        newOrderRequest.setProductId(0L);
        final List<NewOrderRequest> orderRequest = List.of(newOrderRequest);

        final List<OrderResponse> expectedResult = List.of(orderResponse);

        // Configure ProductRepository.findById(...).
        when(mockProductRepository.findById(0L)).thenReturn(Optional.of(product));

        // Configure OrderMapper.mapToOrder(...).
        final ProductOrder productOrder = new ProductOrder("Frank", "0813", "productName", 1,
                new BigDecimal("10.00"), new BigDecimal("10.00"), product);
        when(mockOrderMapper.mapToOrder(any(),any())).thenReturn(productOrder);

        // Configure ProductRepository.saveAndFlush(...).
        when(mockProductRepository.saveAndFlush(any())).thenReturn(product);

        // Configure OrderRepository.save(...).
        when(mockOrderRepository.save(any(ProductOrder.class))).thenReturn(productOrder);

        // Configure OrderMapper.mapToOrderResponse(...).
        when(mockOrderMapper.mapToOrderResponse(any(ProductOrder.class))).thenReturn(orderResponse);

        // Run the test
        final List<OrderResponse> result = orderServiceImplUnderTest.createOrder(orderRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockReportSenderService, atLeastOnce()).sendOrderReport(orderResponse);
    }

    @Test
    void testShouldThrowIfOutOfStock() {

        product.setDisabled(true);
        product.setQuantity(0);
        final NewOrderRequest newOrderRequest = new NewOrderRequest();
        newOrderRequest.setCustomerName("Frank");
        newOrderRequest.setCustomerPhoneNumber("0813");
        newOrderRequest.setProductName("productName");
        newOrderRequest.setOrderQuantity(1);
        newOrderRequest.setProductId(0L);

        when(mockProductRepository.findById(any())).thenReturn(Optional.of(product));
        final List<NewOrderRequest> orderRequests = List.of(newOrderRequest);

        assertThatThrownBy(() -> orderServiceImplUnderTest.createOrder(orderRequests))
                .isInstanceOf(ApiResourceNotFoundException.class)
                .hasMessageContaining("This product is out of stock");
    }

    @Test
    void testShouldThrowIfOrderQuantityGreaterThanProductQuantity() {

        product.setDisabled(false);
        product.setQuantity(2);
        final NewOrderRequest newOrderRequest = new NewOrderRequest();
        newOrderRequest.setCustomerName("Frank");
        newOrderRequest.setCustomerPhoneNumber("0813");
        newOrderRequest.setProductName("productName");
        newOrderRequest.setOrderQuantity(3);
        newOrderRequest.setProductId(0L);

        when(mockProductRepository.findById(any())).thenReturn(Optional.of(product));
        final List<NewOrderRequest> orderRequests = List.of(newOrderRequest);

        assertThatThrownBy(() -> orderServiceImplUnderTest.createOrder(orderRequests))
                .isInstanceOf(ApiRequestUnauthorizedException.class)
                .hasMessageContaining("Quantity of order is higher than 2");
    }

    @Test
    void testGetAllOrders() {
        // Setup
        final List<OrderResponse> expectedResult = List.of(orderResponse);

        // Configure OrderRepository.findAll(...).
        final ProductOrder productOrder = new ProductOrder("Frank", "0813", "productName", 1,
                new BigDecimal("10.00"), new BigDecimal("10.00"), product);
        final List<ProductOrder> productOrders = List.of(productOrder);
        when(mockOrderRepository.findAll()).thenReturn(productOrders);

        // Configure OrderMapper.mapToOrderResponse(...).
        when(mockOrderMapper.mapToOrderResponse(any())).thenReturn(orderResponse);

        // Run the test
        final List<OrderResponse> result = orderServiceImplUnderTest.getAllOrders();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldThrowIfOrderEmpty() {
        when(mockOrderRepository.findAll()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> orderServiceImplUnderTest.getAllOrders())
                .isInstanceOf(ApiResourceNotFoundException.class)
                .hasMessageContaining("Could not retrieve orders");

    }

    @Test
    void testGetOrder() {

        // Configure OrderRepository.findById(...).
        final ProductOrder productOrder = new ProductOrder("Frank", "0813", "productName", 1,
                new BigDecimal("10.00"), new BigDecimal("10.00"), product);
        when(mockOrderRepository.findById(0L)).thenReturn(Optional.of(productOrder));

        // Configure OrderMapper.mapToOrderResponse(...).
        when(mockOrderMapper.mapToOrderResponse(any(ProductOrder.class))).thenReturn(orderResponse);

        // Run the test
        final OrderResponse result = orderServiceImplUnderTest.getOrder(0L);

        // Verify the results
        assertThat(result).isEqualTo(orderResponse);
    }

}
