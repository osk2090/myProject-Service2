package com.osk2090.pms.web;

import com.osk2090.pms.domain.Client;
import com.osk2090.pms.service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@SuppressWarnings("serial")
@WebServlet("/client/delete")
public class ClientDeleteHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClientService clientService = (ClientService) request.getServletContext().getAttribute("clientService");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>응모자 삭제</title>");


        try {
            int no = Integer.parseInt(request.getParameter("no"));

            Client oldClient = clientService.get(no);
            if (oldClient == null) {
                throw new Exception("해당 번호의 게시글이 없습니다.");
            }

            clientService.delete(no);
            out.println("<meta http-equiv='Refresh' content='1;url=list'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>게시글 삭제</h1>");
            out.println("<p>응모자를 삭제하였습니다.</p>");
        } catch (Exception e) {
            StringWriter strWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(strWriter);
            e.printStackTrace(printWriter);

            out.println("</head>");
            out.println("<body>");
            out.println("<h1>게시글 삭제 오류</h1>");
            out.printf("<p>%s</p>\n", e.getMessage());
            out.printf("<pre>%s</pre>\n", strWriter.toString());
            out.println("<p><a href='list'>목록</a></p>");
        }
        out.println("</body>");
        out.println("</html>");
    }
}