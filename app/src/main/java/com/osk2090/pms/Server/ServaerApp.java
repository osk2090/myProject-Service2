package com.osk2090.pms.Server;

import com.osk2090.pms.Server.table.ClientTable;
import com.osk2090.pms.Server.table.DataTable;
import com.osk2090.pms.Server.util.Request;
import com.osk2090.pms.Server.util.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

//데이터를 파일에 보관하고 꺼내는 일을 할 애플리케이션
public class ServaerApp {

    int port;
    HashMap<String, DataTable> tableMap = new HashMap<>();

    public static void main(String[] args) {
        ServaerApp app = new ServaerApp(8888);
        app.service();
    }

    public ServaerApp(int port) {
        this.port = port;
    }

    public void service() {

        tableMap.put("client/", new ClientTable());

        //클라이언트 연결을 기다리는 서버 소켓 생성
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println("서버 실행!");

            while (true) {
                processRequest(serverSocket.accept());
            }
        } catch (Exception e) {
            System.out.println("서버 실행 중 오류 발생!");
            e.printStackTrace();
        }
    }

    private void processRequest(Socket socket) {
        try (DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {

            while (true) {
                Request request = receiveRequest(in);
                log(request);

                if (request.getCommand().equals("quit")) {
                    sendResponse(out, "success");
                    break;
                }

                DataTable dataTable = findDataTable(request.getCommand());

                if (dataTable != null) {
                    Response response = new Response();
                    try {
                        dataTable.service(request, response);
                        sendResponse(
                                out,
                                "success",
                                response.getDataList().toArray(new String[response.getDataList().size()]));
                    } catch (Exception e) {
                        sendResponse(
                                out,
                                "error",
                                e.getMessage() != null ? e.getMessage() : e.getClass().getName());
                    }
                } else {
                    sendResponse(out, "error", "해당 요청을 처리할 수 없습니다!");
                }
            }
        } catch (Exception e) {
            System.out.println("클라이언트의 요청을 처리하는 중에 오류 발생!");
            e.printStackTrace();
        }
    }

    private DataTable findDataTable(String command) {
        Set<String> keySet = tableMap.keySet();
        for (String key : keySet) {
            if (command.startsWith(key)) {
                return tableMap.get(key);
            }
        }
        return null;
    }

    private Request receiveRequest(DataInputStream in)throws Exception {
        Request request = new Request();

        request.setCommand(in.readUTF());

        int length = in.readInt();
        ArrayList<String> data = null;
        if (length > 0) {
            data = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                data.add(in.readUTF());
            }
            request.setData(data);
        }
        return request;
    }

    private void sendResponse(DataOutputStream out,
                              String status,
                              String... data)throws Exception {
        out.writeUTF(status);
        out.writeInt(data.length);
        for (int i = 0; i < data.length; i++) {
            out.writeUTF(data[i]);
        }
        out.flush();
    }

    private void log(Request request) {
        System.out.println("-----------------------------");
        System.out.printf("명령: %s\n", request.getCommand());

        List<String> data = request.getData();
        System.out.printf("데이터 개수: %d\n", data == null ? 0 : data.size());
        if (data != null) {
            System.out.printf("데이터:");
            for (String str : data) {
                System.out.println(str);
            }
        }
    }
}