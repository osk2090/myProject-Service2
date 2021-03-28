package com.osk2090.pms.Client.handler;


import com.osk2090.pms.Client.util.Agreement;

public class ClientPrintOneHandler implements Command {

    public ClientPrintOneHandler(ClientAddHandler clientAddHandler) {
        this.clientAddHandler = clientAddHandler;
    }

    ClientAddHandler clientAddHandler;
//    DataOutputStream out;
//    DataInputStream in;

//    public void service() throws Exception {
//        if (Agreement.Agree()) {
//            this.clientAddHandler.service(in, out);
//
//        }
//    }

    @Override
    public void service() throws Exception {
        if (Agreement.Agree()) {
            this.clientAddHandler.service();
        }
    }
}