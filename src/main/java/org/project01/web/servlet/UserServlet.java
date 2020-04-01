package org.project01.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.project01.domain.User;
import org.project01.service.UserService;
import org.project01.utils.RRHolder;
import org.project01.utils.UUIDUtil;
import org.project01.web.vo.ResultVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.UUID;

@WebServlet(urlPatterns = "/user")
public class UserServlet extends BaseServlet {




    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();

//        ResultVo resultVo = new ResultVo(ResultVo.CODE_SUCCESS,"");
//
//        String s = new ObjectMapper().writeValueAsString(resultVo);
//        response.getWriter().write(s);
        success();
    }

    protected void currentname(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if (user != null){
            String name = user.getName();

//            ResultVo resultVo = new ResultVo(ResultVo.CODE_SUCCESS,name,"");
//
//            String s = new ObjectMapper().writeValueAsString(resultVo);
//            response.getWriter().write(s);
            success(name);

        }else {
//            ResultVo resultVo = new ResultVo(ResultVo.CODE_FAILED,"未登录状态");
//
//            String s = new ObjectMapper().writeValueAsString(resultVo);
//            response.getWriter().write(s);
            fail();

        }
    }

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.getWriter().println("这是登录功能");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserService userService = new UserService();
        User user = userService.findByUAP(username,password);

        if (user != null){
            // 保存登录状态
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            // 登录成功
//            ResultVo resultVo = new ResultVo(ResultVo.CODE_SUCCESS,"");
//
//            String s = new ObjectMapper().writeValueAsString(resultVo);
//            response.getWriter().write(s);
            success();
        }else{
            // 登陆失败
//            ResultVo resultVo = new ResultVo(ResultVo.CODE_FAILED,"用户名或密码错误","");
//
//            String s = new ObjectMapper().writeValueAsString(resultVo);
//            response.getWriter().write(s);
            fail("用户名或密码错误");
        }

    }


    protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.getWriter().println("这是注册功能");
//        System.out.println("接收到了");
        // 验证用户名
        String username = request.getParameter("username");
        if(username==null||username.trim().length()>6||username.trim().length()<2){
//            ResultVo resultVo = new ResultVo(ResultVo.CODE_FAILED,"username长度不正确","格式不正确");
//
//            String s = new ObjectMapper().writeValueAsString(resultVo);
//            response.getWriter().write(s);
            fail("username长度不正确");
            return;
        }

        // 接收数据
        Map<String, String[]> parameterMap = request.getParameterMap();
        // 封装数据
        User user = new User();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 生成用户id
        user.setUid(UUIDUtil.getId());
        // 发送给service保存
        UserService userService = new UserService();
        userService.save(user);
        // 成功了回送给前端，用JSON数据
//        ResultVo resultVo = new ResultVo(ResultVo.CODE_SUCCESS,"");
//
//        String s = new ObjectMapper().writeValueAsString(resultVo);
//        response.getWriter().write(s);
        success();


    }
}
