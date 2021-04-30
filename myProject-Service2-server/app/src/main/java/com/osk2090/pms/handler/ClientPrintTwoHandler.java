package com.osk2090.pms.handler;


import com.osk2090.pms.dao.ClientDao;
import com.osk2090.util.concurrent.CommandRequest;
import com.osk2090.util.concurrent.CommandResponse;

public class ClientPrintTwoHandler implements Command {


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
    public void service(CommandRequest request, CommandResponse response) throws Exception {
        int check = AdminCheckResultHandler.checkResult();
        if (check == 0) {
            adminLogicHandler.adminLogic(clientListHandler, adminWinnerResultHandler,
                    clientInfoHandler, clientDeleteHandler, clientDetailHandler, clientDao);
        } else {
            System.out.println("관리자의 아이디와 비밀번호를 확인해주세요.");
        }
    }
}