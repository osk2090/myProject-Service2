package com.osk2090.pms.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LogincheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain nextChain) throws IOException, ServletException {

        //파라미터로 받은 request를 본해 타입으로 형변환한다
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String servletPath = httpRequest.getServletPath().toLowerCase();

        if (servletPath.endsWith("add") ||
                servletPath.endsWith("update") ||
                servletPath.endsWith("delete")) {

            if (httpRequest.getSession().getAttribute("loginUser") == null) {
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().println("로그인 해야 합니다.");
                return;
            }
        }
        nextChain.doFilter(request, response);
    }
}
