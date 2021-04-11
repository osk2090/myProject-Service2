package com.osk2090.pms.handler;


public class ClientStatusHandler {

    public static void statusPannel(AdminWinnerResultHandler adminWinnerResultHandler) throws Exception {

        System.out.println("===============================================");
        adminWinnerResultHandler.winnerStatus();
        System.out.printf("현재 가입자: %d 명\n", ClientInfoHandler.showCountClients());
        System.out.println("===============================================");
    }
}