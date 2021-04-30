package com.osk2090.pms.handler;

import com.osk2090.pms.domain.Client;
import com.osk2090.pms.service.ClientService;
import com.osk2090.stereotype.Component;
import com.osk2090.util.Prompt;
import com.osk2090.util.concurrent.CommandRequest;
import com.osk2090.util.concurrent.CommandResponse;

import java.io.PrintWriter;

@Component(value = "/client/detail")
public class ClientDetailHandler implements Command {

    ClientService clientService;

    public ClientDetailHandler(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void service(CommandRequest request, CommandResponse response) throws Exception {
        PrintWriter out = response.getWriter();
        Prompt prompt = request.getPrompt();

        out.println("[응모자 상세보기]");

        int no = prompt.promptInt("번호? ");

        Client c = clientService.get(no);

        if (c == null) {
            out.println("해당 번호의 응모자가 없습니다.");
            return;
        }

        //이름: %s 전화번호: %s 생년월일: %s 아이디: %s 사이즈:
        out.printf("이름: %s\n", c.getName());
        out.printf("전화번호: %s\n", c.getPhone_number());
        out.printf("생년월일: %s\n", c.getBirth_number());
        out.printf("NIKE 아이디: %s\n", c.getId());
        out.printf("사이즈: %s\n", c.getcSize());
    }
}