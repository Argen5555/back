package com.example.carservice.dto.mapper.impl;

import com.example.carservice.dto.mapper.RequestDtoMapper;
import com.example.carservice.dto.mapper.ResponseDtoMapper;
import com.example.carservice.dto.request.ServiceRequestDto;
import com.example.carservice.dto.response.ServiceResponseDto;
import com.example.carservice.model.ServiceModel;
import com.example.carservice.service.MasterService;
import com.example.carservice.service.OrderService;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper implements RequestDtoMapper<ServiceRequestDto, ServiceModel>,
        ResponseDtoMapper<ServiceResponseDto, ServiceModel> {
    private final OrderService orderService;
    private final MasterService masterService;

    public ServiceMapper(OrderService orderService, MasterService masterService) {
        this.orderService = orderService;
        this.masterService = masterService;
    }

    @Override
    public ServiceModel mapToModel(ServiceRequestDto dto) {
        ServiceModel service = new ServiceModel();
        service.setDiagnostic(dto.getDiagnostic());
        service.setOrder(orderService.get(dto.getOrderId()));
        service.setMaster(masterService.get(dto.getMasterId()));
        service.setPrice(dto.getPrice());
        service.setStatus(dto.getStatus());
        return service;
    }

    @Override
    public ServiceResponseDto mapToDto(ServiceModel service) {
        ServiceResponseDto dto = new ServiceResponseDto();
        dto.setId(service.getId());
        dto.setDiagnostic(service.getDiagnostic());
        dto.setOrderId(service.getOrder().getId());
        dto.setMasterId(service.getMaster().getId());
        dto.setPrice(service.getPrice());
        dto.setStatus(service.getStatus());
        return dto;
    }
}
