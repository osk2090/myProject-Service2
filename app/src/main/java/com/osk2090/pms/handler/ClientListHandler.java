package com.osk2090.pms.handler;

import com.osk2090.pms.dao.ClientDao;
import com.osk2090.pms.domain.Client;

import java.util.List;

public class ClientListHandler implements Command {

    ClientDao clientDao;

    public ClientListHandler(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public void service() throws Exception {

        System.out.println("응모자 목록");

        List<Client> list = clientDao.findAll();

        for (Client c : list) {
            System.out.printf("%d,%s,%s\n",
                    c.getNo(),
                    c.getName(),
                    c.getId());
        }
    }
}