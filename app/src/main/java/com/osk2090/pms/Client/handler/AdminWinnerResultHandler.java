package com.osk2090.pms.Client.handler;

import com.osk2090.pms.Client.domain.Client;

import java.util.List;

public class AdminWinnerResultHandler extends AbstractAdminHandler {

  void winnerResult(ClientInfoHandler clientInfoHandler) {
    Client client = clientInfoHandler.getInfo(ran.nextInt(clientInfoHandler.showClients()));
    while (true) {
      for (int i = 3; i >= 1; i--) {
        try {
          Thread.sleep(1000);
        } catch (Exception e) {
          e.printStackTrace();
        }
        System.out.printf("%d! ", i);
      }
      if (client == null) {
        System.out.println("해당 번호의 회원은 삭제된 회원이므로 다시 뽑겠습니다!");
        continue;
      }

      break;
    }

    System.out.println("당첨자:" + client.getName());
    System.out.println("축하합니다!");
    r = client.getNo();//당첨자 인덱스 저장
  }

  void winnerStatus(ClientInfoHandler clientInfoHandler) {
    if (getR() == -1) {
      System.out.println(winnerTitle + "없음");
    } else {
      System.out.println(winnerTitle + clientInfoHandler.getInfo(getR()).getName() + " 님.");
    }
  }
}