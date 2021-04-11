package com.osk2090.pms.handler;


import com.osk2090.pms.dao.ClientDao;
import com.osk2090.pms.dao.mariadb.ClientDaoImpl;

public class ClientPrintTwoHandler implements Command {


    public ClientPrintTwoHandler(AdminCheckResultHandler adminCheckResultHandler,
                                 AdminLogicHandler adminLogicHandler,
                                 ClientListHandler clientListHandler,
                                 AdminWinnerResultHandler adminWinnerResultHandler,
                                 ClientDeleteHandler clientDeleteHandler,
                                 ClientDetailHandler clientDetailHandler,
                                 ClientDao clientDao) {

        this.adminCheckResultHandler = adminCheckResultHandler;
        this.adminLogicHandler = adminLogicHandler;
        this.clientListHandler = clientListHandler;
        this.adminWinnerResultHandler = adminWinnerResultHandler;
        this.clientDeleteHandler = clientDeleteHandler;
        this.clientDetailHandler = clientDetailHandler;
        this.clientDao = clientDao;
    }

    AdminCheckResultHandler adminCheckResultHandler;
    AdminLogicHandler adminLogicHandler;
    ClientListHandler clientListHandler;
    ClientInfoHandler clientInfoHandler;
    AdminWinnerResultHandler adminWinnerResultHandler;
    ClientDeleteHandler clientDeleteHandler;
    ClientDetailHandler clientDetailHandler;
    ClientDao clientDao;

    @Override
    public void service() throws Exception {
        int check = AdminCheckResultHandler.checkResult();
        if (check == 0) {
            adminLogicHandler.adminLogic(clientListHandler, adminWinnerResultHandler,
                    clientInfoHandler, clientDeleteHandler, clientDetailHandler, clientDao);
        } else {
            System.out.println("관리자의 아이디와 비밀번호를 확인해주세요.");
        }
    }
}