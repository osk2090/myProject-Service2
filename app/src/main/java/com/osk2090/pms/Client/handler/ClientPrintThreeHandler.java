package com.osk2090.pms.Client.handler;


import com.osk2090.pms.Client.domain.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.List;

public class ClientPrintThreeHandler extends AbstractAdminHandler implements Command {

    public ClientPrintThreeHandler(List<Client> clientList, ClientInfoHandler clientInfoHandler,
                                   AdminWinnerCheckHandler adminWinnerCheckHandler) {
        super(clientList);
        this.clientInfoHandler = clientInfoHandler;
        this.adminWinnerCheckHandler = adminWinnerCheckHandler;
    }

    ClientInfoHandler clientInfoHandler;
    AdminWinnerCheckHandler adminWinnerCheckHandler;

//    public void service() throws Exception {
//
//    }


    @Override
    public void service(DataInputStream in, DataOutputStream out) throws Exception {
        if (this.clientInfoHandler.showClients() == 0) {
            System.out.println("입력된 응모자가 없습니다.");
        } else {
            Client c = this.clientInfoHandler.getInfo(this.adminWinnerCheckHandler.getR());
            this.adminWinnerCheckHandler.winnerCheck(
                    c.getName(), c.getId(), c.getcSize(), c.getNo(), this.clientInfoHandler);
        }
    }
}