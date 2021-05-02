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

    static int[] SHOE_SIZE = {250, 255, 260, 265, 270, 275, 280, 285, 290, 300};
    int mySize = 0;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ClientService clientService = (ClientService) request.getServletContext().getAttribute("clientService");

        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

//        out.println("[응모자 등록]");
//
//        Client c = new Client();
//
//        c.setName(request.getParameter("name"));//정보입력응모자 이름:
////        booleanResult_PN(c, request, response);//"응모자 전화번호\n예)01012345678-11자리:"
////        booleanResult_BN(c, request, response);//"응모자 생년월일\n예)900101-6자리:"
//        c.setPhone_number(request.getParameter("pn"));
//        c.setBirth_number(request.getParameter("bn"));
//        c.setId(request.getParameter("id"));//나이키 닷컴 아이디를 기재해주세요.나이키 멤버만 구매 가능합니다.
//        c.setcSize(Integer.parseInt(request.getParameter("size")));

//        out.println("NIKE DUNK LOW RETRO (DD1390-100)");
//        out.println("금액: 129.000 krw");
//        for (int i = 0; i < SHOE_SIZE.length; i++) {
//            if (i % 5 == 0) {
//                System.out.println();//두줄로 나누기
//            }
//            out.print(SHOE_SIZE[i] + " ");
//        }
//        out.println();

        try {
            out.println("[응모자 등록]");

            Client c = new Client();

            c.setName(request.getParameter("name"));//정보입력응모자 이름:
            c.setPhone_number(request.getParameter("pn"));
            c.setBirth_number(request.getParameter("bn"));
            c.setId(request.getParameter("id"));//나이키 닷컴 아이디를 기재해주세요.나이키 멤버만 구매 가능합니다.
            c.setcSize(Integer.parseInt(request.getParameter("size")));
//            c.setcSize(finSizeCheck(c, mySize, request, response));
            out.println("1111111111");
            clientService.add(c);
            out.println("222222");
            out.println("응모에 참여해주셔서 감사합니다.");
        } catch (Exception e) {
            StringWriter strWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(strWriter);
            e.printStackTrace(printWriter);
            out.println(strWriter.toString());
        }

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