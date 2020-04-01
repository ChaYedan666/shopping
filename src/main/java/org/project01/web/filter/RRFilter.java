package org.project01.web.filter;

import org.project01.utils.RRHolder;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class RRFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

//        ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<>();
//        requestThreadLocal.set(request);
//
//        ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal<>();
//        responseThreadLocal.set(response);
        RRHolder.setRequest(request);
        RRHolder.setResponse(response);


        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
