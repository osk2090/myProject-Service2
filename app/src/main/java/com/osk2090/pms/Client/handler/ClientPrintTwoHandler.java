package com.osk2090.pms.Client.handler;


public class ClientPrintTwoHandler implements Command {


    public ClientPrintTwoHandler(AdminCheckResultHandler adminCheckResultHandler,
//                                 AdminLogicHandler adminLogicHandler,
                                 ClientListHandler clientListHandler,
                                 AdminWinnerResultHandler adminWinnerResultHandler) {
        this.adminCheckResultHandler = adminCheckResultHandler;
//        this.adminLogicHandler = adminLogicHandler;
        this.clientListHandler = clientListHandler;
        this.adminWinnerResultHandler = adminWinnerResultHandler;
    }

    AdminCheckResultHandler adminCheckResultHandler;
    AdminLogicHandler adminLogicHandler;
    ClientListHandler clientListHandler;
    AdminWinnerResultHandler adminWinnerResultHandler;

    @Override
    public void service() throws Exception {
        int check = adminCheckResultHandler.checkResult();
        if (check == 0) {
            adminLogicHandler.adminLogic(clientListHandler, adminWinnerResultHandler);
        } else {
            System.out.println("관리자의 아이디와 비밀번호를 확인해주세요.");
        }
    }
}