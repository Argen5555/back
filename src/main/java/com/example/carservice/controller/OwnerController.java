package com.example.carservice.controller;

import com.example.carservice.dto.mapper.RequestDtoMapper;
import com.example.carservice.dto.mapper.ResponseDtoMapper;
import com.example.carservice.dto.request.OwnerRequestDto;
import com.example.carservice.dto.response.OrderResponseDto;
import com.example.carservice.dto.response.OwnerResponseDto;
import com.example.carservice.model.Order;
import com.example.carservice.model.Owner;
import com.example.carservice.service.OwnerService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owners")
public class OwnerController {
    private final OwnerService ownerService;
    private final RequestDtoMapper<OwnerRequestDto, Owner> requestDtoMapper;
    private final ResponseDtoMapper<OwnerResponseDto, Owner> responseDtoMapper;
    private final ResponseDtoMapper<OrderResponseDto, Order> orderResponseDtoMapper;

    public OwnerController(OwnerService ownerService,
                           RequestDtoMapper<OwnerRequestDto, Owner> requestDtoMapper,
                           ResponseDtoMapper<OwnerResponseDto, Owner> responseDtoMapper,
                           ResponseDtoMapper<OrderResponseDto, Order> orderResponseDtoMapper) {
        this.ownerService = ownerService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
        this.orderResponseDtoMapper = orderResponseDtoMapper;
    }

    @GetMapping
    @ApiOperation("Get all owners")
    public List<OwnerResponseDto> getAll() {
        return ownerService.getAll()
                .stream()
                .map(responseDtoMapper::mapToDto)
                .toList();
    }

    @GetMapping("/{id}")
    @ApiOperation("Get owner by id")
    public OwnerResponseDto get(@PathVariable Long id) {
        Owner owner = ownerService.get(id);
        return responseDtoMapper.mapToDto(owner);
    }

    @PostMapping
    @ApiOperation("Add a new owner")
    public OwnerResponseDto create() {
        Owner owner = newOwner();
        return responseDtoMapper.mapToDto(ownerService.add(owner));
    }

    @PostMapping("/{id}")
    @ApiOperation("Update owner by id")
    public OwnerResponseDto update(@PathVariable Long id,
                                   @RequestBody OwnerRequestDto requestDto) {
        Owner owner = requestDtoMapper.mapToModel(requestDto);
        owner.setId(id);
        return responseDtoMapper.mapToDto(ownerService.update(owner));
    }

    @GetMapping("/{id}/orders")
    @ApiOperation("Get all orders by owner id")
    public List<OrderResponseDto> getOrders(@PathVariable Long id) {
        return ownerService.getOrders(id)
                .stream()
                .map(orderResponseDtoMapper::mapToDto)
                .toList();
    }

    private Owner newOwner() {
        Owner owner = new Owner();
        owner.setCars(List.of());
        owner.setOrders(List.of());
        return owner;
    }
}
