package com.osk2090.pms.web;


import com.osk2090.pms.dao.ClientDao;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class ClientPrintTwoHandler extends AbstractAdminHandler {

    public ClientPrintTwoHandler(AdminCheckResultHandler adminCheckResultHandler,
                                 AdminWinnerCheckHandler adminWinnerCheckHandler,
                                 AdminLogicHandler adminLogicHandler,
                                 ClientListHandler clientListHandler,
                                 AdminWinnerResultHandler adminWinnerResultHandler,
                                 ClientDeleteHandler clientDeleteHandler,
                                 ClientDetailHandler clientDetailHandler,
                                 ClientDao clientDao) {
        this.adminWinnerCheckHandler = adminWinnerCheckHandler;
        this.adminCheckResultHandler = adminCheckResultHandler;
        this.adminLogicHandler = adminLogicHandler;
        this.clientListHandler = clientListHandler;
        this.adminWinnerResultHandler = adminWinnerResultHandler;
        this.clientDeleteHandler = clientDeleteHandler;
        this.clientDetailHandler = clientDetailHandler;
        this.clientDao = clientDao;
    }

    AdminCheckResultHandler adminCheckResultHandler;
    AdminWinnerCheckHandler adminWinnerCheckHandler;
    AdminLogicHandler adminLogicHandler;
    ClientListHandler clientListHandler;
    ClientInfoHandler clientInfoHandler;
    AdminWinnerResultHandler adminWinnerResultHandler;
    ClientDeleteHandler clientDeleteHandler;
    ClientDetailHandler clientDetailHandler;
    ClientDao clientDao;

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        try {
            int check = AdminCheckResultHandler.checkResult();
            if (check == 0) {
                adminLogicHandler.adminLogic(clientListHandler, adminWinnerResultHandler,
                        clientInfoHandler, clientDeleteHandler, clientDetailHandler, clientDao);
            } else {
                System.out.println("관리자의 아이디와 비밀번호를 확인해주세요.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}