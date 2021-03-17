package com.osk2090.pms.Client.handler;

import com.osk2090.pms.Client.domain.Client;
import com.osk2090.pms.Client.util.Prompt;

import java.util.List;

public class ClientSizeCheckHandler extends AbstractAdminHandler {

    Client client;
    List<Client> clientList;

    public ClientSizeCheckHandler(List<Client> clientList, Client client) {
        super(clientList);
        this.client = client;
    }

    static void finSizeCheck(Client c, int mySize, List<Client> clientList,) {
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

    static int sizeCheck(int mySize, ClientAddHandler clientAddHandler) {
        while (true) {
            for (int i = 0; i < clientAddHandler.SHOE_SIZE.length; i++) {
                if (clientAddHandler.SHOE_SIZE[i] == mySize) {
                    return mySize;
                }
            }
            return -1;
        }
    }
}