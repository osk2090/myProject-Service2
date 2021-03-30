package com.osk2090.pms.Client.handler;


public class ClientPrintThreeHandler extends AbstractAdminHandler implements Command {

    public ClientPrintThreeHandler(ClientInfoHandler clientInfoHandler,
                                   AdminWinnerCheckHandler adminWinnerCheckHandler) {
        this.clientInfoHandler = clientInfoHandler;
        this.adminWinnerCheckHandler = adminWinnerCheckHandler;
    }

    ClientInfoHandler clientInfoHandler;
    AdminWinnerCheckHandler adminWinnerCheckHandler;

    @Override
    public void service() throws Exception {
        if (this.clientInfoHandler.showCountClients() == 0) {
            System.out.println("입력된 응모자가 없습니다.");
        } else {
            this.clientInfoHandler.getInfo(this.adminWinnerCheckHandler.getR());
        }
    }
}