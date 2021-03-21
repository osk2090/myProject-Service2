package com.osk2090.pms.Client.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ClientListHandler implements Command {

    @Override
    public void service(DataInputStream in, DataOutputStream out) throws Exception {

        System.out.println("응모자 목록");
        out.writeUTF("client/selectall");
        out.writeInt(0);
        out.flush();

        String status = in.readUTF();
        int length = in.readInt();

        if (status.equals("error")) {
            System.out.println(in.readUTF());
            return;
        }


        for (int i = 0; i < length; i++) {
            String[] fields = in.readUTF().split(",");

            System.out.printf("%d,%s,%s,%s,%s,%d\n",
                    fields[0],
                    fields[1],
                    fields[2],
                    fields[3],
                    fields[4],
                    fields[5]);
        }





//        Iterator<Client> iterator = clientList.iterator();
//        System.out.println("====================================================================");
//        while (iterator.hasNext()) {
//            Client c1 = iterator.next();
//            System.out.printf("%d. 이름: %s 전화번호: %s 생년월일: %s 아이디: %s 사이즈: %s\n",
//                    c1.getIdx(), c1.getName(), c1.getpN(), c1.getbN(), c1.getId(), c1.getcSize());
//        }
//        System.out.println("====================================================================");
    }

}