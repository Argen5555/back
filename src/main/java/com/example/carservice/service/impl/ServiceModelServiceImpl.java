package com.example.carservice.service.impl;

import com.example.carservice.model.ServiceModel;
import com.example.carservice.repository.ServiceRepository;
import com.example.carservice.service.ServiceModelService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ServiceModelServiceImpl implements ServiceModelService {
    private final ServiceRepository serviceRepository;

    public ServiceModelServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public List<ServiceModel> getAll() {
        return serviceRepository.findAll();
    }

    @Override
    public ServiceModel get(Long id) {
        return serviceRepository.getReferenceById(id);
    }

    @Override
    public ServiceModel add(ServiceModel service) {
        return serviceRepository.save(service);
    }

    @Override
    public ServiceModel update(ServiceModel service) {
        return serviceRepository.save(service);
    }

    @Override
    public ServiceModel updateStatus(Long id, ServiceModel.ServiceStatus status) {
        ServiceModel service = serviceRepository.getReferenceById(id);
        service.setStatus(status);
        return update(service);
    }
}
