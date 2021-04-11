package com.osk2090.pms.dao;

import com.osk2090.pms.domain.Client;

import java.util.List;

public interface ClientDao {

    public int insert(Client client) throws Exception;

    public Client findByNo(int no) throws Exception;

    public List<Client> findAll() throws Exception;

    public int delete(int no) throws Exception;

}