package com.osk2090.pms.Client.dao.mariadb;

import com.osk2090.pms.Client.dao.ClientDao;
import com.osk2090.pms.Client.domain.Client;

import java.util.List;

public class ForAbstract implements ClientDao {
    @Override
    public int insert(Client client) throws Exception {
        return 0;
    }

    @Override
    public Client findByNo(int no) throws Exception {
        return null;
    }

    @Override
    public List<Client> findAll() throws Exception {
        return null;
    }

    @Override
    public int delete(int no) throws Exception {
        return 0;
    }
}