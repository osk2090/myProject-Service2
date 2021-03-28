package com.osk2090.pms.Client.handler;


import com.osk2090.pms.Client.util.Prompt;

public class AdminWinnerCheckHandler extends AbstractAdminHandler {

  public static void winnerCheck(String name, String id, int size, int n) {

    System.out.println("--강남 나이키 매장에 오신걸 환영합니다.");
    String winnerName = Prompt.promptString("당첨자 성함:");
    String winnerId = Prompt.promptString("당첨자 나이키 id:");
    int winnerSize = Prompt.promptInt("선택한 사이즈:");

    if (winnerName.equals(name) && winnerId.equals(id) && winnerSize == size) {
      System.out.println("정보가 확인되었습니다. 수령해가시기 바랍니다.");
//      clientInfoHandler.removeClient(n, clientList);//삭제 구현하기
      setR(-1);
      return;
    } else {
      System.out.println("정보가 틀립니다.수령하실수 없습니다.");
      System.out.println("응모자 정보를 삭제겠습니다.");
//      clientInfoHandler.removeClient(n, clientList);//삭제 구현하기
      setR(-1);
      return;
    }
  }
}