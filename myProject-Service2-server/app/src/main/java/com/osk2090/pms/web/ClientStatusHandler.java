package com.osk2090.pms.web;


import com.osk2090.pms.dao.ClientDao;

public class ClientStatusHandler {

    public static void statusPannel(AdminWinnerResultHandler adminWinnerResultHandler, ClientDao clientDao) throws Exception {

        System.out.println("===============================================");
        adminWinnerResultHandler.winnerStatus(clientDao);
        System.out.printf("현재 가입자: %d 명\n", ClientInfoHandler.showCountClients());
        System.out.println("===============================================");
    }
}