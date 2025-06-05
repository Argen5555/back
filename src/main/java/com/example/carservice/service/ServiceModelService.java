package com.example.carservice.service;

import com.example.carservice.model.ServiceModel;
import java.util.List;

public interface ServiceModelService {
    List<ServiceModel> getAll();

    ServiceModel get(Long id);

    ServiceModel add(ServiceModel service);

    ServiceModel update(ServiceModel service);

    ServiceModel updateStatus(Long id, ServiceModel.ServiceStatus status);
}
