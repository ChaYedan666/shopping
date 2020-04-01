package org.project01.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.project01.dao.OrderDao;
import org.project01.domain.Order;
import org.project01.domain.OrderItem;
import org.project01.utils.DataSourceUtil;

import javax.sql.DataSource;
import java.sql.SQLException;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void saveOrder(Order order) {

        QueryRunner queryRunner = new QueryRunner();

        String sql = "INSERT INTO ORDERS VALUES(?,?,?,?,null,null,null,?)";

        try {
            int update = queryRunner.update(DataSourceUtil.getConnection(),sql, order.getOid(), order.getOrdertime(), order.getTotal(),
                    order.getState(),order.getUid());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveOrderItem(OrderItem orderItem) {
        QueryRunner queryRunner = new QueryRunner();

        String sql = "INSERT INTO ORDERITEM VALUES(?,?,?,?)";

        try {
            int update = queryRunner.update(DataSourceUtil.getConnection(),sql, orderItem.getCount(),orderItem.getSubTotal(),
                    orderItem.getPid(),orderItem.getOid());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
