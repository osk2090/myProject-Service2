package com.osk2090.pms.Client.handler;

import com.osk2090.pms.Client.domain.Client;

import java.util.List;

public class ClientInfoHandler {

    List<Client> clientList;

    public ClientInfoHandler(List<Client> clientList) {
        this.clientList = clientList;
    }

    public int showClients() {//카운팅
        return clientList.size();
    }

    public Client getInfo(int clientNo) {//정보가져오기------삭제 예정
        return clientList.get(clientNo);
    }

    public void removeClient(int clientNo, List<Client> clientList) {
        clientList.remove(clientNo);
    }
}