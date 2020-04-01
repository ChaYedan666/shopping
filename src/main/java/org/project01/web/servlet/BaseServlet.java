package org.project01.web.servlet;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project01.utils.RRHolder;
import org.project01.web.vo.ResultVo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;


public abstract class BaseServlet extends HttpServlet {

    public void success() {
        try {
            success(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void success(Object data){
        ResultVo resultVo = new ResultVo(ResultVo.CODE_SUCCESS,data,"");

        String s = null;
        try {
            s = new ObjectMapper().writeValueAsString(resultVo);
            RRHolder.getResponse().getWriter().write(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void fail() {
        try {
            fail(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fail(Object data)  {
        ResultVo resultVo = new ResultVo(ResultVo.CODE_FAILED,data,"");

        String s = null;
        try {
            s = new ObjectMapper().writeValueAsString(resultVo);
            RRHolder.getResponse().getWriter().write(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void nologin() {
        try {
            nologin(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nologin(Object data)  {
        ResultVo resultVo = new ResultVo(ResultVo.CODE_NOLOGIN,data,"");

        String s = null;
        try {
            s = new ObjectMapper().writeValueAsString(resultVo);
            RRHolder.getResponse().getWriter().write(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //获取你要访问我哪个功能 参数值
        String md = request.getParameter("md");


        //反射获取方法  并且执行
        //getDeclaredMethod() 用来获取某一个类方法
        //参数1  方法名
        //参数2  形参类型列表
        try {
            Method method = this.getClass().getDeclaredMethod(md, HttpServletRequest.class, HttpServletResponse.class);

            //执行方法
            method.invoke(this,request,response);


        } catch (Exception e) {
//            e.printStackTrace();
//
//            throw new RuntimeException(e);
            // 不能抛给用户了
            ResultVo resultVo = new ResultVo(ResultVo.CODE_SERVER_ERROR,"","不好意思，服务器出现了异常");
            String s = new ObjectMapper().writeValueAsString(resultVo);
            RRHolder.getResponse().getWriter().print(s);
        }
    }

}
