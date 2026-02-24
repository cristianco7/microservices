package com.cristianexercise.orders_service.services;

import com.cristianexercise.orders_service.model.dtos.BaseResponse;
import com.cristianexercise.orders_service.model.dtos.OrderItemsRequest;
import com.cristianexercise.orders_service.model.dtos.OrderRequest;
import com.cristianexercise.orders_service.model.entities.Order;
import com.cristianexercise.orders_service.model.entities.OrderItems;
import com.cristianexercise.orders_service.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public OrderService(OrderRepository orderRepository, WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
    }

    public void placeOrder(OrderRequest orderRequest) {

        BaseResponse result = this.webClientBuilder.build()
                .post()
                .uri("http://localhost:8083/api/inventory/in-stock")
                .bodyValue(orderRequest.getOrderItems())
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();

        if (result != null && !result.hasErrors()) {

            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderItemsList(orderRequest.getOrderItems().stream()
                    .map(orderItemsRequest -> mapOrderItemRequestToOrderItem(orderItemsRequest, order))
                    .toList());
            this.orderRepository.save(order);
        } else {
            throw  new IllegalArgumentException("Some of the products are not in the stock");
        }
    }

    private OrderItems mapOrderItemRequestToOrderItem(OrderItemsRequest orderItemsRequest, Order order) {
        return OrderItems.builder()
                .id(orderItemsRequest.getId())
                .sku(orderItemsRequest.getSku())
                .price(orderItemsRequest.getPrice())
                .quantity(orderItemsRequest.getQuantity())
                .order(order)
                .build();
    }
}
