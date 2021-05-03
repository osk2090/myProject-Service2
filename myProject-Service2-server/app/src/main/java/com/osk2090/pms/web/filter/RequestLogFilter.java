package com.osk2090.pms.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestLogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain nextChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        System.out.printf("[%s:%d] %s\n", httpRequest.getRemoteAddr(), httpRequest.getRemotePort(), httpRequest.getServletPath());
        nextChain.doFilter(request, response);
    }
}
