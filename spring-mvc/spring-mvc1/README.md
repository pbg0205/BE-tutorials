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

- WAS를 만들 때마다 매번 
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

![image](https://user-images.githubusercontent.com/48561660/175236476-c351b822-8544-42b3-af6c-667b4222eccc.png)

- 톰캣과 같은 서블릿을 지원하는 WAS를 서블릿 컨테이너라고 한다.
- 서블릿 컨테이너는 서블릿 객체의 생명주기를 관리한다. (생성, 초기화, 호출, 종료)
  - 개발자는 서블릿 코드만 작성하면 자동으로 서블릿 컨테이너(WAS)를 생명주기를 관리한다.
- 서블릿 객체는 싱글톤으로 관리
  - 고객의 요청이 들어오면 각각의 ServletRequest, ServletResponse 인스턴스를 생성한다.
  - 같은 요청은 각각의 ServletRequest, ServletResponse 인스턴스를 생성해 동일한 서블릿 인스턴스에 접근한다.
  - 요청이 완료될 경우, ServletResponse 에 담아 응답한다.

<br>
