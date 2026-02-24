package com.cristianexercise.orders_service.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsRequest {

    private Long id;
    private String sku;
    private Double price;
    private Long quantity;
}
