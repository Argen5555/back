package com.example.carservice.dto.mapper.impl;

import com.example.carservice.dto.mapper.RequestDtoMapper;
import com.example.carservice.dto.request.StatusRequestDto;
import com.example.carservice.model.ServiceModel;
import org.springframework.stereotype.Component;

@Component
public class ServiceStatusMapper implements
        RequestDtoMapper<StatusRequestDto, ServiceModel.ServiceStatus> {
    @Override
    public ServiceModel.ServiceStatus mapToModel(StatusRequestDto dto) {
        return ServiceModel.ServiceStatus.valueOf(dto.getName());
    }
}
