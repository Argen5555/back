package com.example.carservice.dto.response;

import com.example.carservice.model.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private Long carId;
    private String description;
    private LocalDateTime orderTime;
    private List<Long> serviceIds;
    private List<Long> goodsIds;
    private Order.OrderStatus status;
    private BigDecimal price;
    private LocalDateTime completionTime;
}
