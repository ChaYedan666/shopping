package org.project01.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/testcookie")
public class CookieServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("text/html;charset=utf-8");
//        request.setCharacterEncoding("utf-8");

        String username = request.getParameter("username");
        System.out.println("前端传来参数:"+username);

        //尝试获取
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length>0){
            for (Cookie cookie : cookies) {
                System.out.println("cookie的名字:"+cookie.getName()+"-----cookie的值:"+cookie.getValue());
            }
        }


        //设置一个cookie
        Cookie cookie = new Cookie("name", "xyz");
        cookie.setMaxAge(60*60);

        cookie.setPath("/");

        response.addCookie(cookie);




        response.getWriter().print("测试cookie");

    }
}
