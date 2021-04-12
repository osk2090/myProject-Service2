package com.osk2090.pms.handler;

import com.osk2090.pms.dao.ClientDao;

public class AdminWinnerResultHandler extends AbstractAdminHandler {

  void winnerResult(ClientInfoHandler clientInfoHandler, ClientDao clientDao) throws Exception {
    int r = -1;
    while (true) {
      r = ran.nextInt(ClientInfoHandler.showCountClients()) + 1;
      client = clientDao.findByNo(r);
      if (client != null) {
        System.out.println("당첨자 선정이 끝났습니다.");
        break;
      }
    }


//      if (clients == null) {//
//        r = ran.nextInt(ClientInfoHandler.showCountClients()) + 1;
//        System.out.println("해당 번호의 회원은 삭제된 회원이므로 다시 뽑겠습니다!");
//        continue;
//      }

      for (int i = 3; i >= 1; i--) {
        try {
          Thread.sleep(1000);
        } catch (Exception e) {
          e.printStackTrace();
        }
        System.out.printf("%d! ", i);
        setR(r);
      }

    System.out.println("당첨자:" + client.getName());
    System.out.println("축하합니다!");
    r = client.getNo();//당첨자 인덱스 저장
  }

  void winnerStatus(ClientDao clientDao) throws Exception {
    if (getR() == -1) {
      System.out.println(winnerTitle + "없음");
    } else {
      System.out.println(winnerTitle + client.getName() + " 님.");
    }
  }
}