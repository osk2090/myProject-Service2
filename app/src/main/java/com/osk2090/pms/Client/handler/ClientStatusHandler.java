package com.osk2090.pms.Client.handler;

import com.osk2090.edit_ver.draw.domain.Client;

import java.util.List;

public class ClientStatusHandler extends AbstractClientHandler {

    public ClientStatusHandler(List<Client> clientList) {
        super(clientList);
    }

    public static void statusPannel(com.osk2090.edit_ver.draw.Handler.AdminWinnerResultHandler adminWinnerResultHandler,
                                    com.osk2090.edit_ver.draw.Handler.ClientInfoHandler clientInfoHandler) {
        System.out.println("===============================================");
        adminWinnerResultHandler.winnerStatus(clientInfoHandler);
        System.out.printf("현재 가입자: %d 명\n", clientInfoHandler.showClients());
        System.out.println("===============================================");
    }

    @Override
    public void service() throws CloneNotSupportedException {

    }
}