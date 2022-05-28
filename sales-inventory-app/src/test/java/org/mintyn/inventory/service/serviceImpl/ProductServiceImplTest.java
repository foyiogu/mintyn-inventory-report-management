package org.mintyn.inventory.service.serviceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mintyn.app.configuration.exception.ApiBadRequestException;
import org.mintyn.app.configuration.exception.ApiRequestUnauthorizedException;
import org.mintyn.app.configuration.exception.ApiResourceNotFoundException;
import org.mintyn.inventory.model.Product;
import org.mintyn.inventory.payload.request.CreateProductRequest;
import org.mintyn.inventory.payload.request.UpdateProductRequest;
import org.mintyn.inventory.payload.response.ProductResponse;
import org.mintyn.inventory.repository.ProductRepository;
import org.mintyn.inventory.utility.ProductMapper;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL_INT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository mockProductRepository;
    @Mock
    private ProductMapper mockProductMapper;

    private ProductServiceImpl productServiceImplUnderTest;

    CreateProductRequest productRequest = new CreateProductRequest();
    ProductResponse expectedResult = new ProductResponse();

    Product product1 = new Product();

    @BeforeEach
    void setUp() {
        productServiceImplUnderTest = new ProductServiceImpl(mockProductRepository, mockProductMapper);

        expectedResult.setId(0L);
        expectedResult.setProductName("productName");
        expectedResult.setProductDescription("description");
        expectedResult.setProductPrice(new BigDecimal("10.00"));
        expectedResult.setDisabled(false);
        expectedResult.setProductQuantity(10);

        productRequest.setProductName("productName");
        productRequest.setProductDescription("description");
        productRequest.setProductPrice(new BigDecimal("10.00"));
        productRequest.setDisabled(true);
        productRequest.setProductQuantity(10);

        product1 = new Product("productName", new BigDecimal("10.00"), "description", false, 10);
        product1.setId(0L);
    }

    @Test
    void testCreateProduct() {
        // Configure ProductRepository.findByProductName(...).

        when(mockProductRepository.findByProductName(any())).thenReturn(Optional.empty());

        // Configure ProductMapper.mapToProduct(...).
        when(mockProductMapper.mapToProduct(any())).thenReturn(product1);

        when(mockProductRepository.save(any(Product.class))).thenReturn(product1);

        // Configure ProductMapper.mapToProductResponse(...).

        when(mockProductMapper.mapToProductResponse(any())).thenReturn(expectedResult);

        // Run the test
        final ProductResponse result = productServiceImplUnderTest.createProduct(productRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockProductRepository, atLeastOnce()).save(product1);
    }

    @Test
    void testShouldThrowIfProductNameExists() {
        when(mockProductRepository.findByProductName(any())).thenReturn(Optional.of(product1));
        assertThatThrownBy(() -> productServiceImplUnderTest.createProduct(productRequest))
                .isInstanceOf(ApiBadRequestException.class)
                .hasMessageContaining("Product with name already exists");

        verify(mockProductRepository, never()).save(any());
    }

    @Test
    void testShouldThrowIfProductPriceIsZero() {
        productRequest.setProductPrice(BigDecimal.ZERO);
        assertThatThrownBy(() -> productServiceImplUnderTest.createProduct(productRequest))
                .isInstanceOf(ApiRequestUnauthorizedException.class)
                .hasMessageContaining("The price of product must be greater than zer0");

    }

    @Test
    void testUpdateProduct() {
        // Setup
        final UpdateProductRequest productRequest = new UpdateProductRequest();
        productRequest.setId(0L);
        productRequest.setProductName("productNamee");
        productRequest.setProductDescription("productDescription");
        productRequest.setProductPrice(new BigDecimal("20.00"));
        productRequest.setDisabled(false);
        productRequest.setProductQuantity(30);


        // Configure ProductRepository.findById(...).
        when(mockProductRepository.findById(0L)).thenReturn(Optional.of(product1));

        // Configure ProductRepository.save(...).
        final Product product2 = new Product("productName2", new BigDecimal("20.00"), "productDescription", false, 40);
        product2.setId(0L);
        when(mockProductRepository.save(any())).thenReturn(product2);

        // Configure ProductMapper.mapToProductResponse(...).
        final ProductResponse productResponse = new ProductResponse();
        productResponse.setId(0L);
        productResponse.setProductName("productNamee");
        productResponse.setProductDescription("productDescription");
        productResponse.setProductPrice(new BigDecimal("20.00"));
        productResponse.setDisabled(false);
        productResponse.setProductQuantity(40);
        when(mockProductMapper.mapToProductResponse(any())).thenReturn(productResponse);

        // Run the test
        final ProductResponse result = productServiceImplUnderTest.updateProduct(0L, productRequest);

        // Verify the results
        assertThat(result).isEqualTo(productResponse);
    }


    @Test
    void testGetAllProducts() {
        // Setup

        List<ProductResponse> expectedResults = List.of(expectedResult);

        // Configure ProductRepository.findAll(...).
        final List<Product> products = List.of(product1);
        when(mockProductRepository.findAll()).thenReturn(products);

        // Configure ProductMapper.mapToProductResponse(...).
        when(mockProductMapper.mapToProductResponse(any())).thenReturn(expectedResult);

        // Run the test
        final List<ProductResponse> result = productServiceImplUnderTest.getAllProducts();

        // Verify the results
        assertThat(result).isEqualTo(expectedResults);
    }

    @Test
    void testGetAllProducts_ProductRepositoryReturnsNoItems() {
        when(mockProductRepository.findAll()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> productServiceImplUnderTest.getAllProducts())
                .isInstanceOf(ApiResourceNotFoundException.class)
                .hasMessageContaining("Could not retrieve products");
    }

    @Test
    void testGetProduct() {

        // Configure ProductRepository.findById(...).
        when(mockProductRepository.findById(any())).thenReturn(Optional.of(product1));

        // Configure ProductMapper.mapToProductResponse(...).
        when(mockProductMapper.mapToProductResponse(any())).thenReturn(expectedResult);

        // Run the test
        final ProductResponse result = productServiceImplUnderTest.getProduct(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }


}
