package org.project01.dao;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.project01.constants.Global;
import org.project01.domain.Product;
import org.project01.utils.DataSourceUtil;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class ProductDao {

    public int count(String cid){
        DataSource dataSource = DataSourceUtil.getDataSource();
        QueryRunner queryRunner = new QueryRunner(dataSource);

        String sql = "SELECT COUNT(*) FROM PRODUCT WHERE CID = ?";
        int i = 0;
        try {
            i = ((Long) queryRunner.query(sql, new ScalarHandler(), cid)).intValue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public List<Product> findByPageWithCid(String cid, int pageNumber, int pageSize){
        DataSource dataSource = DataSourceUtil.getDataSource();
        QueryRunner queryRunner = new QueryRunner(dataSource);

        String sql = "SELECT * FROM PRODUCT WHERE CID = ? LIMIT ?,?";
        List<Product> query = null;
        try {
            query = queryRunner.query(sql, new BeanListHandler<Product>(Product.class), cid, (pageNumber - 1) * pageSize, pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }


    public  List<Product> findNews(){
        DataSource dataSource = DataSourceUtil.getDataSource();
        QueryRunner queryRunner = new QueryRunner(dataSource);

        String sql = "SELECT * FROM PRODUCT WHERE PFLAG = ? ORDER BY PDATE DESC LIMIT 9";

        List<Product> query = null;
        try {
            query = queryRunner.query(sql, new BeanListHandler<Product>(Product.class), Global.PRODUCT_PFLAG_ON);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    public  List<Product> findHots(){
        DataSource dataSource = DataSourceUtil.getDataSource();
        QueryRunner queryRunner = new QueryRunner(dataSource);

        String sql = "SELECT * FROM PRODUCT WHERE PFLAG = ? AND IS_HOT = ? ORDER BY PDATE DESC LIMIT 9";

        List<Product> query = null;
        try {
            query = queryRunner.query(sql, new BeanListHandler<Product>(Product.class), Global.PRODUCT_PFLAG_ON, Global.PRODUCT_IS_HOT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    public Product findById(String pid){
        DataSource dataSource = DataSourceUtil.getDataSource();
        QueryRunner queryRunner = new QueryRunner(dataSource);

        String sql = "SELECT * FROM PRODUCT WHERE PID=?";

        Product query = null;
        try {
            query = queryRunner.query(sql, new BeanHandler<Product>(Product.class), pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return query;

    }


}
