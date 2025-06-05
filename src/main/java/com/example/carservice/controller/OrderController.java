package com.example.carservice.controller;

import com.example.carservice.dto.mapper.RequestDtoMapper;
import com.example.carservice.dto.mapper.ResponseDtoMapper;
import com.example.carservice.dto.request.GoodsRequestDto;
import com.example.carservice.dto.request.OrderRequestDto;
import com.example.carservice.dto.request.StatusRequestDto;
import com.example.carservice.dto.response.OrderResponseDto;
import com.example.carservice.model.Goods;
import com.example.carservice.model.Order;
import com.example.carservice.service.OrderService;
import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final RequestDtoMapper<OrderRequestDto, Order> requestDtoMapper;
    private final ResponseDtoMapper<OrderResponseDto, Order> responseDtoMapper;
    private final RequestDtoMapper<GoodsRequestDto, Goods> goodsRequestDtoMapper;
    private final RequestDtoMapper<StatusRequestDto, Order.OrderStatus> statusRequestDtoMapper;

    public OrderController(OrderService orderService,
            RequestDtoMapper<OrderRequestDto, Order> requestDtoMapper,
            ResponseDtoMapper<OrderResponseDto, Order> responseDtoMapper,
            RequestDtoMapper<GoodsRequestDto, Goods> goodsRequestDtoMapper,
            RequestDtoMapper<StatusRequestDto, Order.OrderStatus> statusRequestDtoMapper) {
        this.orderService = orderService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
        this.goodsRequestDtoMapper = goodsRequestDtoMapper;
        this.statusRequestDtoMapper = statusRequestDtoMapper;
    }

    @GetMapping
    @ApiOperation("Get all orders")
    public List<OrderResponseDto> getAll() {
        return orderService.getAll()
                .stream()
                .map(responseDtoMapper::mapToDto)
                .toList();
    }

    @GetMapping("/{id}")
    @ApiOperation("Get order by id")
    public OrderResponseDto get(@PathVariable Long id) {
        Order order = orderService.get(id);
        return responseDtoMapper.mapToDto(order);
    }

    @PostMapping
    @ApiOperation("Add a new order")
    public OrderResponseDto create(@RequestBody OrderRequestDto requestDto) {
        Order order = requestDtoMapper.mapToModel(requestDto);
        return responseDtoMapper.mapToDto(orderService.add(order));
    }

    @PostMapping("/{id}")
    @ApiOperation("Update order by id")
    public OrderResponseDto update(@PathVariable Long id,
                                   @RequestBody OrderRequestDto requestDto) {
        Order order = requestDtoMapper.mapToModel(requestDto);
        order.setId(id);
        return responseDtoMapper.mapToDto(orderService.update(order));
    }

    @PostMapping("/{id}/goods")
    @ApiOperation("Add a new goods to order by id")
    public OrderResponseDto addGoods(@PathVariable Long id,
                                     @RequestBody GoodsRequestDto goodsRequestDto) {
        Goods goods = goodsRequestDtoMapper.mapToModel(goodsRequestDto);
        return responseDtoMapper.mapToDto(orderService.addGoods(id, goods));
    }

    @PostMapping("/{id}/status")
    @ApiOperation("Update status by order id")
    public OrderResponseDto updateStatus(@PathVariable Long id,
                                         @RequestBody StatusRequestDto statusRequestDto) {
        Order.OrderStatus status = statusRequestDtoMapper.mapToModel(statusRequestDto);
        return responseDtoMapper.mapToDto(orderService.updateStatus(id, status));
    }

    @GetMapping("/{id}/price")
    @ApiOperation("Calculate price by order id")
    public BigDecimal calculatePrice(@PathVariable Long id) {
        return orderService.calculatePrice(id);
    }
}
