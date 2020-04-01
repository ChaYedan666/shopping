package org.project01.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project01.dao.CategoryDao;
import org.project01.domain.Category;
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

}
