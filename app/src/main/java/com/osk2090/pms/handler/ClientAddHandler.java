package com.osk2090.pms.handler;

import com.osk2090.pms.dao.ClientDao;
import com.osk2090.pms.domain.Client;
import com.osk2090.util.Prompt;

public class ClientAddHandler implements Command {//완료

    static int[] SHOE_SIZE = {250, 255, 260, 265, 270, 275, 280, 285, 290, 300};
    int mySize = 0;
    ClientDao clientDao;

    public ClientAddHandler(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public void service() throws Exception {
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
        c.setcSize(finSizeCheck(c, mySize));

        clientDao.insert(c);

        System.out.println("응모에 참여해주셔서 감사합니다.");
    }


    static int finSizeCheck(Client c, int mySize) throws Exception {
        boolean run = true;
        while (run) {
            mySize = Prompt.promptInt("사이즈 선택:");
            if (sizeCheck(mySize) == -1) {
                System.out.println("없는 사이즈 입니다.");
                continue;
            }
            System.out.println("사이즈 확인됨");
            c.setcSize(mySize);
            System.out.println("응모자를 등록하였습니다.");
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