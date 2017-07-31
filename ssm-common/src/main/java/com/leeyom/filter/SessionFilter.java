package com.leeyom.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * description: session过期拦截器
 * author: leeyom
 * company: leaderment.com
 * date: 2017/6/15
 */
public class SessionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        //登录界面URL
        String loginUrl = httpRequest.getContextPath() + "/login.jsp";
        String url = httpRequest.getRequestURI();
        String path = url.substring(url.lastIndexOf("/"));
        //登录操作放行
        if ("/checkLogin".equals(path)) {
            chain.doFilter(request, response);
            return;
        }

        //登录界面放行
        if (path.indexOf("/login.jsp")!=-1){
            chain.doFilter(request, response);
            return;
        }

        //静态资源放行
        if (path.indexOf(".png")!=-1 || path.indexOf(".jpg")!=-1 || path.indexOf(".css")!=-1 || path.indexOf(".js")!=-1){
            chain.doFilter(request, response);
            return;
        }


        // 超时处理，ajax请求超时设置超时状态，页面请求超时则返回提示并重定向
        if (session.getAttribute("loginUser") == null) {
            //判断是否是ajax请求
            if (httpRequest.getHeader("x-requested-with") != null
                    && httpRequest.getHeader("x-requested-with")
                    .equalsIgnoreCase("XMLHttpRequest")) {
                //重定向到登录界面
                httpResponse.addHeader("sessionstatus", "timeOut");
                httpResponse.addHeader("loginPath", loginUrl);
                chain.doFilter(request, response);
            } else {
                String str = "<script language='javascript'>alert('会话过期,请重新登录');" + "window.top.location.href='" + loginUrl + "';</script>";
                response.setContentType("text/html;charset=UTF-8");
                try {
                    PrintWriter writer = response.getWriter();
                    writer.write(str);
                    writer.flush();
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
