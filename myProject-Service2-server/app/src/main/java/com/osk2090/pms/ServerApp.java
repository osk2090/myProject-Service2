package com.osk2090.pms;

import com.osk2090.mybatis.MybatisDaoFactory;
import com.osk2090.pms.dao.ClientDao;
import com.osk2090.pms.handler.*;
import com.osk2090.pms.service.ClientService;
import com.osk2090.pms.service.impl.DefaultClientService;
import com.osk2090.stereotype.Component;
import com.osk2090.util.Prompt;
import com.osk2090.util.concurrent.CommandRequest;
import com.osk2090.util.concurrent.CommandResponse;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ServerApp {

  int port;

  boolean isStop;

  // 객체를 보관할 컨테이너 준비
  Map<String,Object> objMap = new HashMap<>();

  public static void main(String[] args) {

    try {
      ServerApp app = new ServerApp(8888);
      app.service();

      } catch (Exception e) {
        System.out.println("서버를 시작하는 중에 오류 발생!");
        e.printStackTrace();
      }
  }

  public ServerApp(int port) {
    this.port = port;
  }

  public void service() throws Exception {
    ExecutorService threadPool = Executors.newCachedThreadPool();

    // Mybatis 설정 파일을 읽을 입력 스트림 객체 준비
    InputStream mybatisConfigStream = Resources.getResourceAsStream(
            "com/osk2090/pms/conf/mybatis-config.xml");

    // SqlSessionFactory 객체 준비
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(mybatisConfigStream);

    // DAO가 사용할 SqlSession 객체 준비
    // => 수동 commit 으로 동작하는 SqlSession 객체를 준비한다.
    SqlSession sqlSession = sqlSessionFactory.openSession(false);

    // DAO 구현체를 만들어주는 공장 객체를 준비한다.
    MybatisDaoFactory daoFactory = new MybatisDaoFactory(sqlSession);

    // 서비스 객체가 사용할 DAO 객체 준비
    ClientDao clientDao = daoFactory.createDao(ClientDao.class);

    // Command 구현체가 사용할 의존 객체 준비
    ClientService clientService = new DefaultClientService(sqlSession, clientDao);

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

    // Command 구현체가 사용할 의존 객체를 보관
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

    // Command 구현체를 자동 생성하여 맵에 등록
    registerCommands();

    try (ServerSocket serverSocket = new ServerSocket(this.port)) {
      System.out.println("서버 실행!");

      while (true) {
        Socket socket = serverSocket.accept();
        if (isStop) {
          break;
        }

        threadPool.execute(() -> processRequest(socket));
      }

    } catch (Exception e) {
      System.out.println("서버 실행 중 오류 발생!");
      e.printStackTrace();
    }

    threadPool.shutdown();
    System.out.println("서버 종료 중...");

    try {
      if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
        System.out.println("아직 실행 중인 스레드가 있습니다.");

        // 종료를 재시도 한다.
        // => 대기 중인 작업도 취소한다.
        // => 실행 중인 스레드 중에서 Not Runnable 상태에 있을 경우에도 강제로 종료시킨다.
        threadPool.shutdownNow();

        while (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
          System.out.println("아직 실행 중인 스레드가 있습니다.");
        }

        System.out.println("모든 스레드를 종료했습니다.");
      }
    } catch (Exception e) {
      System.out.println("스레드 강제 종료 중에 오류 발생!");
    }

    System.out.println("서버 종료!");
  }

  public void processRequest(Socket socket) {
    try (
            Socket clientSocket = socket;
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
    ) {

      // 클라이언트가 보낸 명령을 Command 구현체에게 전달하기 쉽도록 객체에 담는다.
      InetSocketAddress remoteAddr = (InetSocketAddress) clientSocket.getRemoteSocketAddress();

      // 클라이언트로부터 값을 입력 받을 때 사용할 객체를 준비한다.
      Prompt prompt = new Prompt(in, out);

      while (true) {
        // 클라이언트가 보낸 요청을 읽는다.
        String requestLine = in.readLine();

        // 클라이언트가 보낸 나머지 데이터를 읽는다.
        while (true) {
          String line = in.readLine();
          if (line.length() == 0) {
            break;
          }
          // 지금은 '요청 명령' 과 '빈 줄' 사이에 존재하는 데이터는 무시한다.
        }

        // 클라이언트 요청에 대해 기록(log)을 남긴다.
        System.out.printf("[%s:%d] %s\n",
                remoteAddr.getHostString(), remoteAddr.getPort(), requestLine);


        if (requestLine.equalsIgnoreCase("serverstop")) {
          out.println("Server stopped!");
          out.println();
          out.flush();
          terminate();
          return;
        }

        if (requestLine.equalsIgnoreCase("exit") || requestLine.equalsIgnoreCase("quit")) {
          out.println("Goodbye!");
          out.println();
          out.flush();
          return;
        }

        // 클라이언트의 요청을 처리할 Command 구현체를 찾는다.
        Command command = (Command) objMap.get(requestLine);
        if (command == null) {
          out.println("해당 명령을 처리할 수 없습니다!");
          out.println();
          out.flush();
          continue;
        }

        CommandRequest request = new CommandRequest(
                requestLine,
                remoteAddr.getHostString(),
                remoteAddr.getPort(),
                prompt);

        CommandResponse response = new CommandResponse(out);

        // Command 구현체를 실행한다.
        try {
          command.service(request, response);
        } catch (Exception e) {
          out.println("서버 오류 발생!");
          e.printStackTrace();
        }
        out.println();
        out.flush();
      }

    } catch (Exception e) {
      System.out.println("클라이언트의 요청을 처리하는 중에 오류 발생!");
      e.printStackTrace();
    }
  }

  // 서버를 최종적으로 종료하는 일을 한다.
  private void terminate() {
    // 서버 상태를 종료로 설정한다.
    isStop = true;

    // 그리고 서버가 즉시 종료할 수 있도록 임의의 접속을 수행한다.
    // => 스스로 클라이언트가 되어 ServerSocket 에 접속하면
    //    accept()에서 리턴하기 때문에 isStop 변수의 상태에 따라 반복문을 멈출 것이다.
    try (Socket socket = new Socket("localhost", 8888)) {
      // 서버를 종료시키기 위해 임의로 접속하는 것이기 때문에 특별히 추가로 해야 할 일이 없다.
    } catch (Exception e) {}
  }

  private void registerCommands() throws Exception {

    // 패키지에 소속된 모든 클래스의 타입 정보를 알아낸다.
    ArrayList<Class<?>> components = new ArrayList<>();
    loadComponents("com.osk2090.pms.handler", components);

    for (Class<?> clazz : components) {

      // 클래스 목록에서 클래스 정보를 한 개 꺼내, Command 구현체인지 검사한다.
      if (!isCommand(clazz)) {
        continue;
      }

      // 클래스 정보를 이용하여 객체를 생성한다.
      Object command = createCommand(clazz);

      // 클래스 정보에서 @Component 애노테이션 정보를 가져온다.
      Component compAnno = clazz.getAnnotation(Component.class);

      // 애노테이션 정보에서 맵에 객체를 저장할 때 키로 사용할 문자열 꺼낸다.
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
    // 클래스가 아니라 인터페이스라면 무시한다.
    if (type.isInterface()) {
      return false;
    }

    // 클래스의 인터페이스 목록을 꺼낸다.
    Class<?>[] interfaces = type.getInterfaces();

    // 클래스가 구현한 인터페이스 중에서 Command 인터페이스가 있는지 조사한다.
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
    // 생성자 정보를 알아낸다. 첫 번째 생성자만 꺼낸다.
    Constructor<?> constructor = clazz.getConstructors()[0];

    // 생성자의 파라미터 정보를 알아낸다.
    Parameter[] params = constructor.getParameters();

    // 생성자를 호출할 때 넘겨 줄 값을 담을 컬렉션을 준비한다.
    ArrayList<Object> args = new ArrayList<>();

    // 각 파라미터의 타입을 알아낸 후 objMap에서 찾는다.
    for (Parameter p : params) {
      Class<?> paramType = p.getType();
      args.add(findDependency(paramType));
    }

    // 생성자를 호출하여 인스턴스를 생성한다.
    return constructor.newInstance(args.toArray());
  }

  private Object findDependency(Class<?> type) {
    // 맵에서 값 목록을 꺼낸다.
    Collection<?> values = objMap.values();

    for (Object obj : values) {
      if (type.isInstance(obj)) {
        return obj;
      }
    }
    return null;
  }
}