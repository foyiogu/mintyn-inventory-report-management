package org.mintyn.inventory.utility;

import org.mintyn.inventory.model.Product;
import org.mintyn.inventory.payload.request.CreateProductRequest;
import org.mintyn.inventory.payload.request.UpdateProductRequest;
import org.mintyn.inventory.payload.response.ProductResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

@Component
public class ProductMapper {

    public Product mapToProduct(CreateProductRequest productRequest){
        Product product = new Product();
        product.setProductName(productRequest.getProductName().toLowerCase());
        product.setDescription(productRequest.getProductDescription());
        product.setPrice(productRequest.getProductPrice());
        product.setDisabled(productRequest.getDisabled());
        product.setQuantity(productRequest.getProductQuantity());

        return product;
    }

    public ProductResponse mapToProductResponse(Product product){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setProductName(product.getProductName());
        productResponse.setProductDescription(product.getDescription());
        productResponse.setProductPrice(product.getPrice());
        productResponse.setProductQuantity(product.getQuantity());
        productResponse.setDisabled(product.getDisabled());

        return productResponse;
    }

    public void updateAndMapToProduct(Product product, UpdateProductRequest productRequest){

        if(!ObjectUtils.isEmpty(productRequest.getProductName())){
            product.setProductName(productRequest.getProductName().toLowerCase());
        }
        if(!ObjectUtils.isEmpty(productRequest.getProductDescription())){
            product.setDescription(productRequest.getProductDescription().toLowerCase());
        }
        if(!ObjectUtils.isEmpty(productRequest.getProductPrice())){
            product.setPrice(productRequest.getProductPrice());
        }
        if(!ObjectUtils.isEmpty(productRequest.getProductQuantity())){
            product.setQuantity(product.getQuantity() + productRequest.getProductQuantity());
        }
        if ( product.getQuantity() != 0 || productRequest.getProductQuantity() !=0){
            product.setDisabled(Boolean.FALSE);
        }
    }

    public Product updateProductPrice(Product product, BigDecimal price){
        product.setPrice(price);
        return product;
    }
}
