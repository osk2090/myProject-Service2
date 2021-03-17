package com.osk2090.pms.Client.handler;

import com.osk2090.pms.Client.util.Prompt;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ClientDetailHandler implements Command {

    @Override
    public void service(DataInputStream in, DataOutputStream out) throws Exception {
        System.out.println("[응모자 상세보기]");

        int no = Prompt.promptInt("번호? ");

        out.writeUTF("client/select");
        out.writeInt(1);
        out.writeUTF(Integer.toString(no));
        out.flush();

        String status = in.readUTF();
        in.readInt();

        if (status.equals("error")) {
            System.out.println(in.readUTF());
            return;
        }

        String[] fields = in.readUTF().split(",");

        //이름: %s 전화번호: %s 생년월일: %s 아이디: %s 사이즈:
        System.out.printf("이름: %s\n", fields[1]);
        System.out.printf("전화번호: %s\n", fields[2]);
        System.out.printf("생년월일: %s\n", fields[3]);
        System.out.printf("NIKE 아이디: %s\n", fields[4]);
        System.out.printf("사이즈: %s\n", fields[5]);

    }
}