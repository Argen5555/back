package com.example.carservice.service.impl;

import com.example.carservice.model.Goods;
import com.example.carservice.model.Master;
import com.example.carservice.model.Order;
import com.example.carservice.model.Owner;
import com.example.carservice.model.ServiceModel;
import com.example.carservice.repository.OrderRepository;
import com.example.carservice.service.MasterService;
import com.example.carservice.service.OrderService;
import com.example.carservice.service.OwnerService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private static final double GOODS_DISCOUNT_PERCENT = 0.01;
    private static final double SERVICE_DISCOUNT_PERCENT = 0.02;
    private final OrderRepository orderRepository;
    private final OwnerService ownerService;
    private final MasterService masterService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OwnerService ownerService,
                            MasterService masterService) {
        this.orderRepository = orderRepository;
        this.ownerService = ownerService;
        this.masterService = masterService;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order get(Long id) {
        return orderRepository.getReferenceById(id);
    }

    @Override
    public Order add(Order order) {
        order.setOrderTime(LocalDateTime.now());
        orderRepository.save(order);
        Owner owner = order.getCar().getOwner();
        owner.getOrders().add(order);
        ownerService.update(owner);
        return order;
    }

    @Override
    public Order addGoods(Long id, Goods goods) {
        Order order = orderRepository.getReferenceById(id);
        order.getGoods().add(goods);
        return update(order);
    }

    @Override
    public Order update(Order order) {
        Order oldOrder = get(order.getId());
        order.setOrderTime(oldOrder.getOrderTime());
        order.setServices(oldOrder.getServices());
        order.setPrice(oldOrder.getPrice());
        order.setCompletionTime(oldOrder.getCompletionTime());
        return orderRepository.save(order);
    }

    @Override
    public Order updateStatus(Long id, Order.OrderStatus status) {
        Order order = orderRepository.getReferenceById(id);
        if (status == Order.OrderStatus.COMPLETED_SUCCESSFULLY
                || status == Order.OrderStatus.COMPLETED_UNSUCCESSFULLY) {
            order.setCompletionTime(LocalDateTime.now());
            List<Master> masters = order.getServices()
                    .stream()
                    .map(ServiceModel::getMaster)
                    .distinct()
                    .peek(master -> master.getCompletedOrders().add(order))
                    .toList();
            masterService.update(masters);
        }
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Override
    public BigDecimal calculatePrice(Long id) {
        Order order = orderRepository.getReferenceById(id);
        BigDecimal price = calculateGoodsPriceAfterDiscount(order)
                .add(calculateServicesPriceAfterDiscount(order));
        order.setPrice(price);
        orderRepository.save(order);
        return price;
    }

    private BigDecimal calculateGoodsPriceAfterDiscount(Order order) {
        int ownerOrderSize = order.getCar().getOwner().getOrders().size();
        double discount = ownerOrderSize * GOODS_DISCOUNT_PERCENT;
        return order.getGoods()
                .stream()
                .map(Goods::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .multiply(new BigDecimal(1.0 - discount));
    }

    private BigDecimal calculateServicesPriceAfterDiscount(Order order) {
        int ownerOrderSize = order.getCar().getOwner().getOrders().size();
        double discount = ownerOrderSize * SERVICE_DISCOUNT_PERCENT;
        boolean allServicesAreDiagnostic = order.getServices()
                .stream()
                .allMatch(ServiceModel::getDiagnostic);
        return order.getServices()
                .stream()
                .filter(service -> allServicesAreDiagnostic ^ !service.getDiagnostic())
                .map(ServiceModel::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .multiply(new BigDecimal(1.0 - discount));
    }
}
