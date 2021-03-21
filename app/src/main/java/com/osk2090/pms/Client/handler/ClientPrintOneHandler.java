package com.osk2090.pms.Client.handler;


import com.osk2090.pms.Client.domain.Client;
import com.osk2090.pms.Client.util.Agreement;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.List;

public class ClientPrintOneHandler implements Command {
    List<Client> clientList;

    public ClientPrintOneHandler(List<Client> clientList, ClientAddHandler clientAddHandler) {
        this.clientList = clientList;
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
    public void service(DataInputStream in, DataOutputStream out) throws Exception {
        if (Agreement.Agree()) {
            this.clientAddHandler.service(in, out);
        }
        }
}