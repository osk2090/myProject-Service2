package com.osk2090.pms.web;

import com.osk2090.pms.domain.Client;
import com.osk2090.pms.service.ClientService;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@WebServlet("/client/list")
public class ClientListHandler implements Servlet {
    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {

        ClientService clientService = (ClientService) request.getServletContext().getAttribute("clientService");
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("응모자 목록");

        try {
            List<Client> list = clientService.list();

            for (Client c : list) {
                out.printf("%d,%s,%s\n",
                        c.getNo(),
                        c.getName(),
                        c.getId());
            }
        } catch (Exception e) {
            StringWriter strWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(strWriter);
            e.printStackTrace(printWriter);
            
            out.println(strWriter.toString());
        }

    }

    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}