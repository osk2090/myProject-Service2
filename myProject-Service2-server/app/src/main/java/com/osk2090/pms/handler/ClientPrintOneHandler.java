package com.osk2090.pms.handler;


import com.osk2090.util.Agreement;

public class ClientPrintOneHandler implements Command {

    public ClientPrintOneHandler(ClientAddHandler clientAddHandler) {
        this.clientAddHandler = clientAddHandler;
    }

    ClientAddHandler clientAddHandler;

    @Override
    public void service() throws Exception {
        if (Agreement.Agree()) {
            this.clientAddHandler.service();
        }
    }
}