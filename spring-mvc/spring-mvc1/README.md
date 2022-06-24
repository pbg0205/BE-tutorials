# 스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술

## 1. 웹 어플리케이션 이해

### (1) 서블릿

- 만약 WAS를 직접 구현해야 한다면 아래와 같은 일련의 작업을 모두 수행해야 한다.

```
1. 서버 TCP/IP 연결 대기, 소켓 연결
2. HTTP 요청 메시지 파싱해서 읽기
3. POST 방식, /save URL 인지
4. Content-Type 확인
5. HTTP 메시지 바디 내용 파싱
    - username, age 데이터를 사용할 수 있게 파싱
6. 저장 프로세스 실행

<-- 비즈니스 로직 -->
7. 비즈니스 로직 실행
    - DB에 저장 요청
<---------------->

8. HTTP 응답 메시지 생성 시작
    - HTTP 시작 라인 생성
    - Header 생성
    - 메시지 바디에 HTML 생성에서 입력
9. TCP/IP에 응답 전달, 소켓 종료
```

- WAS를 만들 때마다 매번 HTTP message를 파싱하는 로직을 만드는 것은 비효율적이다.
- 서블릿을 지원하는 WAS는 위에 7번을 제외한 모든 것을 지원해준다.
- 서블릿 예시
    ```java
    @WebServlet(name = "helloServlet", urlPattern = "/hello")
    public class HelloServlet extends HttpServlet {
        @Override
        protected void service(HttpServletRequest request, HttpServletResponse response) {
            //애플리케이션 로직
        }
    }
    ```
    - urlPattern(/hello)의 URL이 호출되면 서블릿 코드 실행
    - 개발자는 HTTP 스펙을 편리하게 사용할 수 있다.

<br>

### (2) 서블릿 컨테이너

<img width="600" alt="image" src="https://user-images.githubusercontent.com/48561660/175236476-c351b822-8544-42b3-af6c-667b4222eccc.png">

- 톰캣과 같은 서블릿을 지원하는 WAS를 서블릿 컨테이너라고 한다.
- 서블릿 컨테이너는 서블릿 객체의 생명주기를 관리한다. (생성, 초기화, 호출, 종료)
  - 개발자는 서블릿 코드만 작성하면 자동으로 서블릿 컨테이너(WAS)를 생명주기를 관리한다.
- 서블릿 객체는 싱글톤으로 관리
  - 고객의 요청이 들어오면 각각의 ServletRequest, ServletResponse 인스턴스를 생성한다.
  - 같은 요청은 각각의 ServletRequest, ServletResponse 인스턴스를 생성해 동일한 서블릿 인스턴스에 접근한다.
  - 요청이 완료될 경우, ServletResponse 에 담아 응답한다.

<br>

### (3) 쓰레드

- 서블릿 객체를 호출하는 주체
- 자바 메인 메서드를 처음 실행하면 main 이라는 이름의 쓰레드가 실행
- 쓰레드는 한번에 하난의 코드 라인만 수행
- 동시 처리가 필요하면 쓰레드를 추가로 생성

<br>

### (4) 쓰레드 풀

**요청마다 쓰레드를 생성 시, 단점**

- 쓰레드 생성 비용은 비싸다. 동시 요청의 경우 요청마다 쓰레드를 생성하면 응답 속도가 늦어진다.
- 또한 쓰레드는 컨텍스트 스위칭 비용이 발생하고 쓰레드 생성에 제한이 없어 고객 요청이 너무 많을 경우, CPU, 메모리의 임계점을 넘어 서버가 다운될 수 있다.

<br>

**쓰레드 풀**

<img width="600" alt="image" src="https://user-images.githubusercontent.com/48561660/175248272-f62cfd4c-b66f-45b9-9cf4-6eec077b571f.png">

`특징`

- 필요한 쓰레드를 쓰레드 풀에 보관 및 관리한다.
- 쓰레드 풀에 생성 가능한 쓰레드 최대치를 관리한다. (Tomcat Default 200)
  - 별도로 maxThreads를 설정할 수도 있다. (server.xml)
  ```
     <Connector port="8080" address="localhost" maxThreads="250" maxHttpHeaderSize="8192" emptySessionPath="true" protocol="HTTP/1.1" enableLookups="false" redirectPort="8181" acceptCount="100" connectionTimeout="20000" disableUploadTimeout="true" />
  ```

`사용`

- 쓰레드가 필요하면, 이미 생성되어 있는 쓰레드 풀에서 꺼내서 사용한다.
- 사용을 종료하면 쓰레드 풀에 해당 쓰레드를 반납한다.
- 최대 쓰레드가 모두 사용 중일 경우, 요청 거절 또는 숫자만큼 대기 설정을 할 수 있다.

`장점`

- 쓰레드가 미리 생성되어 있으므로, 쓰레드 생성, 종료 비용이 절약되 응답 속도가 빠르다.
- 생성 가능한 쓰레드의 최대치가 존재하여 많은 요청에 대해 기존 요청을 안전하게 처리할 수 있다.

`실무 팁`

- WAS의 주요 튜닝 포인트는 최대 쓰레드(max thread) 이다.
  - 낮을 경우, 동시 요청이 많을 때 리소스는 여유롭지만 클라이언트에게 응답이 지연된다.
  - 높을 경우, CPU, 메모리 리소스 임계점 초과로 서버가 다운된다. (서버를 매번 생성하는 요소와 같다.)
  - 적정 숫자는 로직 복잡도와 CPU, 메모리, IO 리소스 상황에 따라 모두 다르다.
    - 성능 테스트 : 최대한 실제 서비스와 유사한 성능 테스트 시도
      - tool : 아파치, ab, 제이미터, nGrinder


- 장애 발생 시
  - 클라우드일 경우 스케일 아웃하고 이후 튜닝
  - 클라우드가 아니면 열심히 튜닝

<br>

### (3) 쓰레드

- 서블릿 객체를 호출하는 주체
- 자바 메인 메서드를 처음 실행하면 main 이라는 이름의 쓰레드가 실행
- 쓰레드는 한번에 하난의 코드 라인만 수행
- 동시 처리가 필요하면 쓰레드를 추가로 생성

<br>

### (4) 쓰레드 풀

**요청마다 쓰레드를 생성 시, 단점**

- 쓰레드 생성 비용은 비싸다. 동시 요청의 경우 요청마다 쓰레드를 생성하면 응답 속도가 늦어진다.
- 또한 쓰레드는 컨텍스트 스위칭 비용이 발생하고 쓰레드 생성에 제한이 없어 고객 요청이 너무 많을 경우, CPU, 메모리의 임계점을 넘어 서버가 다운될 수 있다.

<br>

**쓰레드 풀**

<img width="600" alt="image" src="https://user-images.githubusercontent.com/48561660/175248272-f62cfd4c-b66f-45b9-9cf4-6eec077b571f.png">

`특징`

- 필요한 쓰레드를 쓰레드 풀에 보관 및 관리한다.
- 쓰레드 풀에 생성 가능한 쓰레드 최대치를 관리한다. (Tomcat Default 200)
  - 별도로 maxThreads를 설정할 수도 있다. (server.xml)
  ```
     <Connector port="8080" address="localhost" maxThreads="250" maxHttpHeaderSize="8192" emptySessionPath="true" protocol="HTTP/1.1" enableLookups="false" redirectPort="8181" acceptCount="100" connectionTimeout="20000" disableUploadTimeout="true" />
  ```

`사용`

- 쓰레드가 필요하면, 이미 생성되어 있는 쓰레드 풀에서 꺼내서 사용한다.
- 사용을 종료하면 쓰레드 풀에 해당 쓰레드를 반납한다.
- 최대 쓰레드가 모두 사용 중일 경우, 요청 거절 또는 숫자만큼 대기 설정을 할 수 있다.

`장점`

- 쓰레드가 미리 생성되어 있으므로, 쓰레드 생성, 종료 비용이 절약되 응답 속도가 빠르다.
- 생성 가능한 쓰레드의 최대치가 존재하여 많은 요청에 대해 기존 요청을 안전하게 처리할 수 있다.

`실무 팁`

- WAS의 주요 튜닝 포인트는 최대 쓰레드(max thread) 이다.
  - 낮을 경우, 동시 요청이 많을 때 리소스는 여유롭지만 클라이언트에게 응답이 지연된다.
  - 높을 경우, CPU, 메모리 리소스 임계점 초과로 서버가 다운된다. (서버를 매번 생성하는 요소와 같다.)
  - 적정 숫자는 로직 복잡도와 CPU, 메모리, IO 리소스 상황에 따라 모두 다르다.
    - 성능 테스트 : 최대한 실제 서비스와 유사한 성능 테스트 시도
      - tool : 아파치, ab, 제이미터, nGrinder


- 장애 발생 시
  - 클라우드일 경우 스케일 아웃하고 이후 튜닝
  - 클라우드가 아니면 열심히 튜닝

<br>

## HttpServletRequest & Response 메서드

<img width="757" alt="image" src="https://user-images.githubusercontent.com/48561660/175526308-06453eec-5038-4a22-9800-6ea2855123f3.png">

<br>

### 1. HttpRequest

`StartLine`

```text
--- REQUEST-LINE - start ---
request.getMethod() : GET
request.getProtocol() : HTTP/1.1
request.getScheme() : http
request.getRequestURL() : http://localhost:8080/request-header
request.getRequestURI() : /request-header
request.getQueryString() : null
request.isSecure() : false
--- REQUEST-LINE - end ---
```

`Request Header`

```
--- Header 편의 조회 start ---
[Host 편의 조회]
request.getServerName() =  : localhost 
request.getServerPort() =  : 8080 
[Accept-Language 편의 조회]
locale = ko_KR
locale = ko
locale = en_US
locale = en
request.getLocale() : ko_KR
```

`Cookie 편의 조회`
```java
if (request.getCookies() != null) {
    for (Cookie cookie : request.getCookies()) {
        log.info("{} : {}", cookie.getName(), cookie.getValue());
    }
}
```

```
[cookie 편의 조회]
Idea-b788e82c : 7fae297b-abda-4da7-8d1f-13436261f0e9
Webstorm-e072a532 : b8759a6c-cb47-4547-96a9-129fa3b3ddcb
ab.storage.userId.7af503ae-0c84-478f-98b0-ecfff5d67750 : %7B%22g%22%3A%226730558%22%2C%22c%22%3A1655035472530%2C%22l%22%3A1655035472548%7D
ab.storage.deviceId.7af503ae-0c84-478f-98b0-ecfff5d67750 : %7B%22g%22%3A%229fe9612e-90b0-04d5-e3de-73d950108e46%22%2C%22c%22%3A1655035472556%2C%22l%22%3A1655035472556%7D
ab.storage.sessionId.7af503ae-0c84-478f-98b0-ecfff5d67750 : %7B%22g%22%3A%22de61b88e-9b00-ce13-6d32-b05065a9e727%22%2C%22e%22%3A2155997920380%2C%22c%22%3A1655035472540%2C%22l%22%3A1655997920380%7D
```

`Content 편의 조회`

```text
[Content 편의 조회]
request.getContentType() : null
request.getContentLength() : -1
```

### 2. HttpServletResponse

`Response Header`
```java
@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) {
    // [status-line]
    response.setStatus(HttpServletResponse.SC_OK);

    // [response-headers]
    response.setHeader("Content-Type", "text/plain;charset=utf-8");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("my-header","hello");

    //[Header 편의 메서드]
    content(response);
    cookie(response);
    redirect(response);


    //[message body]
    PrintWriter writer = response.getWriter();
    writer.println("ok");
  }

  private void content(HttpServletResponse response) {
    //Content-Type: text/plain;charset=utf-8
    // Content-Length: 2
    response.setHeader("Content-Type", "text/plain;charset=utf-8");
    response.setContentType("text/plain");
    response.setCharacterEncoding("utf-8");
    response.setContentLength(2); //(생략시 자동 생성)
  }

  private void cookie(HttpServletResponse response) {
    //Set-Cookie: myCookie=good; Max-Age=600;
    response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
    Cookie cookie = new Cookie("myCookie", "good");
    cookie.setMaxAge(600); //600초
    response.addCookie(cookie);
  }

  private void redirect(HttpServletResponse response) throws IOException {
    //Status Code 302
    //Location: /basic/hello-form.html
    response.setStatus(HttpServletResponse.SC_FOUND); //302
    response.setHeader("Location", "/basic/hello-form.html");
    response.sendRedirect("/basic/hello-form.html");
  }
  
}

```
- `Content-Type : text/plain;charset=utf-8`
  - 응답하는 리소스의 media type 을 나타내기 위해 사용하는 헤더
- `Cache-Control : no-cache, no-store, must-revalidate`
  - Cache-Control : 확실한 캐시 무효화 응답 (EX.고객의 통장 잔고 : 통장 잔고는 언제든 바뀔 수 있기 때문에 캐싱하면 안됨)
    - no-cache : 데이터는 캐시해도 되지만 원서버의 검증을 받아야 함(원서버 접근 실패 시 캐시에 있는 정보 반환)
    - must-revalidate : 캐시 만료 후, 최초 조회시 원서버에 검증해야 함. (원서버 접근 실패시 반드시 504(time out 반환))
- `Pragma: no-cache`
  - Pragma : no-cache 와 같지만 HTTP 1.0 하위 호환 (주로 글로벌 서비스에서 많이 사용)

<br>

`response json body`
```java
@WebServlet(name = "responseJsonServlet", urlPatterns = "response-json")
public class ResponseJsonServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Content-Type: application/json
        response.setHeader("Content-Type", "application/json");
        response.setCharacterEncoding("utf-8"); // application/json 사용시, utf-8 필요 X

        Employee employee = Employee.create("kim", 20);

        String result = objectMapper.writeValueAsString(employee); //object -> json
        response.getWriter().write(result);
    }

}

```

- **application/json** 은 스펙상 utf-8 형식을 사용하도록 정의되어 있어 같이 사용할 필요가 없다.
  - **response.getWriter()** 를 사용하면 추가 파라미터를 자동으로 추가해버린다.
  - 이때는 **response.getOutputStream()** 으로 출력하면 그런 문제가 없다.
