package com.osk2090.pms.web;

import com.osk2090.pms.domain.Client;
import com.osk2090.pms.service.ClientService;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@SuppressWarnings("serial")
@WebServlet("/client/detail")
public class ClientDetailHandler extends GenericServlet {
    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {

        ClientService clientService = (ClientService) request.getServletContext().getAttribute("clientService");
        response.setContentType("text/plain;charset=UTF-8");

        PrintWriter out = response.getWriter();
        int no = Integer.parseInt(request.getParameter("no"));

        out.println("[응모자 상세보기]");

        try {
            Client c = clientService.get(no);
            if (c == null) {
                out.println("해당 번호의 응모자가 없습니다.");
                return;
            }
            //이름: %s 전화번호: %s 생년월일: %s 아이디: %s 사이즈:
            out.printf("이름: %s\n", c.getName());
            out.printf("전화번호: %s\n", c.getPhone_number());
            out.printf("생년월일: %s\n", c.getBirth_number());
            out.printf("NIKE 아이디: %s\n", c.getId());
            out.printf("사이즈: %s\n", c.getcSize());

        } catch (Exception e) {
            StringWriter strWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(strWriter);
            e.printStackTrace();

            out.println(strWriter.toString());
        }
    }
}