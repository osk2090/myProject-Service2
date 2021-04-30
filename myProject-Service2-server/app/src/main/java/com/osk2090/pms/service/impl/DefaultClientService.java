package com.osk2090.pms.service.impl;

import com.osk2090.pms.dao.ClientDao;
import com.osk2090.pms.domain.Client;
import com.osk2090.pms.service.ClientService;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

//서비스 객체
//-비즈니스 로직을 담고 있다
//-업무에 따라 트랜잭션을 제어하는 일을 한다
//-서비스 객체의 메서드는 가능한 업무 관련 용어를 사용하여 메서드를 정의한다
public class DefaultClientService implements ClientService {

    //서비스 객체는 트랜젝션을 제어해야 하기 때문에
    //DAO가 사용하는 SqlSession 객체를 주입 받아야 한다
    SqlSession sqlSession;

    //비즈니스 로직을 수행하는 동안 데이터 처리를 위해 사용할 DAO를 주입 받아야 한다
    ClientDao clientDao;

    public DefaultClientService(SqlSession sqlSession, ClientDao clientDao) {
        this.sqlSession = sqlSession;
        this.clientDao = clientDao;
    }

    //응모자 등록 업무
    public int add(Client client) throws Exception {
        int count = clientDao.insert(client);
        sqlSession.commit();
        return count;
    }

    //응모자 목록 조회 업무
    public List<Client> list() throws Exception {
        return clientDao.findAll();
    }

    //응모자 상세 조회 업무
    public Client get(int no) throws Exception {
        Client client = clientDao.findByNo(no);
        if (client != null) {
            sqlSession.commit();
        }
        return client;
    }

    //응모자 삭제 업무
    public int delete(int no) throws Exception {
        int count = clientDao.delete(no);
        sqlSession.commit();
        return count;
    }
}