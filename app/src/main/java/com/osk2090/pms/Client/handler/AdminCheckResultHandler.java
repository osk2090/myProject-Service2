package com.osk2090.pms.Client.handler;


import com.osk2090.pms.Client.domain.Client;
import com.osk2090.pms.Client.util.Prompt;

import java.util.List;


public class AdminCheckResultHandler extends AbstractAdminHandler {

  public AdminCheckResultHandler(List<Client> clientList) {
    super(clientList);
  }

  public static int checkResult() {
    String myAdminId = Prompt.promptString("관리자id: ");
    int myAdminPw = Prompt.promptInt("관리자pw: ");

    if (myAdminId.equals(AdminID) && myAdminPw == AdminPW) {
      System.out.println("관리자님 환영합니다.");
      return 0;
    }
    return -1;
  }
}