package com.osk2090.pms.handler;

import com.osk2090.util.Prompt;

public class AdminLogicHandler extends AbstractAdminHandler {

  public void adminLogic(ClientListHandler clientListHandler,
                         AdminWinnerResultHandler adminWinnerResultHandler,
                         ClientInfoHandler clientInfoHandler,
                         ClientDeleteHandler clientDeleteHandler,
                         ClientDetailHandler clientDetailHandler) throws Exception {
    boolean run = true;
    while (run) {

      int choice = Prompt.promptInt("1. 추첨하기 2. 응모자리스트 3. 응모자삭제 4. 로그아웃");
      if (choice == 1) {
        if (ClientInfoHandler.showCountClients() != 0) {
          System.out.println("추첨하겠습니다.");
          adminWinnerResultHandler.winnerResult(clientInfoHandler);
          return;
        } else {
          System.out.println("현재 입력된 응모자가 없습니다.");
          continue;
        }
      } else if (choice == 2) {
        if (clientInfoHandler.showCountClients() == 0) {
          System.out.println("현재 응모자가 없습니다.");
          continue;
        }
        System.out.println("--------------------------------------------");
        clientListHandler.service();
        System.out.println("--------------------------------------------");
        choice = Prompt.promptInt("1.자세히보기 2.나가기");
        if (choice == 1) {
          clientDetailHandler.service();
        } else if (choice == 2) {
          continue;
        } else {
          System.out.println("메뉴 확인해주세요.");
        }
      } else if (choice == 3) {
        if (clientInfoHandler.showCountClients() == 0) {
          System.out.println("입력된 응모자가 없습니다.");
          continue;
        } else {
          try {
            clientListHandler.service();
          } catch (Exception e) {
            e.printStackTrace();
          }
          clientDeleteHandler.service();
        }
      } else if (choice == 4) {
        System.out.println("로그아웃 되었습니다.");
        AdminCheck = -1;//로그아웃
        break;
      } else {
        System.out.println("없는 메뉴 입니다.");
      }
    }
  }
}