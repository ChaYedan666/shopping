package org.project01.service;

import org.project01.domain.Order;
import org.project01.domain.OrderItem;

import java.util.ArrayList;
import java.util.List;

public interface OrderService {
    void save(Order order, ArrayList<OrderItem> orderItems);

    Order findByOidWithItems(String oid);

    List<Order> findOrderByUid(String uid, int pageNumber, int pageSize);

    int countByUid(String uid);

    void updateSHR(Order order);

    void updateState(String oid, int orderStateYifukuan);
}
