package com.osk2090.pms.handler;

import com.osk2090.pms.domain.Client;
import com.osk2090.pms.service.ClientService;

import java.util.List;

public class ClientListHandler implements Command {

    ClientService clientService;

    public ClientListHandler(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void service() throws Exception {

        System.out.println("응모자 목록");

        List<Client> list = clientService.list();

        for (Client c : list) {
            System.out.printf("%d,%s,%s\n",
                    c.getNo(),
                    c.getName(),
                    c.getId());
        }
    }
}