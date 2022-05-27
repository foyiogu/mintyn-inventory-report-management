package org.mintyn.inventory.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.mintyn.inventory.payload.request.CreateProductRequest;
import org.mintyn.inventory.payload.request.UpdateProductRequest;
import org.mintyn.inventory.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "This is a bad request, please follow the API documentation for the proper request format."),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "The server is down, please make sure that the Application is running")
})
@RequestMapping("api/v1/inventory")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add-product")
    public ResponseEntity<?> createProduct(@Valid @RequestBody CreateProductRequest productRequest){
        return new ResponseEntity<>(productService.createProduct(productRequest), HttpStatus.CREATED);
    }

    @PutMapping("/update-product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @Valid @RequestBody UpdateProductRequest productRequest){
        return new ResponseEntity<>(productService.updateProduct(id, productRequest), HttpStatus.ACCEPTED);
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long id){
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

}
