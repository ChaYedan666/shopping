package org.project01.web.servlet;

import org.project01.domain.Category;
import org.project01.exceptions.CategoryHasProductException;
import org.project01.service.CategoryService;
import org.project01.service.impl.CategoryServiceImpl;
import org.project01.utils.UUIDUtil;

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

    protected void add(HttpServletRequest request,HttpServletResponse response) {
        // 添加目录
        String cname = request.getParameter("cname");
        System.out.println(cname);
        // 验证是否合法
        if (cname == null || cname.trim().length() < 2 || cname.trim().length() > 6){
            fail("长度必须在2和6之间");
            return;
        }
        // 封装对象，保存新加的目录名
        Category category = new Category();
        category.setCname(cname);
        category.setCid(UUIDUtil.getId());

        CategoryServiceImpl categoryServiceImpl = new CategoryServiceImpl();
        categoryServiceImpl.save(category);

    }

    protected void findByCid(HttpServletRequest request,HttpServletResponse response) {
        String cid = request.getParameter("cid");
        Category byId = new CategoryServiceImpl().findById(cid);

        success(byId);
    }

    protected void update(HttpServletRequest request,HttpServletResponse response) {
        // 修改目录名
        String cid = request.getParameter("cid");
        String cname = request.getParameter("cname");

        if (cname == null || cname.trim().length() < 2 || cname.trim().length() > 6){
            fail("长度必须在2和6之间");
            return;
        }
        Category category = new Category();
        category.setCname(cname);
        category.setCid(cid);

        // 修改
        CategoryServiceImpl categoryService = new CategoryServiceImpl();
        categoryService.update(category);

        success();


    }

    protected void del(HttpServletRequest request,HttpServletResponse response) {
        String cid = request.getParameter("cid");
        try {
            new CategoryServiceImpl().del(cid);
            success();
        } catch (CategoryHasProductException e) {
            e.printStackTrace();
            fail("该分类有商品数据，无法删除");
        }


    }

}
