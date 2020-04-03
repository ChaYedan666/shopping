package org.project01.web.servlet;

import com.alipay.api.AlipayApiException;
import org.apache.commons.beanutils.BeanUtils;
import org.project01.auth.Auth;
import org.project01.constants.Global;
import org.project01.domain.*;
import org.project01.service.OrderService;
import org.project01.utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@WebServlet(urlPatterns = "/order")
public class OrderServlet extends BaseServlet {
    // 工厂方法，将接口的方法反射到实现类
    private OrderService orderService = BeanFactory.newInstance(OrderService.class);



    protected void xx(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write("我不需要登录");
    }
    @Auth("ROLE_ADMIN")
    protected void yy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write("我不需要登录");
    }



    // 还有回调的方法（callback）没写，教程没有我服了
    // 下面是复制的
    @Auth
    protected void callback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接受请求
        //先校验 过了
        Map<String, String[]> parameterMap = request.getParameterMap();


        boolean check = AlipayUtil.check(parameterMap);
        if (check) {
            //才会取出订单id 该改订单状态改变已支付
            String oid = request.getParameter("out_trade_no");

            orderService.updateState(oid, Global.ORDER_STATE_YIFUKUAN);

            //response.getWriter().print("付款成功了!");
            //重定向到订单页面
            response.sendRedirect("http://www.project01.com:8020/project01/view/order/info.html?oid=" + oid);
        } else {
            //滚犊子
            response.getWriter().print("滚犊子!!!");
        }
    }


    //这里留空等以后有机会补吧
    @Auth
    protected void toPay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取oid,收货人姓名，地址，电话
        Map<String, String[]> parameterMap = request.getParameterMap();
        Order order = new Order();
        try {
            BeanUtils.populate(order,parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 调用service里面的方法，进行数据库更新
        orderService.updateSHR(order);
        // 接入支付宝
        try {
            String s = AlipayUtil.generateAlipayTradePagePayRequestForm(order.getOid(), "测试购买的商品", 0.01);
            response.getWriter().print(s);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }


    }
    @Auth
    protected void generate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查是否登录
        User user = (User) request.getSession().getAttribute("user");
//        if (user == null){
//            nologin();
//            return;
//        }

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

        success(oid);

    }

    protected void info(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取oid
        String oid = request.getParameter("oid");
        // 通过oid查询订单详情和订单项详情
        Order order = orderService.findByOidWithItems(oid);

        success(order);

    }
    @Auth
    protected void myOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 通过session获取用户的信息
        User user = (User) request.getSession().getAttribute("user");
//        //判断是否登录
//        if (user == null){
//            nologin();
//            return;
//        }

        // 通过url获取当前页面的页数
        String pn = request.getParameter("pageNumber");
        System.out.println(pn);
        int pageNumber = GlobalUtil.mustInt(pn, 1);
        // 设置每页显示的条数
        int pageSize = 2;
        // 通过uid去查找订单信息
        List<Order> orders = orderService.findOrderByUid(user.getUid(),pageNumber,pageSize);
        // 设置分页功能
        PageBean<Order> pageBean = new PageBean<>();
        pageBean.setData(orders);

        pageBean.setPageNumber(pageNumber);
        pageBean.setPageSize(pageSize);

        // 查询获得总页数并设置
        int total = orderService.countByUid(user.getUid());
        pageBean.setTotal(total);

        // 返回页面信息

        success(pageBean);

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
