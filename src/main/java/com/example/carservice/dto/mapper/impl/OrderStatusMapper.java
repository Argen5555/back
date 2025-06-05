package com.example.carservice.dto.mapper.impl;

import com.example.carservice.dto.mapper.RequestDtoMapper;
import com.example.carservice.dto.request.StatusRequestDto;
import com.example.carservice.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusMapper implements RequestDtoMapper<StatusRequestDto, Order.OrderStatus> {
    @Override
    public Order.OrderStatus mapToModel(StatusRequestDto dto) {
        return Order.OrderStatus.valueOf(dto.getName());
    }
}
