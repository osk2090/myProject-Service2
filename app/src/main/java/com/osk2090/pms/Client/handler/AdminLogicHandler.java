package com.osk2090.pms.Client.handler;

import com.osk2090.pms.Client.domain.Client;
import com.osk2090.pms.Client.util.Prompt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.List;

public class AdminLogicHandler extends AbstractAdminHandler {
  DataInputStream in;
  DataOutputStream out;

  public void adminLogic(ClientListHandler clientListHandler,
                         AdminWinnerResultHandler adminWinnerResultHandler)
          throws CloneNotSupportedException {

    boolean run = true;
    while (run) {

      int choice = Prompt.promptInt("1. 추첨하기 2. 응모자리스트 3. 응모자삭제 4. 로그아웃");
      if (choice == 1) {
        if (clientInfoHandler.showClients() != 0) {
          System.out.println("추첨하겠습니다.");
          adminWinnerResultHandler.winnerResult(clientInfoHandler);
          return;
        } else {
          System.out.println("현재 입력된 응모자가 없습니다.");
        }
      } else if (choice == 2) {
        if (clientInfoHandler.showClients() == 0) {
          System.out.println("현재 응모자가 없습니다.");
        } else {
          try {
            clientListHandler.service();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      } else if (choice == 3) {
        if (clientInfoHandler.showClients() == 0) {
          System.out.println("입력된 응모자가 없습니다.");
        } else {
          try {
            clientListHandler.service();
          } catch (Exception e) {
            e.printStackTrace();
          }
          choice = Prompt.promptInt("삭제할 회원의 인덱스를 입력:");
          if (choice < 0 || choice > clientList.size() - 1) {
            System.out.println("존재하지 않는 응모자입니다.");
          } else {
            clientInfoHandler.removeClient(choice, clientList);
            System.out.println("삭제를 완료하였습니다.");
          }
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