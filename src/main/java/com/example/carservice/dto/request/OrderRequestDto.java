package com.example.carservice.dto.request;

import com.example.carservice.model.Order;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    private Long carId;
    private String description;
    private List<Long> goodsIds;
    private Order.OrderStatus status;
}
