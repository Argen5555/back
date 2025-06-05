package com.example.carservice.dto.mapper.impl;

import com.example.carservice.dto.mapper.RequestDtoMapper;
import com.example.carservice.dto.mapper.ResponseDtoMapper;
import com.example.carservice.dto.request.OwnerRequestDto;
import com.example.carservice.dto.response.OwnerResponseDto;
import com.example.carservice.model.Car;
import com.example.carservice.model.Order;
import com.example.carservice.model.Owner;
import com.example.carservice.service.CarService;
import com.example.carservice.service.OrderService;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapper implements RequestDtoMapper<OwnerRequestDto, Owner>,
        ResponseDtoMapper<OwnerResponseDto, Owner> {
    private final CarService carService;
    private final OrderService orderService;

    public OwnerMapper(CarService carService, OrderService orderService) {
        this.carService = carService;
        this.orderService = orderService;
    }

    @Override
    public Owner mapToModel(OwnerRequestDto dto) {
        Owner owner = new Owner();
        owner.setCars(dto.getCarIds()
                .stream()
                .map(carService::get)
                .toList());
        owner.setOrders(dto.getOrderIds()
                .stream()
                .map(orderService::get)
                .toList());
        return owner;
    }

    @Override
    public OwnerResponseDto mapToDto(Owner owner) {
        OwnerResponseDto dto = new OwnerResponseDto();
        dto.setId(owner.getId());
        dto.setCarIds(owner.getCars()
                .stream()
                .map(Car::getId)
                .toList());
        dto.setOrderIds(owner.getOrders()
                .stream()
                .map(Order::getId)
                .toList());
        return dto;
    }
}
