package com.osk2090.util;

import com.osk2090.pms.domain.Client;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class Prompt {
  static Scanner scan = new Scanner(System.in);

  private BufferedReader in;
  private PrintWriter out;

  public Prompt(BufferedReader in, PrintWriter out) {
    this.in = in;
    this.out = out;
  }

  public static String promptString(String title) {//문자열입력
    System.out.println(title);
    return scan.nextLine();
  }

  public static int promptInt(String title) {//정수입력
    String temp = promptString(title);

    while (!temp.matches("^[0-9]*$")) {//숫자가 안나오면
      //^[0-9]*$의 의미
      // ^:스트링의 시작
      // [0-9]*:0-9로 이루어진 문자 여러개  *의 의미는 한개이상일때라는 말이다
      // $: 끝

      System.out.println("숫자만 입력해주세요");
      System.out.print("> ");
      temp = scan.nextLine();
    }
    return Integer.parseInt(temp);
  }

  //y/n만 입력받는 프롬프트
  public static String promptAgreeString(String title) {//문자열입력
    System.out.println(title);
    String temp = scan.nextLine();
    while (!temp.matches("^[y]*$")) {
      System.out.println("N 또는 다른 문자를 입력하셨습니다.");
      temp = scan.nextLine();
    }
    return temp;
  }

  public static boolean PhoneNumberCheck(String number) {
    if (number.matches("^[0-9]*$") && number.length() == 11) {//숫자만 있어야되고 길이가 맞게
      return true;
    }
    return false;
  }

  public static boolean BirthNumberCheck(String number) {
    if (number.matches("^[0-9]*$") && number.length() == 6) {//숫자만 있어야되고 길이가 맞게
      return true;
    }
    return false;
  }



  public static void close() {
    scan.close();
  }
}
