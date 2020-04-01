package org.project01.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
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

}
