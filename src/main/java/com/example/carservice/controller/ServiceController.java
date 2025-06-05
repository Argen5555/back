package com.example.carservice.controller;

import com.example.carservice.dto.mapper.RequestDtoMapper;
import com.example.carservice.dto.mapper.ResponseDtoMapper;
import com.example.carservice.dto.request.ServiceRequestDto;
import com.example.carservice.dto.request.StatusRequestDto;
import com.example.carservice.dto.response.ServiceResponseDto;
import com.example.carservice.model.ServiceModel;
import com.example.carservice.service.ServiceModelService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
public class ServiceController {
    private final ServiceModelService serviceModelService;
    private final RequestDtoMapper<ServiceRequestDto, ServiceModel> requestDtoMapper;
    private final ResponseDtoMapper<ServiceResponseDto, ServiceModel> responseDtoMapper;
    private final RequestDtoMapper<StatusRequestDto,
            ServiceModel.ServiceStatus> statusRequestDtoMapper;

    public ServiceController(ServiceModelService serviceModelService,
            RequestDtoMapper<ServiceRequestDto, ServiceModel> requestDtoMapper,
            ResponseDtoMapper<ServiceResponseDto, ServiceModel> responseDtoMapper,
            RequestDtoMapper<StatusRequestDto, ServiceModel.ServiceStatus> statusRequestDtoMapper) {
        this.serviceModelService = serviceModelService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
        this.statusRequestDtoMapper = statusRequestDtoMapper;
    }

    @GetMapping
    @ApiOperation("Get all services")
    public List<ServiceResponseDto> getAll() {
        return serviceModelService.getAll()
                .stream()
                .map(responseDtoMapper::mapToDto)
                .toList();
    }

    @GetMapping("/{id}")
    @ApiOperation("Get service by id")
    public ServiceResponseDto get(@PathVariable Long id) {
        ServiceModel service = serviceModelService.get(id);
        return responseDtoMapper.mapToDto(service);
    }

    @PostMapping
    @ApiOperation("Add a new service")
    public ServiceResponseDto create(@RequestBody ServiceRequestDto requestDto) {
        ServiceModel service = requestDtoMapper.mapToModel(requestDto);
        return responseDtoMapper.mapToDto(serviceModelService.add(service));
    }

    @PostMapping("/{id}")
    @ApiOperation("Update service by id")
    public ServiceResponseDto update(@PathVariable Long id,
                                     @RequestBody ServiceRequestDto requestDto) {
        ServiceModel service = requestDtoMapper.mapToModel(requestDto);
        service.setId(id);
        return responseDtoMapper.mapToDto(serviceModelService.update(service));
    }

    @PostMapping("/{id}/status")
    @ApiOperation("Update status by service id")
    public ServiceResponseDto updateStatus(@PathVariable Long id,
                                           @RequestBody StatusRequestDto statusRequestDto) {
        ServiceModel.ServiceStatus status = statusRequestDtoMapper.mapToModel(statusRequestDto);
        return responseDtoMapper.mapToDto(serviceModelService.updateStatus(id, status));
    }
}
