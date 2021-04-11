package com.osk2090.pms;

import com.osk2090.pms.dao.ClientDao;
import com.osk2090.pms.dao.mariadb.ClientDaoImpl;
import com.osk2090.pms.handler.*;
import com.osk2090.util.Prompt;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class ClientApp {
    // 사용자가 입력한 명령을 저장할 컬렉션 객체 준비
    ArrayDeque<Integer> commandStack = new ArrayDeque<>();
    LinkedList<Integer> commandQueue = new LinkedList<>();

    String serverAddress;
    int port;

    public static void main(String[] args) {
        ClientApp app = new ClientApp("localhost", 8888);

        try {
            app.execute();
        } catch (Exception e) {
            System.out.println("클라이언트 실행 중 오류 발생!");
            e.printStackTrace();
        }
    }

    public ClientApp(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public void execute() throws Exception {
        // Mybatis 설정 파일을 읽을 입력 스트림 객체 준비
        InputStream mybatisConfigStream = Resources.getResourceAsStream(
                "com/osk2090/pms/conf/mybatis-config.xml");

        // SqlSessionFactory 객체 준비
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(mybatisConfigStream);

        // DAO가 사용할 SqlSession 객체 준비
        // => 수동 commit 으로 동작하는 SqlSession 객체를 준비한다.
        SqlSession sqlSession = sqlSessionFactory.openSession(false);

        ClientDao clientDao = new ClientDaoImpl(sqlSession);

        HashMap<Integer, Command> commandMap = new HashMap<>();

        ClientStatusHandler clientStatusHandler = new ClientStatusHandler();
        AdminWinnerResultHandler adminWinnerResultHandler = new AdminWinnerResultHandler();

        ClientAddHandler clientAddHandler = new ClientAddHandler(clientDao);
        AdminCheckResultHandler adminCheckResultHandler = new AdminCheckResultHandler();
        AdminWinnerCheckHandler adminWinnerCheckHandler = new AdminWinnerCheckHandler();
        AdminLogicHandler adminLogicHandler = new AdminLogicHandler();
        ClientListHandler clientListHandler = new ClientListHandler(clientDao);
        ClientInfoHandler clientInfoHandler = new ClientInfoHandler();
        ClientDeleteHandler clientDeleteHandler = new ClientDeleteHandler(clientDao);
        ClientDetailHandler clientDetailHandler = new ClientDetailHandler(clientDao);

        commandMap.put(1, new ClientPrintOneHandler(clientAddHandler));
        commandMap.put(2, new ClientPrintTwoHandler(
                adminCheckResultHandler,
                adminLogicHandler,
                clientListHandler,
                adminWinnerResultHandler,
                clientDeleteHandler,
                clientDetailHandler));
//        commandMap.put(3, new ClientPrintThreeHandler( clientInfoHandler, adminWinnerCheckHandler));

        try {

            loop:
            while (true) {
                clientStatusHandler.statusPannel(adminWinnerResultHandler);
                int choice = Prompt.promptInt("-Nike-\n-Draw-\n1. 응모자 2. 관리자 3. 당첨자 수령하기 4. History 0. 종료");

                commandStack.push(choice);
                commandQueue.offer(choice);

                try {
                    switch (choice) {
                        case 4:
                            printCommandHistory(commandQueue.iterator());
                            break;
                        case 0:
                            System.out.println("종료합니다.");
                            break loop;
                        default:
                            Command commandHandler = commandMap.get(choice);

                            if (0 > choice || choice > 4) {
                                System.out.println("다시 선택해주세요.");
                            } else {
                                commandHandler.service();
                            }
                    }
                } catch (Exception e) {
                    System.out.println("==================================================");
                    System.out.printf("명령어 실행중 오류 발생: %s\n", e.getClass());
                    System.out.println("==================================================");
                }
            }
        } catch (Exception e) {
            System.out.println("서버와 통신하는 중에 오류 발생!");
            e.printStackTrace();
        }
        Prompt.close();
    }

    static void printCommandHistory(Iterator<Integer> iterator) {
        int count = 0;
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            if ((++count % 5) == 0) {
                String input = Prompt.promptString(": ");
                if (input.equalsIgnoreCase("q")) {
                    break;
                }
            }
        }
    }
}