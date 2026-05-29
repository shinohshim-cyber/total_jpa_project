package com.my.total_jpa_back.orders.dto;

import com.my.total_jpa_back.common.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class OrderResponse {
    private Long orderId;
    private String productName;
    private Integer price;
    private String userName;
    private OrderStatus status;

    public OrderResponse(Long orderId, String productName, Integer price, String userName, OrderStatus status) {
        this.orderId = orderId;
        this.productName = productName;
        this.price = price;
        this.userName = userName;
        this.status = status;
    }
}
