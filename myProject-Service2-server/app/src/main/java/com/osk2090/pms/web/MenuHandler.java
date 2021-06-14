package com.osk2090.pms.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
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

        ServletContext ctx = request.getServletContext();
        String path = ctx.getRealPath("/nike.jpg");
        System.out.println(path);

        FileInputStream in = new FileInputStream(path);

        response.setContentType("image/jpeg");
        BufferedOutputStream drop = new BufferedOutputStream(response.getOutputStream());

        int b;
        while ((b = in.read()) != -1) {
            drop.write(b);
        }
        drop.flush();
        drop.close();
        in.close();

        out.println("<h1>Draw</h1>");

        out.println("<p><a href='form.html'>응모자</a></p>");
        out.println("<p><a href='form.html'>관리자</a></p>");
    }
}
