package com.osk2090.pms.handler;

import com.osk2090.pms.domain.Client;
import com.osk2090.pms.service.ClientService;
import com.osk2090.stereotype.Component;
import com.osk2090.util.concurrent.CommandRequest;
import com.osk2090.util.concurrent.CommandResponse;

import java.io.PrintWriter;
import java.util.List;

@Component(value = "/client/list")
public class ClientListHandler implements Command {

    ClientService clientService;

    public ClientListHandler(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void service(CommandRequest request, CommandResponse response) throws Exception {
        PrintWriter out = response.getWriter();

        out.println("응모자 목록");

        List<Client> list = clientService.list();

        for (Client c : list) {
            out.printf("%d,%s,%s\n",
                    c.getNo(),
                    c.getName(),
                    c.getId());
        }
    }
}