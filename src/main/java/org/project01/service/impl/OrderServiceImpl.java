package org.project01.service.impl;

import org.project01.dao.OrderDao;
import org.project01.domain.Order;
import org.project01.domain.OrderItem;
import org.project01.service.OrderService;
import org.project01.utils.BeanFactory;
import org.project01.utils.DataSourceUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = BeanFactory.newInstance(OrderDao.class);
    @Override
    public void save(Order order, ArrayList<OrderItem> orderItems) {

        try {
            // 开启事务
            DataSourceUtil.beginTransaction();
            // 执行代码
            // 分开保存吧
            orderDao.saveOrder(order);

            for (OrderItem orderItem : orderItems) {
                orderDao.saveOrderItem(orderItem);
            }

            // 提交事务
            DataSourceUtil.commitAndClose();

        }catch (SQLException e){
            //有问题回滚事务
            DataSourceUtil.rollbackAndClose();
            throw new RuntimeException(e);

        }

    }
}
