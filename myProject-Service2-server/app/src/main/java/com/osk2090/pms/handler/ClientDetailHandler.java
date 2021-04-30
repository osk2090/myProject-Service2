package com.osk2090.pms.handler;

import com.osk2090.pms.domain.Client;
import com.osk2090.pms.service.ClientService;
import com.osk2090.streotype.Component;
import com.osk2090.util.Prompt;

@Component(value = "/client/detail")
public class ClientDetailHandler implements Command {

    ClientService clientService;

    public ClientDetailHandler(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void service() throws Exception {
        System.out.println("[응모자 상세보기]");

        int no = Prompt.promptInt("번호? ");

        Client c = clientService.get(no);

        if (c == null) {
            System.out.println("해당 번호의 응모자가 없습니다.");
            return;
        }

        //이름: %s 전화번호: %s 생년월일: %s 아이디: %s 사이즈:
        System.out.printf("이름: %s\n", c.getName());
        System.out.printf("전화번호: %s\n", c.getPhone_number());
        System.out.printf("생년월일: %s\n", c.getBirth_number());
        System.out.printf("NIKE 아이디: %s\n", c.getId());
        System.out.printf("사이즈: %s\n", c.getcSize());
    }
}