package com.osk2090.pms.web;


import com.osk2090.util.Prompt;


public class AdminCheckResultHandler extends AbstractAdminHandler {

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