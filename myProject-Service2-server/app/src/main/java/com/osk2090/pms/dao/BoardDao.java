package com.osk2090.pms.dao;

import com.osk2090.pms.domain.Board;

import java.util.List;

public interface BoardDao {

    int insert(Board board) throws Exception;

    Board findByNo(int no) throws Exception;

    List<Board> findAll() throws Exception;

    int delete(int no) throws Exception;

    int update(Board board) throws Exception;

    List<Board> findByKeyword(String keyword) throws Exception;

}