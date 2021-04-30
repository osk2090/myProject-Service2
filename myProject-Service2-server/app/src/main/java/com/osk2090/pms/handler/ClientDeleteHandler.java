package com.osk2090.pms.handler;

import com.osk2090.pms.service.ClientService;
import com.osk2090.stereotype.Component;
import com.osk2090.util.Prompt;
import com.osk2090.util.concurrent.CommandRequest;
import com.osk2090.util.concurrent.CommandResponse;

import java.io.PrintWriter;

@Component(value = "/client/delete")
public class ClientDeleteHandler implements Command {

    ClientService clientService;

    public ClientDeleteHandler(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void service(CommandRequest request, CommandResponse response) throws Exception {

        PrintWriter out = response.getWriter();
        Prompt prompt = request.getPrompt();

        out.println("[응모자 삭제]");

        int no = prompt.promptInt("번호? ");

        String input = prompt.promptString("정말 삭제하시겠습니까?(y/N)? ");
        if (!input.equalsIgnoreCase("y")) {
            out.println("응모자 삭제를 취소하였습니다.");
            return;
        }

        if (clientService.delete(no) == 0) {
            out.println("해당 번호의 응모자가 없습니다.");
            return;
        } else {
            out.println("회원을 삭제하였습니다.");
        }
    }
}