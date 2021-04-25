package com.osk2090.pms;

import com.osk2090.mybatis.MybatisDaoFactory;
import com.osk2090.pms.dao.ClientDao;
import com.osk2090.pms.handler.*;
import com.osk2090.pms.service.ClientService;
import com.osk2090.pms.service.impl.DefaultClientService;
import com.osk2090.streotype.Component;
import com.osk2090.util.Prompt;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.*;

public class ClientApp {
//    // 사용자가 입력한 명령을 저장할 컬렉션 객체 준비
//    ArrayDeque<Integer> commandStack = new ArrayDeque<>();
//    LinkedList<Integer> commandQueue = new LinkedList<>();

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

    Map<String, Object> objMap = new HashMap<>();

    public void execute() throws Exception {
        // Mybatis 설정 파일을 읽을 입력 스트림 객체 준비
        InputStream mybatisConfigStream = Resources.getResourceAsStream(
                "com/osk2090/pms/conf/mybatis-config.xml");

        // SqlSessionFactory 객체 준비
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(mybatisConfigStream);

        // DAO가 사용할 SqlSession 객체 준비
        // => 수동 commit 으로 동작하는 SqlSession 객체를 준비한다.
        SqlSession sqlSession = sqlSessionFactory.openSession(false);

        MybatisDaoFactory daoFactory = new MybatisDaoFactory(sqlSession);

        ClientDao clientDao = daoFactory.createDao(ClientDao.class);

        ClientService clientService = new DefaultClientService(sqlSession, clientDao);

//        HashMap<Integer, Command> commandMap = new HashMap<>();

        ClientStatusHandler clientStatusHandler = new ClientStatusHandler();
        AdminWinnerResultHandler adminWinnerResultHandler = new AdminWinnerResultHandler();

        ClientAddHandler clientAddHandler = new ClientAddHandler(clientService);
        AdminCheckResultHandler adminCheckResultHandler = new AdminCheckResultHandler();
        AdminWinnerCheckHandler adminWinnerCheckHandler = new AdminWinnerCheckHandler();
        AdminLogicHandler adminLogicHandler = new AdminLogicHandler();
        ClientListHandler clientListHandler = new ClientListHandler(clientService);
        ClientInfoHandler clientInfoHandler = new ClientInfoHandler();
        ClientDeleteHandler clientDeleteHandler = new ClientDeleteHandler(clientService);
        ClientDetailHandler clientDetailHandler = new ClientDetailHandler(clientService);

//        commandMap.put(1, new ClientPrintOneHandler(clientAddHandler));
//        commandMap.put(2, new ClientPrintTwoHandler(
//                adminCheckResultHandler,
//                adminWinnerCheckHandler,
//                adminLogicHandler,
//                clientListHandler,
//                adminWinnerResultHandler,
//                clientDeleteHandler,
//                clientDetailHandler,
//                clientDao));
//        commandMap.put(3, new ClientPrintThreeHandler(clientInfoHandler, adminWinnerCheckHandler, clientDao));

        objMap.put("1", new ClientPrintOneHandler(clientAddHandler));
        objMap.put("2", new ClientPrintTwoHandler(
                adminCheckResultHandler,
                adminWinnerCheckHandler,
                adminLogicHandler,
                clientListHandler,
                adminWinnerResultHandler,
                clientDeleteHandler,
                clientDetailHandler,
                clientDao));
        objMap.put("3", new ClientPrintThreeHandler(clientInfoHandler, adminWinnerCheckHandler, clientDao));

        registerCommands();

        try {

            loop:
            while (true) {
                clientStatusHandler.statusPannel(adminWinnerResultHandler,clientDao);
                String choice = String.valueOf(Prompt.promptInt("-Nike-\n-Draw-\n1. 응모자 2. 관리자 3. 당첨자 수령하기 4. History 0. 종료"));
//                int choice = Prompt.promptInt("-Nike-\n-Draw-\n1. 응모자 2. 관리자 3. 당첨자 수령하기 4. History 0. 종료");

//                commandStack.push(choice);
//                commandQueue.offer(choice);

                try {
                    switch (choice) {
                        case "4":
//                            printCommandHistory(commandQueue.iterator());
                            break;
                        case "0":
                            System.out.println("종료합니다.");
                            break loop;
                        default:
                            Command commandHandler = (Command) objMap.get(choice);

                            if (commandHandler == null) {
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
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("서버와 통신하는 중에 오류 발생!");
            e.printStackTrace();
        }
        sqlSession.close();
        Prompt.close();
    }

    private void registerCommands() throws Exception {
        ArrayList<Class<?>> components = new ArrayList<>();
        loadComponents("com.osk2090.pms.handler", components);

        for (Class<?> clazz : components) {

            if (!isCommand(clazz)) {
                continue;
            }

            Object command = createCommand(clazz);

            Component compAnno = clazz.getAnnotation(Component.class);

            String key = null;
            if (compAnno.value().length() == 0){
                key = clazz.getName(); // 키로 사용할 문자열이 없으면 클래스 이름을 키로 사용한다.
            } else {
                key = compAnno.value();
            }

            // 생성된 객체를 객체 맵에 보관한다.
            objMap.put(key, command);

            System.out.println("인스턴스 생성 ===> " + command.getClass().getName());
        }
    }

    private boolean isCommand(Class<?> type) {
        if (type.isInterface()) {
            return false;
        }
        Class<?>[] interfaces = type.getInterfaces();

        for (Class<?> i : interfaces) {
            if (i == Command.class) {
                return true;
            }
        }
        return false;
    }

    private void loadComponents(String packageName, ArrayList<Class<?>> components) throws Exception {

        // 패키지의 '파일 시스템 경로'를 알아낸다.
        File dir = Resources.getResourceAsFile(packageName.replaceAll("\\.", "/"));

        if (!dir.isDirectory()) {
            throw new Exception("유효한 패키지가 아닙니다.");
        }

        File[] files = dir.listFiles(f -> {
            if (f.isDirectory() || f.getName().endsWith(".class"))
                return true;
            return false;
        });

        for (File file : files) {
            if (file.isDirectory()) {
                loadComponents(packageName + "." + file.getName(), components);
            } else {
                String className = packageName + "." + file.getName().replace(".class", "");
                try {
                    Class<?> clazz = Class.forName(className);
                    if (clazz.getAnnotation(Component.class) != null) {
                        components.add(clazz);
                    }
                } catch (Exception e) {
                    System.out.println("클래스 로딩 오류: " + className);
                }
            }
        }
    }
    private Object createCommand(Class<?> clazz) throws Exception {
        Constructor<?> constructor = clazz.getConstructors()[0];

        Parameter[] params = constructor.getParameters();

        ArrayList<Object> args = new ArrayList<>();

        for (Parameter p : params) {
            Class<?> paramType = p.getType();
            args.add(findDependency(paramType));
        }

        return constructor.newInstance(args.toArray());
    }

    private Object findDependency(Class<?> type) {
        Collection<?> values = objMap.values();

        for (Object obj : values) {
            if (type.isInstance(obj)) {
                return obj;
            }
        }
        return null;
    }


//    static void printCommandHistory(Iterator<Integer> iterator) {
//        int count = 0;
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//            if ((++count % 5) == 0) {
//                String input = Prompt.promptString(": ");
//                if (input.equalsIgnoreCase("q")) {
//                    break;
//                }
//            }
//        }
//    }
}