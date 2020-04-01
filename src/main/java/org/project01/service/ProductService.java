package org.project01.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project01.dao.ProductDao;
import org.project01.domain.Category;
import org.project01.domain.Product;
import org.project01.utils.RedisUtil;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;

public class ProductService {

    public int count(String cid){
        ProductDao productDao = new ProductDao();
        int count = productDao.count(cid);
        return count;
    }

    public List<Product> findByPageWithCid(String cid, int pageNumber, int pageSize){
        ProductDao productDao = new ProductDao();
        List<Product> byPageWithCid = productDao.findByPageWithCid(cid, pageNumber, pageSize);

        return byPageWithCid;
    }

    public Product findById(String pid){
        ProductDao productDao = new ProductDao();
        Product byId = productDao.findById(pid);

        return byId;
    }

    public List<Product> findNews(){
        // 加入到redis缓存
        Jedis connection = null;

        // 获取连接
        try {
            connection = RedisUtil.getConnection();
            String news = connection.get("news");
            // 查询键是否存在
            if (news == null){
                // 不存在添加
                System.out.println("不存在 添加商品");
                ProductDao productDao = new ProductDao();
                List<Product> products = productDao.findNews();

                String s = new ObjectMapper().writeValueAsString(products);
                connection.set("news",s);
                connection.expire("news",300);

                return products;
            }else {
                // 存在直接返回

                return new ObjectMapper().readValue(news, List.class);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            // 关闭连接
            if (connection != null){
                connection.close();
            }
        }

    }

    public List<Product> findHots(){
//        ProductDao productDao = new ProductDao();
//        List<Product> hots = productDao.findHots();
//        return hots;
        // 加入到redis缓存
        Jedis connection = null;

        // 获取连接
        try {
            connection = RedisUtil.getConnection();
            String hots = connection.get("hots");
            // 查询键是否存在
            if (hots == null){
                // 不存在添加
                System.out.println("不存在 添加商品");
                ProductDao productDao = new ProductDao();
                List<Product> products = productDao.findHots();

                String s = new ObjectMapper().writeValueAsString(products);
                connection.set("hots",s);
                connection.expire("hots",300);

                return products;
            }else {
                // 存在直接返回

                return new ObjectMapper().readValue(hots, List.class);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            // 关闭连接
            if (connection != null){
                connection.close();
            }
        }
    }
}
