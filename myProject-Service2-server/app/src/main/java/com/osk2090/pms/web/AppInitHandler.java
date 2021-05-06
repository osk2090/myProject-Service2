package com.osk2090.pms.web;

import com.osk2090.mybatis.MybatisDaoFactory;
import com.osk2090.mybatis.SqlSessionFactoryProxy;
import com.osk2090.mybatis.TransactionManager;
import com.osk2090.pms.dao.ClientDao;
import com.osk2090.pms.service.ClientService;
import com.osk2090.pms.service.impl.DefaultClientService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("serial")
@WebServlet(value = "/init", loadOnStartup = 1)
public class AppInitHandler implements Servlet {

    ServletRequest request;
    ServletResponse response;

    @Override
    public void init(ServletConfig config) throws ServletException {

        try {
            InputStream mybatisConfigStream = Resources.getResourceAsStream(
                    "com/osk2090/pms/conf/mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(mybatisConfigStream);
            SqlSessionFactoryProxy sqlSessionFactoryProxy = new SqlSessionFactoryProxy(sqlSessionFactory);

            MybatisDaoFactory daoFactory = new MybatisDaoFactory(sqlSessionFactoryProxy);
            ClientDao clientDao = daoFactory.createDao(ClientDao.class);
            TransactionManager txManager = new TransactionManager(sqlSessionFactoryProxy);
            ClientService clientService = new DefaultClientService(clientDao);

            ClientStatusHandler clientStatusHandler = new ClientStatusHandler();
            AdminWinnerResultHandler adminWinnerResultHandler = new AdminWinnerResultHandler();

            ClientAddHandler clientAddHandler = new ClientAddHandler();
            AdminCheckResultHandler adminCheckResultHandler = new AdminCheckResultHandler();
            AdminWinnerCheckHandler adminWinnerCheckHandler = new AdminWinnerCheckHandler();
            AdminLogicHandler adminLogicHandler = new AdminLogicHandler(request, response);
            ClientListHandler clientListHandler = new ClientListHandler();
            ClientInfoHandler clientInfoHandler = new ClientInfoHandler();
            ClientDeleteHandler clientDeleteHandler = new ClientDeleteHandler();
            ClientDetailHandler clientDetailHandler = new ClientDetailHandler();

            ServletContext servletContext = config.getServletContext();

            // Command 구현체가 사용할 의존 객체를 보관
            servletContext.setAttribute("1", new ClientPrintOneHandler(clientAddHandler));
            servletContext.setAttribute("2", new ClientPrintTwoHandler(
                    adminCheckResultHandler,
                    adminWinnerCheckHandler,
                    adminLogicHandler,
                    clientListHandler,
                    adminWinnerResultHandler,
                    clientDeleteHandler,
                    clientDetailHandler,
                    clientDao));
            servletContext.setAttribute("3", new ClientPrintThreeHandler(clientInfoHandler, adminWinnerCheckHandler, clientDao));

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
