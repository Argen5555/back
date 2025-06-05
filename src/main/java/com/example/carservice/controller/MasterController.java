package com.example.carservice.controller;

import com.example.carservice.dto.mapper.RequestDtoMapper;
import com.example.carservice.dto.mapper.ResponseDtoMapper;
import com.example.carservice.dto.request.MasterRequestDto;
import com.example.carservice.dto.response.MasterResponseDto;
import com.example.carservice.dto.response.OrderResponseDto;
import com.example.carservice.model.Master;
import com.example.carservice.model.Order;
import com.example.carservice.service.MasterService;
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
@RequestMapping("/masters")
public class MasterController {
    private final MasterService masterService;
    private final RequestDtoMapper<MasterRequestDto, Master> requestDtoMapper;
    private final ResponseDtoMapper<MasterResponseDto, Master> responseDtoMapper;
    private final ResponseDtoMapper<OrderResponseDto, Order> orderResponseDtoMapper;

    public MasterController(MasterService masterService,
                            RequestDtoMapper<MasterRequestDto, Master> requestDtoMapper,
                            ResponseDtoMapper<MasterResponseDto, Master> responseDtoMapper,
                            ResponseDtoMapper<OrderResponseDto, Order> orderResponseDtoMapper) {
        this.masterService = masterService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
        this.orderResponseDtoMapper = orderResponseDtoMapper;
    }

    @GetMapping
    @ApiOperation("Get all masters")
    public List<MasterResponseDto> getAll() {
        return masterService.getAll()
                .stream()
                .map(responseDtoMapper::mapToDto)
                .toList();
    }

    @GetMapping("/{id}")
    @ApiOperation("Get master by id")
    public MasterResponseDto get(@PathVariable Long id) {
        Master master = masterService.get(id);
        return responseDtoMapper.mapToDto(master);
    }

    @PostMapping
    @ApiOperation("Add a new master")
    public MasterResponseDto create(@RequestBody MasterRequestDto requestDto) {
        Master master = requestDtoMapper.mapToModel(requestDto);
        return responseDtoMapper.mapToDto(masterService.add(master));
    }

    @PostMapping("/{id}")
    @ApiOperation("Update master by id")
    public MasterResponseDto update(@PathVariable Long id,
                                    @RequestBody MasterRequestDto requestDto) {
        Master master = requestDtoMapper.mapToModel(requestDto);
        master.setId(id);
        return responseDtoMapper.mapToDto(masterService.update(master));
    }

    @GetMapping("/{id}/orders")
    @ApiOperation("Get all orders by master id")
    public List<OrderResponseDto> getOrders(@PathVariable Long id) {
        return masterService.getOrders(id)
                .stream()
                .map(orderResponseDtoMapper::mapToDto)
                .toList();
    }

    @GetMapping("/{id}/salary")
    @ApiOperation("Calculate salary by master id")
    public BigDecimal calculateSalary(@PathVariable Long id) {
        return masterService.calculateSalary(id);
    }
}
