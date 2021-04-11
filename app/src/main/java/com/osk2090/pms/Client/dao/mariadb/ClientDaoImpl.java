package com.osk2090.pms.Client.dao.mariadb;

import com.osk2090.pms.Client.dao.ClientDao;
import com.osk2090.pms.Client.domain.Client;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ClientDaoImpl implements ClientDao {

    SqlSession sqlSession;

    public ClientDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int insert(Client client) throws Exception {
        return sqlSession.insert("ClientMapper.insert", client);
    }

    public Client findByNo(int no) throws Exception {
        return sqlSession.selectOne("ClientMapper.findByNo", no);
    }

    public List<Client> findAll() throws Exception {
        return sqlSession.selectList("ClientMapper.findAll");
    }

    public int delete(int no) throws Exception {
        return sqlSession.delete("ClientMapper.delete", no);
    }
}