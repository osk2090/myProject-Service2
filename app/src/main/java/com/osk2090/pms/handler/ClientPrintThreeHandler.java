package com.osk2090.pms.handler;


import com.osk2090.pms.dao.ClientDao;

public class ClientPrintThreeHandler extends AbstractAdminHandler implements Command {

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
    public void service() throws Exception {
        if (this.clientInfoHandler.showCountClients() == 0) {
            System.out.println("입력된 응모자가 없습니다.");
        } else {
            this.clientDao.findByNo(this.adminWinnerCheckHandler.getR());
        }
    }
}