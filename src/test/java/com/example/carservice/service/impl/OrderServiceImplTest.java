package com.example.carservice.service.impl;

import com.example.carservice.model.Car;
import com.example.carservice.model.Goods;
import com.example.carservice.model.Order;
import com.example.carservice.model.Owner;
import com.example.carservice.model.ServiceModel;
import com.example.carservice.repository.OrderRepository;
import com.example.carservice.service.MasterService;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private MasterService masterService;

    @Test
    void updateStatusWithoutCompletionTime() {
        Long id = 1L;
        updateStatusTestInit(id, Order.OrderStatus.ACCEPTED);
        Order.OrderStatus newStatus = Order.OrderStatus.IN_PROCESS;
        Order actual = orderService.updateStatus(id, newStatus);
        Assertions.assertEquals(newStatus, actual.getStatus());
        Assertions.assertNull(actual.getCompletionTime());
    }

    @Test
    void updateStatusWithCompletionTime() {
        Long id = 1L;
        updateStatusTestInit(id, Order.OrderStatus.IN_PROCESS);
        Order.OrderStatus newStatus = Order.OrderStatus.COMPLETED_SUCCESSFULLY;
        Order actual = orderService.updateStatus(id, newStatus);
        Assertions.assertEquals(newStatus, actual.getStatus());
        Assertions.assertNotNull(actual.getCompletionTime());
    }

    @Test
    void calculatePriceWithDiagnostic() {
        Long id = 2L;
        Order order = new Order();
        order.setCar(new Car());
        order.getCar().setOwner(new Owner());
        order.getCar().getOwner().setOrders(List.of(new Order(), new Order(), new Order()));

        Goods goods = new Goods();
        goods.setPrice(new BigDecimal(150));
        order.setGoods(List.of(goods));

        ServiceModel diagnostic = new ServiceModel();
        diagnostic.setDiagnostic(true);
        diagnostic.setPrice(new BigDecimal(500));
        order.setServices(List.of(diagnostic));
        Mockito.when(orderRepository.getReferenceById(id)).thenReturn(order);

        BigDecimal expected = new BigDecimal("615.50");
        BigDecimal actual = orderService.calculatePrice(id);
        Assertions.assertEquals(expected, actual.round(new MathContext(5)));
    }

    @Test
    void calculatePriceWithoutDiagnostic() {
        Long id = 2L;
        Order order = new Order();
        order.setCar(new Car());
        order.getCar().setOwner(new Owner());
        order.getCar().getOwner().setOrders(List.of(new Order(), new Order()));

        Goods goods = new Goods();
        goods.setPrice(new BigDecimal(200));
        order.setGoods(List.of(goods));

        ServiceModel diagnostic = new ServiceModel();
        diagnostic.setDiagnostic(true);
        diagnostic.setPrice(new BigDecimal(500));
        ServiceModel service = new ServiceModel();
        service.setDiagnostic(false);
        service.setPrice(new BigDecimal(800));
        order.setServices(List.of(diagnostic, service));
        Mockito.when(orderRepository.getReferenceById(id)).thenReturn(order);

        BigDecimal expected = new BigDecimal("964.00");
        BigDecimal actual = orderService.calculatePrice(id);
        Assertions.assertEquals(expected, actual.round(new MathContext(5)));
    }

    private void updateStatusTestInit(Long id, Order.OrderStatus status) {
        Order order = new Order();
        order.setStatus(status);
        order.setServices(List.of());
        Mockito.when(orderRepository.getReferenceById(id)).thenReturn(order);
        Mockito.when(orderRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
    }
}