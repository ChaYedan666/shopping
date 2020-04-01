package org.project01.service;

import org.project01.dao.UserDao;
import org.project01.domain.User;

public class UserService {
    public void save(User user) {
        UserDao userDao = new UserDao();
        userDao.save(user);
    }

    public User findByUAP(String username, String password) {
        UserDao userDao = new UserDao();
        return UserDao.findByUAP(username,password);

    }
}
