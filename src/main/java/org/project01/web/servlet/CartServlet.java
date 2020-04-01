package org.project01.web.servlet;

import org.project01.domain.Cart;
import org.project01.domain.CartItem;
import org.project01.domain.Product;
import org.project01.service.ProductService;
import org.project01.utils.GlobalUtil;
import org.project01.utils.RRHolder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/cart")
public class CartServlet extends BaseServlet {
    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 接收pid和count
        String pid = request.getParameter("pid");
        String sCount = request.getParameter("count");
        int count = GlobalUtil.mustInt(sCount, 1);
        // 查询库存，判断数量问题，大于10认为库存不足
        if (count > 10){
            fail("库存不足");
            return;
        }
        // 将购物信息添加到购物车中
        // 获取自己的购物车
        Cart cart = getCart();
        // 通过Pid查询商品，与count组合成一个CartItem对象
        Product byId = new ProductService().findById(pid);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(byId);
        cartItem.setCount(count);
        // 添加到购物车
        cart.addItems(cartItem);

        // 返回成功信息
        success();
    }

    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取购物车
        Cart cart = getCart();
        // 返回购物车
        success(cart);
    }

    protected void delItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取购物车
        Cart cart = getCart();
        // 获取pid
        String pid = request.getParameter("pid");
        // 移除
        cart.removeItem(pid);
        // 返回购物车
        success();
    }

    protected void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取购物车
        Cart cart = getCart();
        // 移除
        cart.clear();
        // 返回购物车
        success();
    }


    public Cart getCart(){
        HttpServletRequest request = RRHolder.getRequest();
        HttpSession session = request.getSession();
        Cart cart = (Cart)session.getAttribute("cart");
        // 如果以前没有购物车，新建一个返回，如果有，取出返回
        if (cart == null){
            cart = new Cart();
            session.setAttribute("cart",cart);
        }
        return cart;
    }

}
