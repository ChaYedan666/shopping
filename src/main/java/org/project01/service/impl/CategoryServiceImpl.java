package org.project01.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project01.dao.CategoryDao;
import org.project01.domain.Category;
import org.project01.exceptions.CategoryHasProductException;
import org.project01.service.CategoryService;
import org.project01.utils.RedisUtil;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    public List<Category> findAll(){
        // 连接redis数据库
        Jedis connection = null;
        try {
            // 通过工具类获得连接
            connection = RedisUtil.getConnection();
            // 先去查询在数据库中有没有
            String categories = connection.get("categories");
            if (categories == null){
                System.out.println("第一次 设置");
                // 如果没有，在redis数据库中设置
                CategoryDao categoryDao = new CategoryDao();
                List<Category> all = categoryDao.findAll();
                // 将对象集合转换成字符串
                String s = new ObjectMapper().writeValueAsString(all);
                connection.set("categories", s);
                // 设置完后将查询出的List集合返回
                return all;
            }else {
                // 如果缓存中有，直接返回
                // 将字符串转换成JSON对象
                List list = new ObjectMapper().readValue(categories, List.class);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // 防止有借无还
            if (connection != null){
                connection.close();
            }

        }

    }

    public Category findById(String cid){
        CategoryDao categoryDao = new CategoryDao();

        Category byId = categoryDao.findById(cid);

        return byId;
    }

    public void save(Category category) {
        CategoryDao categoryDao = new CategoryDao();

        categoryDao.save(category);

        // 刷新数据库
        refresh();
    }

    private void refresh() {
        // 连接redis
        Jedis connection = RedisUtil.getConnection();
        try {
            // 查询到更新后的数据将原来的数据进行刷新
            List<Category> all = new CategoryDao().findAll();
            String s = new ObjectMapper().writeValueAsString(all);
            connection.set("categories",s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }finally {
            if (connection != null){
                connection.close();
            }
        }


    }

    public void update(Category category) {
        // 目录名修改操作，操作完成后也要刷新

        CategoryDao categoryDao = new CategoryDao();
        categoryDao.update(category);
        // 刷新
        refresh();
    }

    public void del(String cid) throws CategoryHasProductException {
        // 目录名删除操作，关键在于，跟其他表相连的外键能不能删除，只要其他表中还有一个数据，那么这个目录就不能删除
        // 查询此目录的商品数
        CategoryDao categoryDao = new CategoryDao();
        int count = categoryDao.countByCId(cid);
        if (count > 0){
            throw new CategoryHasProductException();

        }else {
            categoryDao.del(cid);
            refresh();
        }




    }
}
