package com.osk2090.pms.dao;

import com.osk2090.pms.domain.Client;

import java.util.List;

public interface ClientDao {

    int insert(Client client) throws Exception;

    Client findByNo(int no) throws Exception;

    List<Client> findAll() throws Exception;

    int delete(int no) throws Exception;

}