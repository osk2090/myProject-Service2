package com.osk2090.pms.web;

import com.osk2090.mybatis.MybatisDaoFactory;
import com.osk2090.mybatis.SqlSessionFactoryProxy;
import com.osk2090.mybatis.TransactionManager;
import com.osk2090.pms.dao.ClientDao;
import com.osk2090.pms.handler.*;
import com.osk2090.pms.service.ClientService;
import com.osk2090.pms.service.impl.DefaultClientService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(value = "/init", loadOnStartup = 1)
public class AppInitHandler implements Servlet {

    ServletRequest request;
    ServletResponse response;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            InputStream mybatisConfigStream = Resources.getResourceAsStream(
                    "com/osk2090/pms/conf/mybatis-config.xml");

            // SqlSessionFactory 객체 준비
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(mybatisConfigStream);

            // DAO가 사용할 SqlSession 객체 준비
            // => 수동 commit 으로 동작하는 SqlSession 객체를 준비한다.
            SqlSessionFactoryProxy sqlSessionFactoryProxy = new SqlSessionFactoryProxy(sqlSessionFactory);

            // DAO 구현체를 만들어주는 공장 객체를 준비한다.
            MybatisDaoFactory daoFactory = new MybatisDaoFactory(sqlSessionFactoryProxy);

            // 서비스 객체가 사용할 DAO 객체 준비
            ClientDao clientDao = daoFactory.createDao(ClientDao.class);

            TransactionManager txManager = new TransactionManager(sqlSessionFactoryProxy);

            // Command 구현체가 사용할 의존 객체 준비
            ClientService clientService = new DefaultClientService(clientDao);

            ClientStatusHandler clientStatusHandler = new ClientStatusHandler();
            AdminWinnerResultHandler adminWinnerResultHandler = new AdminWinnerResultHandler();

            ClientAddHandler clientAddHandler = new ClientAddHandler();
            AdminCheckResultHandler adminCheckResultHandler = new AdminCheckResultHandler();
            AdminWinnerCheckHandler adminWinnerCheckHandler = new AdminWinnerCheckHandler();
            AdminLogicHandler adminLogicHandler = new AdminLogicHandler(request, response);
            ClientListHandler clientListHandler = new ClientListHandler();
            ClientInfoHandler clientInfoHandler = new ClientInfoHandler();
            ClientDeleteHandler clientDeleteHandler = new ClientDeleteHandler(clientService);
            ClientDetailHandler clientDetailHandler = new ClientDetailHandler();

            ServletContext servletContext = config.getServletContext();

            // Command 구현체가 사용할 의존 객체를 보관
//            servletContext.setAttribute("1", new ClientPrintOneHandler(clientAddHandler));
//            servletContext.setAttribute("2", new ClientPrintTwoHandler(
//                    adminCheckResultHandler,
//                    adminWinnerCheckHandler,
//                    adminLogicHandler,
//                    clientListHandler,
//                    adminWinnerResultHandler,
//                    clientDeleteHandler,
//                    clientDetailHandler,
//                    clientDao));
//            servletContext.setAttribute("3", new ClientPrintThreeHandler(clientInfoHandler, adminWinnerCheckHandler, clientDao));

            servletContext.setAttribute("clientService", clientService);

            System.out.println("의존 객체를 모두 준비하였습니다.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {

    }
}
