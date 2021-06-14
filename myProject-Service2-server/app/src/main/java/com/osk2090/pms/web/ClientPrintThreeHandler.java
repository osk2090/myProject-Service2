package com.osk2090.pms.web;


import com.osk2090.pms.dao.ClientDao;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class ClientPrintThreeHandler extends AbstractAdminHandler {

    public ClientPrintThreeHandler(ClientInfoHandler clientInfoHandler,
                                   AdminWinnerCheckHandler adminWinnerCheckHandler
            , ClientDao clientDao) {

        this.clientInfoHandler = clientInfoHandler;
        this.adminWinnerCheckHandler = adminWinnerCheckHandler;
        this.clientDao = clientDao;
    }

    ClientInfoHandler clientInfoHandler;
    AdminWinnerCheckHandler adminWinnerCheckHandler;
    ClientDao clientDao;

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        try {
            if (this.clientInfoHandler.showCountClients() == 0) {
                System.out.println("입력된 응모자가 없습니다.");
            } else {
                this.clientDao.findByNo(this.adminWinnerCheckHandler.getR());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}