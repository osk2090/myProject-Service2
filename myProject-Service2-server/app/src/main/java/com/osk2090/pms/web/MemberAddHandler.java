package com.osk2090.pms.web;

import com.osk2090.pms.domain.Client;
import com.osk2090.pms.service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@SuppressWarnings("serial")
@WebServlet("/member/add")
public class MemberAddHandler extends HttpServlet {
    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        ClientService clientService = (ClientService) request.getServletContext().getAttribute("clientService");

        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            out.println("[Nike 회원 등록]");

            Client client = new Client();
            client.setId(request.getParameter("id"));
            client.setName(request.getParameter("name"));
            client.setPassword(request.getParameter("password"));
            client.setBirth(request.getParameter("birth"));
            client.setTel(request.getParameter("tel"));

            clientService.add(client);
            out.println("Nike 회원이 되신걸 환영합니다!");
        } catch (Exception e) {
            StringWriter strWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(strWriter);
            e.printStackTrace(printWriter);
            out.println(strWriter.toString());
        }
    }
}