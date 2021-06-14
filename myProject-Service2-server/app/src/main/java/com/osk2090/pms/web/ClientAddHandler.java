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
@WebServlet("/client/add")
public class ClientAddHandler extends HttpServlet {
    //client/add?name=osk&pn=11111111111&bn=222222&id=ooo&size=275

//    static int[] SHOE_SIZE = {250, 255, 260, 265, 270, 275, 280, 285, 290, 300};
//    int mySize = 0;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ClientService clientService = (ClientService) request.getServletContext().getAttribute("clientService");

        Client c = new Client();

        response.setCharacterEncoding("UTF-8");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("[응모자 등록]");

        out.println("개인정보 수집 및 이용에 동의합니다.[Y/N]");
        out.println("당첨 되실 경우 당첨자 본인만 당첨 매장에 방문하여 수령 가능합니다.[Y/N]");
        out.println("당첨자는 본인 확인을 위해 신분증 및 핸드폰으로 받으신 SMS(원본)을 확인하오니 지참해주시기 바랍니다.[Y/N]");
        out.println("당첨자는 공지 드리는 구매 기간 외에는 구매 불가하며, 카드 결제시 본인 명의 카드로만 결제가 가능합니다.[Y/N]");
        out.println("다음 진행");

        c.setName(request.getParameter("name"));//정보입력응모자 이름:
        c.setPhone_number(request.getParameter("pn"));
        c.setBirth_number(request.getParameter("bn"));
        c.setId(request.getParameter("id"));//나이키 닷컴 아이디를 기재해주세요.나이키 멤버만 구매 가능합니다.
        c.setcSize(request.getParameter("size"));
//            c.setcSize(finSizeCheck(c, mySize, request, response));
<<<<<<< HEAD
            out.println("1111111111");
            out.println(clientService);
=======

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>응모자 등록</title>");

        try {
>>>>>>> c7c516275d115e769b4fc5eaf3caef111207169a
            clientService.add(c);
            out.println("<meta http-equiv='Refresh' content='1;url=list'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>게시글 등록</h1>");
            out.println("<p>응모를 완료하였습니다.</p>");
            out.println("<p1>응모에 참여해주셔서 감사합니다.</p1>");
        } catch (Exception e) {
            StringWriter strWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(strWriter);
            e.printStackTrace(printWriter);

            out.println("</head>");
            out.println("<body>");
            out.println("<h1>응모자 등록 오류</h1>");
            out.printf("<pre>%s</pre>\n", strWriter.toString());
            out.println("<p><a href='list'>목록</a></p>");
        }

        out.println("</body>");
        out.println("</html>");
    }


//    static int finSizeCheck(Client c, int mySize, ServletRequest request, ServletResponse response) throws Exception {
//        PrintWriter out = response.getWriter();
//        response.setContentType("text/plain;charset=UTF-8");
//
//        boolean run = true;
//        while (run) {
////            mySize = prompt.promptInt("사이즈 선택:");
////            if (sizeCheck(mySize) == -1) {
////                out.println("없는 사이즈 입니다.");
////                continue;
////            }
//            out.println("사이즈 확인됨");
//            c.setcSize(Integer.parseInt(request.getParameter("size")));
//            out.println("응모자를 등록하였습니다.");
//            run = false;
//        }
//        return mySize;
//    }
//
//    static int sizeCheck(int mySize) {
//        while (true) {
//            for (int i = 0; i < SHOE_SIZE.length; i++) {
//                if (SHOE_SIZE[i] == mySize) {
//                    return mySize;
//                }
//            }
//            return -1;
//        }
//    }
//
//    public static void booleanResult_PN(Client c, ServletRequest request, ServletResponse response) {
//        boolean check = true;
//        response.setContentType("text/plain;charset=UTF-8");
//
//        while (check) {
//            c.setPhone_number(request.getParameter("pn"));
//            if (Prompt.PhoneNumberCheck(c.getPhone_number())) {
//                System.out.println("번호길이 맞습니다.");
//                check = false;
//            } else {
//                System.out.println("번호 길이가 맞지않거나 숫자가 아닙니다.");
//            }
//        }
//    }
//
//    public static void booleanResult_BN(Client c, ServletRequest request, ServletResponse response) {
//
//        boolean check = true;
//        response.setContentType("text/plain;charset=UTF-8");
//
//        while (check) {
//            c.setBirth_number(request.getParameter("bn"));
//            if (Prompt.BirthNumberCheck(c.getBirth_number())) {
//                System.out.println("생년월일 길이 맞습니다.");
//                check = false;
//            } else {
//                System.out.println("생년월일 길이가 맞지않거나 숫자가 아닙니다.");
//            }
//        }
//    }
}