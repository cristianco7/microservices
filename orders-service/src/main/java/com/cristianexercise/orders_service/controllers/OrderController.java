package com.cristianexercise.orders_service.controllers;

import com.cristianexercise.orders_service.model.dtos.OrderRequest;
import com.cristianexercise.orders_service.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        this.orderService.placeOrder(orderRequest);
        return "Order Placed succefully";
    }

}
