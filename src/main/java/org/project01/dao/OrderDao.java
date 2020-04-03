package org.project01.dao;

import org.project01.domain.Order;
import org.project01.domain.OrderItem;
import org.project01.domain.vo.OrderItemVo;

import java.util.List;

public interface OrderDao {
    void saveOrder(Order order);

    void saveOrderItem(OrderItem orderItem);

    Order findByOid(String oid);

    List<OrderItemVo> findItemVos(String oid);

    int countByUid(String uid);

    List<Order> findOrderByUid(String uid, int pageNumber, int pageSize);

    void updateSHR(Order order);

    void updateState(String oid, int orderStateYifukuan);
}
