package com.example.carservice.service.impl;

import com.example.carservice.model.Master;
import com.example.carservice.model.Order;
import com.example.carservice.model.ServiceModel;
import com.example.carservice.repository.MasterRepository;
import com.example.carservice.service.MasterService;
import com.example.carservice.service.ServiceModelService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class MasterServiceImpl implements MasterService {
    private static final double SALARY_PERCENT = 0.4;
    private final MasterRepository masterRepository;
    private final ServiceModelService serviceModelService;

    public MasterServiceImpl(MasterRepository masterRepository,
                             ServiceModelService serviceModelService) {
        this.masterRepository = masterRepository;
        this.serviceModelService = serviceModelService;
    }

    @Override
    public List<Master> getAll() {
        return masterRepository.findAll();
    }

    @Override
    public Master get(Long id) {
        return masterRepository.getReferenceById(id);
    }

    @Override
    public Master add(Master master) {
        return masterRepository.save(master);
    }

    @Override
    public Master update(Master master) {
        Master oldMaster = get(master.getId());
        master.setCompletedOrders(oldMaster.getCompletedOrders());
        return masterRepository.save(master);
    }

    @Override
    public List<Master> update(Iterable<Master> masters) {
        return masterRepository.saveAll(masters);
    }

    @Override
    public Set<Order> getOrders(Long id) {
        return masterRepository.getReferenceById(id).getCompletedOrders();
    }

    @Override
    public BigDecimal calculateSalary(Long id) {
        return get(id).getCompletedOrders()
                .stream()
                .flatMap(order -> order.getServices().stream())
                .filter(service -> service.getStatus().equals(ServiceModel.ServiceStatus.UNPAID))
                .peek(service -> serviceModelService.updateStatus(
                        service.getId(), ServiceModel.ServiceStatus.PAID))
                .map(ServiceModel::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .multiply(new BigDecimal(SALARY_PERCENT));
    }
}
