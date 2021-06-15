package com.osk2090.pms.service;

import com.osk2090.pms.domain.Client;

import java.util.List;

public interface ClientService {

    int add(Client client) throws Exception;

    List<Client> list() throws Exception;

    Client get(int no) throws Exception;

    int delete(int no) throws Exception;

    int update(Client client) throws Exception;
}