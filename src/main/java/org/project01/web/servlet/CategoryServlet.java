package org.project01.web.servlet;

import org.project01.domain.Category;
import org.project01.service.impl.CategoryServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(urlPatterns = "/category")
public class CategoryServlet extends BaseServlet {

    protected void list(HttpServletRequest request,HttpServletResponse response) {
        // header栏的分类
        // 调用service查询数据
        CategoryServiceImpl categoryServiceImpl = new CategoryServiceImpl();
        List<Category> all = categoryServiceImpl.findAll();

        // 返回数据，包装为vo对象
        success(all);

    }
}
