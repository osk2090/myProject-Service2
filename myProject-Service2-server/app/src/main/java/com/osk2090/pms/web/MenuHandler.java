package com.osk2090.pms.web;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/menu")
public class MenuHandler extends HttpServlet {

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Nike</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Draw</h1>");

        out.println("<p><a href='form.html'>응모자</a></p>");
        out.println("<p><a href='form.html'>관리자</a></p>");
    }
}
