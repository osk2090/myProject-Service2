package com.osk2090.pms.Client.handler;


import com.osk2090.pms.Client.domain.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.List;

public class ClientPrintTwoHandler implements Command {


    public ClientPrintTwoHandler(List<Client> clientList,
                                 AdminCheckResultHandler adminCheckResultHandler,
                                 AdminLogicHandler adminLogicHandler,
                                 ClientInfoHandler clientInfoHandler,
                                 ClientListHandler clientListHandler,
                                 AdminWinnerResultHandler adminWinnerResultHandler) {
        this.adminCheckResultHandler = adminCheckResultHandler;
        this.adminLogicHandler = adminLogicHandler;
        this.clientInfoHandler = clientInfoHandler;
        this.clientListHandler = clientListHandler;
        this.adminWinnerResultHandler = adminWinnerResultHandler;
    }


    AdminCheckResultHandler adminCheckResultHandler;
    AdminLogicHandler adminLogicHandler;
    ClientInfoHandler clientInfoHandler;
    ClientListHandler clientListHandler;
    AdminWinnerResultHandler adminWinnerResultHandler;

//    public void service() throws CloneNotSupportedException {
//        int check = adminCheckResultHandler.checkResult();
//        if (check == 0) {
//            adminLogicHandler.adminLogic(clientInfoHandler, clientListHandler, adminWinnerResultHandler);
//        } else {
//            System.out.println("관리자의 아이디와 비밀번호를 확인해주세요.");
//        }
//    }


    @Override
    public void service(DataInputStream in, DataOutputStream out) throws Exception {
        int check = adminCheckResultHandler.checkResult();
        if (check == 0) {
            adminLogicHandler.adminLogic(clientInfoHandler, clientListHandler, adminWinnerResultHandler);
        } else {
            System.out.println("관리자의 아이디와 비밀번호를 확인해주세요.");
        }
    }
}