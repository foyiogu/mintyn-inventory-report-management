package org.mintyn.inventory.service;

import org.mintyn.inventory.payload.request.CreateProductRequest;
import org.mintyn.inventory.payload.request.UpdateProductRequest;
import org.mintyn.inventory.payload.response.ProductResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest productRequest);

    ProductResponse updateProduct(Long id, UpdateProductRequest productRequest);

    List<ProductResponse> getAllProducts();

    ProductResponse getProduct(Long productId);

    ProductResponse updateProductPrice(Long id, BigDecimal productPrice);
}
