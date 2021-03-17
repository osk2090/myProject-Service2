package com.osk2090.pms.Client.handler;

import com.osk2090.pms.Client.util.Prompt;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ClientDeleteHandler implements Command {

    @Override
    public void service(DataInputStream in, DataOutputStream out) throws Exception {
        System.out.println("[응모자 삭제]");

        int no = Prompt.promptInt("번호? ");

        out.writeUTF("client/select");
        out.writeInt(1);
        out.writeUTF(Integer.toString(no));
        out.flush();

        String status = in.readUTF();
        in.readInt();
        String data = in.readUTF();

        if (status.equals("error")) {
            System.out.println(data);
            return;
        }

        String input = Prompt.promptString("정말 삭제하시겠습니까?(y/N)? ");
        if (!input.equalsIgnoreCase("y")) {
            System.out.println("응모자 삭제를 취소하였습니다.");
            return;
        }

        out.writeUTF("client/delete");
        out.writeInt(1);
        out.writeUTF(Integer.toString(no));
        out.flush();

        status = in.readUTF();
        in.readInt();

        if (status.equals("error")) {
            System.out.println(in.readUTF());
            return;
        }

        System.out.println("응모자를 삭제하였습니다.");
    }
}