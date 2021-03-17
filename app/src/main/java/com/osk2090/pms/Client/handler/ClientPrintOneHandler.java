package com.osk2090.pms.Client.handler;

import com.osk2090.edit_ver.draw.Agreement;
import com.osk2090.edit_ver.draw.domain.Client;

import java.util.List;

public class ClientPrintOneHandler extends AbstractAdminHandler {

    public ClientPrintOneHandler(List<Client> clientList, ClientAddHandler clientAddHandler) {

        super(clientList);
        this.clientAddHandler = clientAddHandler;
    }

    ClientAddHandler clientAddHandler;

    @Override
    public void service() throws CloneNotSupportedException {
        if (Agreement.Agree()) {
            this.clientAddHandler.add();
        }
    }
}