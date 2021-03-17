package com.osk2090.pms.Client.handler;

import com.osk2090.edit_ver.draw.domain.Client;

import java.util.List;

public class ClientPrintThreeHandler extends AbstractAdminHandler {

    public ClientPrintThreeHandler(List<Client> clientList,ClientInfoHandler clientInfoHandler,
                                   AdminWinnerCheckHandler adminWinnerCheckHandler) {
        super(clientList);
        this.clientInfoHandler = clientInfoHandler;
        this.adminWinnerCheckHandler = adminWinnerCheckHandler;
    }

    ClientInfoHandler clientInfoHandler;
    AdminWinnerCheckHandler adminWinnerCheckHandler;

    public void service() throws CloneNotSupportedException {
        if (this.clientInfoHandler.showClients() == 0) {
            System.out.println("입력된 응모자가 없습니다.");
        } else {
            Client c = this.clientInfoHandler.getInfo(this.adminWinnerCheckHandler.getR());
            this.adminWinnerCheckHandler.winnerCheck(
                    c.getName(), c.getId(), c.getcSize(), c.getIdx(), this.clientInfoHandler);
        }
    }
}