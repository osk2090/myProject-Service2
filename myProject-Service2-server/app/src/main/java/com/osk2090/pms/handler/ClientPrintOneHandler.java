package com.osk2090.pms.handler;


import com.osk2090.util.Agreement;
import com.osk2090.util.concurrent.CommandRequest;
import com.osk2090.util.concurrent.CommandResponse;

public class ClientPrintOneHandler implements Command {

    public ClientPrintOneHandler(ClientAddHandler clientAddHandler) {
        this.clientAddHandler = clientAddHandler;
    }

    ClientAddHandler clientAddHandler;

    @Override
    public void service(CommandRequest request, CommandResponse response) throws Exception {
        if (Agreement.Agree()) {
            this.clientAddHandler.service(request, response);
        }
    }
}