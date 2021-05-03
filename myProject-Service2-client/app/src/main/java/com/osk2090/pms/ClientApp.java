package com.osk2090.pms;

import com.osk2090.util.Prompt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientApp {

  public static void main(String[] args) {
    ClientApp app = new ClientApp();
    try {
      app.execute();

    } catch (Exception e) {
      System.out.println("클라이언트 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }

  public void execute() throws Exception {
    // Stateless 통신 방식

    while (true) {
      String choice = String.valueOf(Prompt.promptInt("-Nike-\n-Draw-\n1. 응모자 2. 관리자 3. 당첨자 수령하기 0. 종료"));
      if (choice.length() == 0) {
        continue;
      }

      if (choice.equalsIgnoreCase("0")) {
        System.out.println("종료합니다!");
        break;
      }
      if (!choice.equalsIgnoreCase("1") ||
              !choice.equalsIgnoreCase("2") ||
              !choice.equalsIgnoreCase("3")) {
        System.out.println("다시 선택해주세요!");
        break;
      }

    }
  }

  private void requestService(String input) {
    int i = input.indexOf("/");
    String command = input.substring(i);

    String[] values = input.substring(0, i).split(":");
    String serverAddress = values[0];
    int port = 8080;
    if (values.length > 1) {
      port = Integer.parseInt(values[1]);
    }

    try (Socket socket = new Socket(serverAddress, port);
         PrintWriter out = new PrintWriter(socket.getOutputStream());
         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
      out.println(command);
      out.println();
      out.flush();

      String line = null;
      while (true) {
        line = in.readLine();

        if (line.length() == 0) {
          break;
        } else if (line.equals("!{}!")) {
          String choice = String.valueOf(Prompt.promptInt("-Nike-\n-Draw-\n1. 응모자 2. 관리자 3. 당첨자 수령하기 0. 종료"));
          out.println(choice);
          out.flush();
        } else {
          System.out.println(line);
        }
      }
    } catch (Exception e) {
      System.out.println("통신 오류 발생!");
    }
  }
}
