package org.project01.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.project01.dao.OrderDao;
import org.project01.domain.Order;
import org.project01.domain.OrderItem;
import org.project01.domain.vo.OrderItemVo;
import org.project01.utils.DataSourceUtil;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

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

    @Override
    public Order findByOid(String oid) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

        String sql="SELECT * FROM ORDERS WHERE OID=?";

        try {
            Order query = queryRunner.query(sql, new BeanHandler<Order>(Order.class), oid);
            return query;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<OrderItemVo> findItemVos(String oid) {

        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

        String sql="SELECT oi.count,oi.subtotal,oi.pid,p.pname,p.pimage,p.shop_price AS price FROM orderitem oi,product p WHERE oi.pid=p.pid AND oid=?";

        try {
            List<OrderItemVo> query = queryRunner.query(sql, new BeanListHandler<OrderItemVo>(OrderItemVo.class), oid);
            return query;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByUid(String uid) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

        String sql = "SELECT COUNT(*) FROM ORDERS WHERE UID=?";

        try {
            int count = ((Long) queryRunner.query(sql, new ScalarHandler(), uid)).intValue();
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Order> findOrderByUid(String uid, int pageNumber, int pageSize) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

        String sql="SELECT * FROM orders  WHERE uid=? LIMIT ?,?";

        try {
            return queryRunner.query(sql,new BeanListHandler<>(Order.class),uid,(pageNumber-1)*pageSize,pageSize);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateSHR(Order order) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

        String sql = "UPDATE ORDERS SET NAME=? ,ADDRESS=?,TELEPHONE=? WHERE OID=?";

        try {
            queryRunner.update(sql,order.getName(),order.getAddress(),order.getTelephone(),order.getOid());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateState(String oid, int orderStateYifukuan) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

        String sql = "UPDATE ORDERS SET STATE = ? WHERE OID = ?";

        try {
            queryRunner.update(sql,orderStateYifukuan,oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
