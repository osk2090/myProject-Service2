package com.osk2090.pms.Client.handler;

import com.osk2090.pms.Client.domain.Client;
import com.osk2090.pms.Client.util.Prompt;

import java.util.List;

public class ClientSizeCheckHandler {

    Client client;

    public ClientSizeCheckHandler(List<Client> clientList) {
        this.client = (Client) clientList;
    }

    static void finSizeCheck(Client c, int mySize) {
        boolean run = true;
        while (run) {
            mySize = Prompt.promptInt("사이즈 선택:");
            if (sizeCheck(mySize) != -1) {
                System.out.println("사이즈 확인됨");
                c.setcSize(mySize);
                clientList.add(c);//최종저장
                System.out.println("응모에 참여해주셔서 감사합니다.");
                run = false;
            } else if (sizeCheck(mySize) == -1) {
                System.out.println("없는 사이즈 입니다.");
            }
        }
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

    @Override
    public void service() throws CloneNotSupportedException {

    }
}