package com.osk2090.pms.Client.handler;

import com.osk2090.pms.Client.dao.ClientDao;
import com.osk2090.pms.Client.util.Prompt;

public class ClientDeleteHandler implements Command {

    ClientDao clientDao;

    public ClientDeleteHandler(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public void service() throws Exception {
        System.out.println("[응모자 삭제]");

        int no = Prompt.promptInt("번호? ");

        String input = Prompt.promptString("정말 삭제하시겠습니까?(y/N)? ");
        if (!input.equalsIgnoreCase("y")) {
            System.out.println("응모자 삭제를 취소하였습니다.");
            return;
        }

        if (clientDao.delete(no) == 0) {
            System.out.println("해당 번호의 응모자가 없습니다.");
            return;
        } else {
            System.out.println("회원을 삭제하였습니다.");
        }
    }
}