package com.osk2090.pms.Server.table;

import com.osk2090.pms.Server.domain.Client;
import com.osk2090.pms.Server.util.JsonFileHandler;
import com.osk2090.pms.Server.util.Request;
import com.osk2090.pms.Server.util.Response;

import java.io.File;
import java.util.List;

public class ClientTable implements DataTable {

    File jsonFile = new File("clients.json");
    List<Client> list;

    public ClientTable() {
        list = JsonFileHandler.loadClients(jsonFile, Client.class);
    }

    @Override
    public void service(Request request, Response response) throws Exception {
        Client client = null;
        String[] fileds = null;

        switch (request.getCommand()) {
            case "client/insert":
                fileds = request.getData().get(0).split(",");
                client = new Client();

                //새 게시글의 번호
                if (list.size() > 0) {
                    client.setNo(list.get(list.size() - 1).getNo() + 1);
                } else {
                    client.setNo(1);
                }

                //인덱스 .이름: %s 전화번호: %s 생년월일: %s 아이디: %s 사이즈:
                client.setName(fileds[0]);
                client.setpN(fileds[1]);
                client.setbN(fileds[2]);
                client.setId(fileds[3]);
                client.setcSize(Integer.parseInt(fileds[4]));
                list.add(client);
                JsonFileHandler.saveClients(jsonFile, list);
                break;
            case "client/selectall":
                for (Client c : list) {
                    response.appendData(String.format("%d,%s,%d",
                            c.getNo(),
                            c.getId(),
                            c.getcSize()));
                }
                break;
            case "client/select":
                int no = Integer.parseInt(request.getData().get(0));

                client = getClient(no);
                if (client != null) {
                    response.appendData(String.format("%d,%s,%s,%s,%s,%d",
                            client.getNo(),
                            client.getName(),
                            client.getpN(),
                            client.getbN(),
                            client.getId(),
                            client.getcSize()));
                } else {
                    throw new Exception("해당 번호의 회원이 없습니다.");
                }
                break;
            case "client/update":
                fileds = request.getData().get(0).split(",");
                client = getClient(Integer.parseInt(fileds[0]));
                if (client == null) {
                    throw new Exception("해당 번호의 응모자가 없습니다.");
                }

                client.setpN(fileds[2]);
                client.setcSize(Integer.parseInt(fileds[5]));
                JsonFileHandler.saveClients(jsonFile, list);
                break;
            case "client/delete":
                no = Integer.parseInt(request.getData().get(0));
                client = getClient(no);
                if (client == null) {
                    throw new Exception("해당 번호의 응모자가 없습니다.");
                }

                list.remove(client);
                JsonFileHandler.saveClients(jsonFile, list);
                break;
            default:
                throw new Exception("해당 명령을 처리할 수 없습니다.");
        }
    }

    private Client getClient(int clientNo) {
        for (Client c : list) {
            if (c.getNo() == clientNo) {
                return c;
            }
        }
        return null;
    }
}