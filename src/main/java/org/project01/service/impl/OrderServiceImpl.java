package org.project01.service.impl;

import org.project01.dao.OrderDao;
import org.project01.domain.Order;
import org.project01.domain.OrderItem;
import org.project01.domain.vo.OrderItemVo;
import org.project01.service.OrderService;
import org.project01.utils.BeanFactory;
import org.project01.utils.DataSourceUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Order findByOidWithItems(String oid) {
        Order order = orderDao.findByOid(oid);
        List<OrderItemVo> vos=orderDao.findItemVos(oid);

        order.setVos(vos);

        return order;
    }

    @Override
    public List<Order> findOrderByUid(String uid, int pageNumber, int pageSize) {
        List<Order> orders = orderDao.findOrderByUid(uid,pageNumber,pageSize);
        // 上面只查询出了订单的相关信息。但数据库中订单项是和订单信息分开的，所以下面把订单项查出来
        for (Order order : orders) {
            List<OrderItemVo> itemVos = orderDao.findItemVos(order.getOid());
            order.setVos(itemVos);
        }

        return orders;
    }

    @Override
    public int countByUid(String uid) {
        int count = orderDao.countByUid(uid);
        return count;
    }

    @Override
    public void updateSHR(Order order) {
        orderDao.updateSHR(order);
    }

    @Override
    public void updateState(String oid, int orderStateYifukuan) {
        orderDao.updateState(oid,orderStateYifukuan);
    }
}
