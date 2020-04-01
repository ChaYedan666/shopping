package org.project01.dao;

import org.project01.domain.Order;
import org.project01.domain.OrderItem;

public interface OrderDao {
    void saveOrder(Order order);

    void saveOrderItem(OrderItem orderItem);

}
