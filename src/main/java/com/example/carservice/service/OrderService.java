package com.example.carservice.service;

import com.example.carservice.model.Goods;
import com.example.carservice.model.Order;
import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    List<Order> getAll();

    Order get(Long id);

    Order add(Order order);

    Order addGoods(Long id, Goods goods);

    Order update(Order order);

    Order updateStatus(Long id, Order.OrderStatus status);

    BigDecimal calculatePrice(Long id);
}
