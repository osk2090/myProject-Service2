package com.osk2090.pms.Client.handler;

import com.osk2090.pms.Client.domain.Client;
import com.osk2090.pms.Client.util.Prompt;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ClientAddHandler implements Command {

    //신발 출력할때 줄을 나열해서 출력하기
    int[] SHOE_SIZE = {250, 255, 260, 265, 270, 275, 280, 285, 290, 300};//신발사이즈
    int mySize = 0;

    @Override
    public void service(DataInputStream in, DataOutputStream out) throws Exception {

        System.out.println("[응모자 등록]");

        Client c = new Client();

        c.setName(Prompt.promptString("정보입력\n응모자 이름: "));
        Prompt.booleanResult_PN(c, "응모자 전화번호\n예)01012345678-11자리:");
        Prompt.booleanResult_BN(c, "응모자 생년월일\n예)900101-6자리:");
        c.setId(Prompt.promptString("나이키 닷컴 아이디를 기재해주세요.\n*나이키 멤버만 구매 가능합니다."));
        System.out.println("NIKE DUNK LOW RETRO (DD1390-100)");
        System.out.println("금액: 129.000 krw");
        for (int i = 0; i < SHOE_SIZE.length; i++) {
            if (i % 5 == 0) {
                System.out.println();//두줄로 나누기
            }
            System.out.print(SHOE_SIZE[i] + " ");
        }
        System.out.println();
        ClientSizeCheckHandler.finSizeCheck(c, mySize);
    }
}