package org.project01.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.project01.domain.Product;
import org.project01.service.ProductService;
import org.project01.utils.RRHolder;
import org.project01.utils.UUIDUtil;
import org.project01.utils.UploadUtil;
import org.project01.web.vo.ResultVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

@WebServlet(urlPatterns = "/productadd")
public class ProductAddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取参数集合
        Map<String, String[]> parameterMap = UploadUtil.parseRequest(request);
        // 封装对象
        Product product = new Product();
        try {
            BeanUtils.populate(product,parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        product.setPdate(new Date());
        product.setPid(UUIDUtil.getId());
        product.setPflag(1);

        // 调用Service 保存
        ProductService productService = new ProductService();
        productService.save(product);

        // 返回成功信息
        ResultVo resultVo = new ResultVo(ResultVo.CODE_SUCCESS,"");

        String s = null;
        try {
            s = new ObjectMapper().writeValueAsString(resultVo);
            RRHolder.getResponse().getWriter().write(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
