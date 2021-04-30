package com.osk2090.pms.handler;

import com.osk2090.pms.domain.Client;
import com.osk2090.pms.service.ClientService;
import com.osk2090.stereotype.Component;
import com.osk2090.util.Prompt;
import com.osk2090.util.concurrent.CommandRequest;
import com.osk2090.util.concurrent.CommandResponse;

import java.io.PrintWriter;

@Component(value = "/client/add")
public class ClientAddHandler implements Command {//완료

    static int[] SHOE_SIZE = {250, 255, 260, 265, 270, 275, 280, 285, 290, 300};
    int mySize = 0;
    ClientService clientService;

    public ClientAddHandler(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void service(CommandRequest request, CommandResponse response) throws Exception {
        PrintWriter out = response.getWriter();
        Prompt prompt = request.getPrompt();

        out.println("[응모자 등록]");

        Client c = new Client();

        c.setName(prompt.promptString("정보입력\n응모자 이름: "));
        prompt.booleanResult_PN(c, "응모자 전화번호\n예)01012345678-11자리:");
        prompt.booleanResult_BN(c, "응모자 생년월일\n예)900101-6자리:");
        c.setId(prompt.promptString("나이키 닷컴 아이디를 기재해주세요.\n*나이키 멤버만 구매 가능합니다."));
        out.println("NIKE DUNK LOW RETRO (DD1390-100)");
        out.println("금액: 129.000 krw");
        for (int i = 0; i < SHOE_SIZE.length; i++) {
            if (i % 5 == 0) {
                System.out.println();//두줄로 나누기
            }
            out.print(SHOE_SIZE[i] + " ");
        }
        System.out.println();
        c.setcSize(finSizeCheck(c, mySize, request, response));

        clientService.add(c);

        out.println("응모에 참여해주셔서 감사합니다.");
    }


    static int finSizeCheck(Client c, int mySize, CommandRequest request, CommandResponse response) throws Exception {
        PrintWriter out = response.getWriter();
        Prompt prompt = request.getPrompt();

        boolean run = true;
        while (run) {
            mySize = prompt.promptInt("사이즈 선택:");
            if (sizeCheck(mySize) == -1) {
                out.println("없는 사이즈 입니다.");
                continue;
            }
            out.println("사이즈 확인됨");
            c.setcSize(mySize);
            out.println("응모자를 등록하였습니다.");
            run = false;
        }
        return mySize;
    }

    static int sizeCheck(int mySize) {
        while (true) {
            for (int i = 0; i < SHOE_SIZE.length; i++) {
                if (SHOE_SIZE[i] == mySize) {
                    return mySize;
                }
            }
            return -1;
        }
    }
}