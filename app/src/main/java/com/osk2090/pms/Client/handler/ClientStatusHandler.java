package com.osk2090.pms.Client.handler;


public class ClientStatusHandler {

    public static void statusPannel(AdminWinnerResultHandler adminWinnerResultHandler,
                                    ClientInfoHandler clientInfoHandler) {
        System.out.println("===============================================");
        adminWinnerResultHandler.winnerStatus(clientInfoHandler);
        System.out.printf("현재 가입자: %d 명\n", clientInfoHandler.showClients());
        System.out.println("===============================================");
    }
}