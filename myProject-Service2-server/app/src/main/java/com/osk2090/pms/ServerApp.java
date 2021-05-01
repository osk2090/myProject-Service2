package com.osk2090.pms;

import com.osk2090.mybatis.MybatisDaoFactory;
import com.osk2090.mybatis.SqlSessionFactoryProxy;
import com.osk2090.mybatis.TransactionManager;
import com.osk2090.pms.dao.ClientDao;
import com.osk2090.pms.handler.*;
import com.osk2090.pms.service.ClientService;
import com.osk2090.pms.service.impl.DefaultClientService;
import com.osk2090.stereotype.Component;
import com.osk2090.util.*;
import com.osk2090.util.concurrent.CommandRequest;
import com.osk2090.util.concurrent.CommandResponse;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ServerApp {

  CommandRequest request;
  CommandResponse response;

  int port;

  boolean isStop;

  // 객체를 보관할 컨테이너 준비
  Map<String,Object> objMap = new HashMap<>();

  //필터를 보관할 컬렉션 준비
  List<Filter> filters = new ArrayList<>();

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
    SqlSessionFactoryProxy sqlSessionFactoryProxy = new SqlSessionFactoryProxy(sqlSessionFactory);

    // DAO 구현체를 만들어주는 공장 객체를 준비한다.
    MybatisDaoFactory daoFactory = new MybatisDaoFactory(sqlSessionFactoryProxy);

    // 서비스 객체가 사용할 DAO 객체 준비
    ClientDao clientDao = daoFactory.createDao(ClientDao.class);

    TransactionManager txManager = new TransactionManager(sqlSessionFactoryProxy);

    // Command 구현체가 사용할 의존 객체 준비
    ClientService clientService = new DefaultClientService(clientDao);

    ClientStatusHandler clientStatusHandler = new ClientStatusHandler();
    AdminWinnerResultHandler adminWinnerResultHandler = new AdminWinnerResultHandler();

    ClientAddHandler clientAddHandler = new ClientAddHandler(clientService);
    AdminCheckResultHandler adminCheckResultHandler = new AdminCheckResultHandler();
    AdminWinnerCheckHandler adminWinnerCheckHandler = new AdminWinnerCheckHandler();
    AdminLogicHandler adminLogicHandler = new AdminLogicHandler(request, response);
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
    registerCommandsFilter();

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

      Session session = new Session();

      String requestLine = in.readLine();

      while (true) {
        // 클라이언트가 보낸 요청을 읽는다.
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

      // 클라이언트의 요청을 처리할 Command 구현체를 찾는다.
      Command command = (Command) objMap.get(requestLine);
      if (command == null) {
        out.println("해당 명령을 처리할 수 없습니다!");
        out.println();
        out.flush();
        return;
      }

      request = new CommandRequest(
              requestLine,
              remoteAddr.getHostString(),
              remoteAddr.getPort(),
              prompt,
              session);

      response = new CommandResponse(out);

      FilterList filterList = new FilterList();

      CommandFilter commandFilter = new CommandFilter(command);

      filterList.add(commandFilter);

      for (Filter filter : filters) {
        filterList.add(filter);
      }

      out.println("OK");
      out.println();

      // Command 구현체를 실행한다.
      try {
        filterList.getHeaderChain().doFilter(request, response);

        out.println();
        out.flush();
      } catch (Exception e) {
        out.println("서버 오류 발생!");
        out.println();
        out.flush();
        throw e;
      }
    } catch (Exception e) {
      System.out.println("클라이언트의 요청을 처리하는 중에 오류 발생!");
      e.printStackTrace();
    }
  }


  private void registerCommandsFilter() throws Exception {

    // 패키지에 소속된 모든 클래스의 타입 정보를 알아낸다.
    ArrayList<Class<?>> components = new ArrayList<>();
    loadComponents("com.osk2090.pms", components);

    for (Class<?> clazz : components) {
      if (isType(clazz, Command.class)) {
        prepareCommand(clazz);
      } else if (isType(clazz, Filter.class)) {
        prepareFilter(clazz);
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

  private void prepareCommand(Class<?> clazz)throws Exception {
    Object command = createObject(clazz);
    Component compAnno = clazz.getAnnotation(Component.class);

    String key = null;
    if (compAnno.value().length() == 0) {
      key = clazz.getName();
    } else {
      key = compAnno.value();
    }
    objMap.put(key, command);
    System.out.println("Command 생성 ===> " + command.getClass().getName());
  }

  private void prepareFilter(Class<?>clazz)throws Exception {
    Object filter = createObject(clazz);
    filters.add((Filter) filter);

    System.out.println("Filter 생성 : " + clazz.getName());
  }

  private boolean isType(Class<?> type,Class<?> target) {
    if (type.isInterface()) {
      return false;
    }

    Class<?>[] interfaces = type.getInterfaces();

    for (Class<?> i : interfaces) {
      if (i == target) {
        return true;
      }
    }
    return false;
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

  private Object createObject(Class<?> clazz) throws Exception {
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
