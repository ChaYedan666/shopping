package org.project01.web.servlet;

import org.apache.commons.beanutils.BeanUtils;
import org.project01.constants.Global;
import org.project01.domain.*;
import org.project01.service.OrderService;
import org.project01.utils.BeanFactory;
import org.project01.utils.RRHolder;
import org.project01.utils.UUIDUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@WebServlet(urlPatterns = "/order")
public class OrderServlet extends BaseServlet {
    // 工厂方法，将接口的方法反射到实现类
    private OrderService orderService = BeanFactory.newInstance(OrderService.class);

    protected void generate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            nologin();
            return;
        }

        // 检查购物车是否为空
        Cart cart = getCart();
        if (cart.getItems().size() == 0){
            fail("购物车空空如也");
            return;
        }
        // 生成订单
        Order order = new Order();
        // 将购物车取出
        String oid = UUIDUtil.getId();
        order.setOid(oid);
        order.setOrdertime(new Date());
        order.setTotal(cart.getTotal());
        order.setUid(user.getUid());
        order.setState(Global.ORDER_STATE_WEIFUKUAN);
        // 订单项
        Collection<CartItem> items = cart.getItems();

        ArrayList<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : items) {
            OrderItem orderItem = new OrderItem();

            orderItem.setOid(oid);
            orderItem.setPid(item.getProduct().getPid());
            orderItem.setCount(item.getCount());
            orderItem.setSubTotal(item.getSubtotal());
            // 组装完成 添加到新集合
            orderItems.add(orderItem);
        }
        // 调用service方法
        orderService.save(order,orderItems);





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
