package org.project01.service;

import org.project01.domain.Order;
import org.project01.domain.OrderItem;

import java.util.ArrayList;

public interface OrderService {
    void save(Order order, ArrayList<OrderItem> orderItems);
}
