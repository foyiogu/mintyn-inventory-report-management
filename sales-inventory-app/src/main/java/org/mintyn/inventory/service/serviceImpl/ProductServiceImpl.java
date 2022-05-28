package org.mintyn.inventory.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.mintyn.app.configuration.exception.ApiBadRequestException;
import org.mintyn.app.configuration.exception.ApiRequestUnauthorizedException;
import org.mintyn.app.configuration.exception.ApiResourceNotFoundException;
import org.mintyn.inventory.model.Product;
import org.mintyn.inventory.payload.request.CreateProductRequest;
import org.mintyn.inventory.payload.request.UpdateProductRequest;
import org.mintyn.inventory.payload.response.ProductResponse;
import org.mintyn.inventory.repository.ProductRepository;
import org.mintyn.inventory.service.ProductService;
import org.mintyn.inventory.utility.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;
    @Override
    public ProductResponse createProduct(CreateProductRequest productRequest) {

        if (productRepository.findByProductName(productRequest.getProductName().toLowerCase()).isPresent()){
            throw new ApiBadRequestException("Product with name already exists");
        }
        if (productRequest.getProductPrice().equals(BigDecimal.ZERO)){
            throw new ApiRequestUnauthorizedException("The price of product must be greater than zer0");
        }
        if (productRequest.getProductQuantity() == 0 && !productRequest.getDisabled()){
            throw new ApiRequestUnauthorizedException("The price of product must be greater than zer0 for disabled to be false");
        }
        Product product = productMapper.mapToProduct(productRequest);
        if (productRequest.getProductQuantity() > 0){
            product.setDisabled(Boolean.FALSE);
        }
        product = productRepository.save(product);

        return productMapper.mapToProductResponse(product);
    }

    @Override
    public ProductResponse updateProduct(Long id, UpdateProductRequest productRequest) {
        if (ObjectUtils.isEmpty(productRequest)){
            throw new ApiBadRequestException("An empty payload has been sent");
        }

        if (!id.equals(productRequest.getId())){
            throw new ApiBadRequestException("This product does not exist");
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() ->new ApiResourceNotFoundException("Product does not exist"));

        if (product.getProductName().equalsIgnoreCase(productRequest.getProductName())){
            throw new ApiBadRequestException("Can not update product with the same name as before");
        }


        productMapper.updateAndMapToProduct(product, productRequest);
        product = productRepository.save(product);

        return productMapper.mapToProductResponse(product);

    }

    @Override
    public List<ProductResponse> getAllProducts() {

        List<ProductResponse> productResponses = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()){
            throw new ApiResourceNotFoundException("Could not retrieve products");
        }
        for (Product product : products) {
            productResponses.add(productMapper.mapToProductResponse(product));
        }
        return productResponses;

    }

    @Override
    public ProductResponse getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->new ApiResourceNotFoundException("Product does not exist"));
        return productMapper.mapToProductResponse(product);
    }

    @Override
    public ProductResponse updateProductPrice(Long id, BigDecimal productPrice) {
        return null;
    }
}
