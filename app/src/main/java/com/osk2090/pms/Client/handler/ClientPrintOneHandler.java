package com.osk2090.pms.Client.handler;


import com.osk2090.pms.Client.domain.Client;
import com.osk2090.pms.Client.util.Agreement;

import java.util.List;

public class ClientPrintOneHandler {
    List<Client> clientList;

    public ClientPrintOneHandler(List<Client> clientList, ClientAddHandler clientAddHandler) {
        this.clientList = clientList;
        this.clientAddHandler = clientAddHandler;
    }

    ClientAddHandler clientAddHandler;

    public void service() throws CloneNotSupportedException {
        if (Agreement.Agree()) {
            this.clientAddHandler.service();
        }
    }
}