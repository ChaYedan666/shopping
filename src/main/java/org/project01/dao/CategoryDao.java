package org.project01.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.project01.domain.Category;
import org.project01.utils.DataSourceUtil;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class CategoryDao {
    public List<Category> findAll() {
        DataSource dataSource = DataSourceUtil.getDataSource();

        QueryRunner queryRunner = new QueryRunner(dataSource);

        String sql = "SELECT * FROM CATEGORY";

        List<Category> query = null;
        try {
            query = queryRunner.query(sql, new BeanListHandler<>(Category.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;

    }

    public Category findById(String cid){
        DataSource dataSource = DataSourceUtil.getDataSource();
        QueryRunner queryRunner = new QueryRunner(dataSource);

        String sql = "SELECT * FROM CATEGORY WHERE CID = ?";

        Category query = null;
        try {
            query = queryRunner.query(sql, new BeanHandler<Category>(Category.class), cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return query;
    }

    public void save(Category category) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

        String sql = "INSERT INTO CATEGORY VALUES(?,?)";

        try {
            int update = queryRunner.update(sql, category.getCid(), category.getCname());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(Category category) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

        String sql = "UPDATE CATEGORY SET CNAME = ? WHERE CID = ?";

        try {
            queryRunner.update(sql,category.getCname(),category.getCid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void del(String cid) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());

        String sql = "DELETE FROM CATEGORY WHERE CID = ?";

        try {
            queryRunner.update(sql,cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int countByCId(String cid) {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());

        //编写sql
        String sql="SELECT COUNT(*) FROM PRODUCT WHERE CID=?";

        try {
            return ((Long)qr.query(sql,new ScalarHandler(),cid)).intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
