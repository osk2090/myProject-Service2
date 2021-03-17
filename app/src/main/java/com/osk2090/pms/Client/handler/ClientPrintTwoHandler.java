package com.osk2090.pms.Client.handler;

import com.osk2090.edit_ver.draw.domain.Client;

import java.util.List;

public class ClientPrintTwoHandler extends AbstractAdminHandler {

    public ClientPrintTwoHandler(List<Client> clientList,
                                 AdminCheckResultHandler adminCheckResultHandler,
                                 AdminLogicHandler adminLogicHandler,
                                 ClientInfoHandler clientInfoHandler,
                                 ClientListHandler clientListHandler,
                                 com.osk2090.edit_ver.draw.Handler.AdminWinnerResultHandler adminWinnerResultHandler) {
        super(clientList);
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
    com.osk2090.edit_ver.draw.Handler.AdminWinnerResultHandler adminWinnerResultHandler;

    @Override
    public void service() throws CloneNotSupportedException {
        int check = adminCheckResultHandler.checkResult();
        if (check == 0) {
            adminLogicHandler.adminLogic(clientInfoHandler,clientListHandler,adminWinnerResultHandler);
        } else {
            System.out.println("관리자의 아이디와 비밀번호를 확인해주세요.");
        }
    }
}