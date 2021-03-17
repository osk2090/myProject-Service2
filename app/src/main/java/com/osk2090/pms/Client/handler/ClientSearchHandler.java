package com.osk2090.pms.Client.handler;

import com.osk2090.pms.Client.util.Prompt;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ClientSearchHandler implements Command {
    @Override
    public void service(DataInputStream in, DataOutputStream out) throws Exception {
        String keword = Prompt.promptString("검색어? ");

        if (keword.length() == 0) {
            System.out.println("검색어를 입력하세요.");
            return;
        }

        out.writeUTF("client/selectByKeyword");
        out.writeInt(1);
        out.writeUTF(keword);
        out.flush();

        String status = in.readUTF();
        int length = in.readInt();

        if (status.equals("error")) {
            System.out.println(in.readUTF());
            return;
        }

        if (length == 0) {
            System.out.println("검색어에 해당하는 회원이 없습니다.");
            return;
        }

        for (int i = 0; i < length; i++) {
            String[] fields = in.readUTF().split(",");

            System.out.printf("%d,%s,%s,%s,%s,%d",
                    Integer.parseInt(fields[0]),
                    fields[1],
                    fields[2],
                    fields[3],
                    fields[4],
                    Integer.parseInt(fields[5])
            );
        }
    }
}