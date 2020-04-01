package org.project01.web.servlet;

import org.project01.domain.Category;
import org.project01.domain.PageBean;
import org.project01.domain.Product;
import org.project01.service.impl.CategoryServiceImpl;
import org.project01.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/product")
public class ProductServlet extends BaseServlet {

    protected void page (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 分类下的商品展示
        // 获取CID
        String cid = request.getParameter("cid");
        int pageNumber = 1;
        // 防止有人写字母来让程序崩溃
        try {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }catch (Exception e){
            System.out.println("你想干什么，小老弟");
        }
        int pageSize=6;
        // 调用service的函数获取数据
        ProductService productService = new ProductService();
        List<Product> byPageWithCid = productService.findByPageWithCid(cid, pageNumber, pageSize);

        // 下方分页栏
        // 获取总个数
        int count = productService.count(cid);
        // 将总个数，一页多少个，当前页码，和查出来的当前页商品数据传给PageBean
        PageBean<Product> pb = new PageBean<>();
        pb.setData(byPageWithCid);
        pb.setPageSize(pageSize);
        pb.setPageNumber(pageNumber);
        pb.setTotal(count);



        success(pb);

    }



    protected void info (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");

        //通过PID查询商品信息
        ProductService productService = new ProductService();
        Product product = productService.findById(pid);
        //获取分类信息
        String cid = product.getCid();
        CategoryServiceImpl categoryServiceImpl = new CategoryServiceImpl();
        Category category = categoryServiceImpl.findById(cid);
        //将商品信息和分类信息组合发送
        HashMap<Object, Object> map = new HashMap<>();
        map.put("category",category);
        map.put("product",product);

        success(map);
    }

    protected void hotandnews (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService productService = new ProductService();

        List<Product> news = productService.findNews();
        List<Product> hots = productService.findHots();

        Map map = new HashMap<>();

        map.put("news",news);
        map.put("hots",hots);

        success(map);

    }

//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//
//    }
}
