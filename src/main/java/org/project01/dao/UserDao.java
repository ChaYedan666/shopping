package org.project01.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.project01.domain.User;
import org.project01.utils.DataSourceUtil;

import javax.sql.DataSource;
import java.sql.SQLException;

public class UserDao {
    public static User findByUAP(String username, String password) {
        DataSource dataSource = DataSourceUtil.getDataSource();

        QueryRunner queryRunner = new QueryRunner(dataSource);

        String sql = "SELECT * FROM USER WHERE USERNAME=? AND PASSWORD=?";

        User query = null;
        try {
            query = queryRunner.query(sql, new BeanHandler<User>(User.class), username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;

    }

    public void save(User user) {
        DataSource dataSource = DataSourceUtil.getDataSource();

        QueryRunner queryRunner = new QueryRunner(dataSource);

        String sql = "insert into user values(?,?,?,?,?,?,?,?)";

        try {
            int update = queryRunner.update(sql,user.getUid(),user.getUsername(),user.getPassword(),
                    user.getName(),user.getEmail(),user.getBirthday(),user.getGender(),user.getRemark()
            );
        } catch (SQLException e) {
           throw new RuntimeException(e);
        }

    }
}
