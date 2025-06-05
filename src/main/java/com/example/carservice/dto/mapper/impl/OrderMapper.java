package com.example.carservice.dto.mapper.impl;

import com.example.carservice.dto.mapper.RequestDtoMapper;
import com.example.carservice.dto.mapper.ResponseDtoMapper;
import com.example.carservice.dto.request.OrderRequestDto;
import com.example.carservice.dto.response.OrderResponseDto;
import com.example.carservice.model.Goods;
import com.example.carservice.model.Order;
import com.example.carservice.model.ServiceModel;
import com.example.carservice.service.CarService;
import com.example.carservice.service.GoodsService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper implements RequestDtoMapper<OrderRequestDto, Order>,
        ResponseDtoMapper<OrderResponseDto, Order> {
    private final GoodsService goodsService;
    private final CarService carService;

    public OrderMapper(GoodsService goodsService,
                       CarService carService) {
        this.goodsService = goodsService;
        this.carService = carService;
    }

    @Override
    public Order mapToModel(OrderRequestDto dto) {
        Order order = new Order();
        order.setCar(carService.get(dto.getCarId()));
        order.setDescription(dto.getDescription());
        order.setServices(List.of());
        order.setGoods(dto.getGoodsIds()
                .stream()
                .map(goodsService::get)
                .toList());
        order.setStatus(dto.getStatus());
        return order;
    }

    @Override
    public OrderResponseDto mapToDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setCarId(order.getCar().getId());
        dto.setDescription(order.getDescription());
        dto.setOrderTime(order.getOrderTime());
        dto.setServiceIds(order.getServices()
                .stream()
                .map(ServiceModel::getId)
                .toList());
        dto.setGoodsIds(order.getGoods()
                .stream()
                .map(Goods::getId)
                .toList());
        dto.setStatus(order.getStatus());
        dto.setPrice(order.getPrice());
        dto.setCompletionTime(order.getCompletionTime());
        return dto;
    }
}
