package org.mintyn.inventory.controller;

import lombok.RequiredArgsConstructor;
import org.mintyn.inventory.payload.request.NewOrderRequest;
import org.mintyn.inventory.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@Valid @RequestBody List<NewOrderRequest> orderRequest){
        return new ResponseEntity<>(orderService.createOrder(orderRequest), HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrders(){
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrder(@PathVariable("id") Long id){
        return new ResponseEntity<>(orderService.getOrder(id), HttpStatus.OK);
    }
}
