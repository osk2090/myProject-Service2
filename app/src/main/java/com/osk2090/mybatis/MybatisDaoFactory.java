package com.osk2090.mybatis;

import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Proxy;

public class MybatisDaoFactory {
    DaoWorker daoWorker;

    public MybatisDaoFactory(SqlSession sqlSession) {
        this.daoWorker = new DaoWorker(sqlSession);
    }

    public <T> T createDao(Class<T> daoInterface) {
        return (T) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class<?>[] {daoInterface},
                daoWorker
        );
    }
}