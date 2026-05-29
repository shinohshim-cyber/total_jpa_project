package com.my.total_jpa_back.orders.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.my.total_jpa_back.common.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderMultiSearchRequest {
    @JsonProperty("price")
    private Integer price;
    @JsonProperty("status")
    private OrderStatus status;
    @JsonProperty("keyword")
    private String keyword;
}
