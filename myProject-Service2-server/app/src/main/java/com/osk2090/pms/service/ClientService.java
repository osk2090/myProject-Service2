package com.osk2090.pms.service;

import com.osk2090.pms.domain.Client;

import java.util.List;

public interface ClientService {

    //응모자 등록 업무
    int add(Client client) throws Exception;

    //응모자 목록 조회 업무
    List<Client> list() throws Exception;

    //응모자 상세 조회 업무
    Client get(int no) throws Exception;

    //응모자 삭제 업무
    int delete(int no) throws Exception;
}