package com.example.carservice.service.impl;

import com.example.carservice.model.Master;
import com.example.carservice.model.Order;
import com.example.carservice.model.ServiceModel;
import com.example.carservice.repository.MasterRepository;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Set;
import com.example.carservice.service.ServiceModelService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MasterServiceImplTest {
    @InjectMocks
    private MasterServiceImpl masterService;

    @Mock
    private MasterRepository masterRepository;
    @Mock
    private ServiceModelService serviceModelService;

    @Test
    void calculateSalary() {
        Long masterId = 1L;
        ServiceModel.ServiceStatus serviceStatus = ServiceModel.ServiceStatus.UNPAID;
        ServiceModel firstService = new ServiceModel();
        firstService.setPrice(new BigDecimal(600));
        firstService.setStatus(serviceStatus);
        ServiceModel secondService = new ServiceModel();
        secondService.setPrice(new BigDecimal(400));
        secondService.setStatus(serviceStatus);
        Order order = new Order();
        order.setServices(List.of(firstService, secondService));
        Master master = new Master();
        master.setCompletedOrders(Set.of(order));
        Mockito.when(masterRepository.getReferenceById(masterId)).thenReturn(master);

        BigDecimal expected = new BigDecimal("400.00");
        BigDecimal actual = masterService.calculateSalary(masterId);
        Assertions.assertEquals(expected, actual.round(new MathContext(5)));
    }
}