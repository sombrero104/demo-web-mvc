# 스프링 MVC 활용
<br/>

# 요청 맵핑하기 
<br/>

## HTTP Method
- GET, POST, PUT, PATCH, DELETE, ...

### GET 요청
- 클라이언트가 서버의 리소스를 요청할 때 사용한다.
- 캐싱 할 수 있다. <br/>
    (조건적인 GET으로 바뀔 수 있다. 동일한 요청을 받았을 때 If-Modified-Since 시간을 보고<br/>
    304 응답코드(Not Modified)와 함께 응답 본문을 다시 보내지 않아도 <br/>
    클라이언트가 캐시하고 있는 그 정보 그대로 클라이언트에서 보여줌.<br/>
    요청 처리가 빠르고 서버쪽 리소스를 아낄 수 있음.)<br/>
- 브라우저 기록에 남는다.
- 북마크 할 수 있다.
- 민감한 데이터를 보낼 때 사용하지 말 것. (URL에 다 보이니까)
- idempotent(멱등): 동일한 GET 요청은 항상 동일한 응답을 리턴해야 한다. 

### POST 요청
- 클라이언트가 서버의 리소스를 수정하거나 새로 만들 때 사용한다. (idempotent하지 않다.)
- 서버에 보내는 데이터를 POST 요청 본문에 담는다. 
- 캐시할 수 없다.
- 브라우저 기록에 남지 않는다.
- 북마크 할 수 없다.
- 데이터 길이 제한이 없다.

### PUT 요청
- URI에 해당하는 데이터를 새로 만들거나 수정할 때 사용한다.
- POST와 다른 점은 "URI"에 대한 의미가 다르다.
    - POST의 URI는 보내는 **_데이터를 처리할_** 리소스를 지칭하며,
    - PUT의 URI는 보내는 데이터에 해당하는 **_리소스 자체_** 를 지칭한다.
- idempotent(멱등): 동일한 PUT 요청은 항상 동일한 응답을 리턴해야 한다. 

### PATCH 요청
- PUT과 비슷하지만, 기존 엔티티와 새 데이터의 차이점만 보낸다는 차이가 있다.
- idempotent(멱등): 동일한 PATCH 요청은 항상 동일한 응답을 리턴해야 한다. 

### DELETE 요청
- URI에 해당하는 리소스를 삭제할 때 사용한다.
- idempotent(멱등): 동일한 DELETE 요청은 항상 동일한 응답을 리턴해야 한다. 
<br/><br/><br/><br/>

# URI 패턴 맵핑

### URI, URL, URN ???
<img src="./images/uri.png" width="50%"><br/>
- URI (Uniform Resource Identifier)
- URL (Uniform Resource Locator)
- URN (Uniform Resource Name)<br/>

https://stackoverflow.com/questions/176264/what-is-the-difference-between-a-uri-a-url-and-a-urn <br/>
<br/>

## 요청 식별자로 맵핑하기
- @RequestMapping은 다음의 패턴을 지원한다.
- ?: 한 글자 ("/author/???" => "/author/123")
- \*: 여러 글자 ("/author/*" => "/author/sombrero104")
- \*\*: 여러 패스 ("/author/\*\*" => "/author/sombrero104/book")
- /{name:정규식}: 정규표현식 맵핑. (@GetMapping("/{name:\[a-z]+}"))

## 패턴이 중복되는 경우에는?
- 가장 구체적으로 맵핑되는 핸들러를 선택한다. ('/**' 보다는 '/구체적인경로'로 맵핑됨.)

## URI 확장자 맵핑 지원
- @GetMapping("/sombrero") 설정을 하면<br/>
스프링 MVC가 암묵적으로 @GetMapping({"/sombrero", "/sombrero.*"})로 설정을 해준다.<br/>
'sombrero.json', 'sombrero.xml', 'sombrero.html', 'sombrero.zip' 요청도 처리할 수 있게끔..<br/>
하지만 스프링부트는 기본적으로 이 기능을 사용하지 않도록 설정되어 있다. (보안 이슈가 있어서..)<br/>
- 이 기능은 권장하지 않는다. (스프링 부트에서는 기본으로 이 기능을 사용하지 않도록 설정해줌.)
    - 보안 이슈(RFD Attack (Reflected File Down Attack)) <br/>
    https://www.trustwave.com/en-us/resources/blogs/spiderlabs-blog/reflected-file-download-a-new-web-attack-vector/
    - URI 변수, Path 매개변수, URI 인코딩을 사용할 때 불명확함.<br/>
       (확장자인지 어떤 용도인지 모호함.<br/>
       예전에는 응답으로 어떤 타입을 원하는지를 확장자로 사용하기도 했었는데, <br/>
       최근에는 서버가 알아서 판단하는 Accept 헤더로 사용하는 추세.<br/>
       헤더에 값을 보내는 것이 불편한 상황이라면 파라미터로 사용 권장.)<br/>
<br/><br/><br/>

# 미디어 타입 맵핑

### 특정한 타입의 데이터를 담고 있는 요청만 처리하는 핸들러
- @RequestMapping(consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
- @RequestMapping(consumes="application/json")
- Content-Type 헤더로 필터링.
- 매치되지 않는 경우에 415(Unsupported Media Type) 응답.

### 특정한 타입의 응답을 만드는 핸들러
- @RequestMapping(produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
- @RequestMapping(produces="application/json")
- Accept 헤더로 필터링. (Accept를 아예 설정하지 않은 경우에도 응답.)
- 매치되지 않는 경우에 406(Not Acceptable) 응답. 

### * 클래스에 선언한 @RequestMapping에 사용한 것과 조합이 되지 않고, 메소드에 사용한 @RequestMapping의 설정으로 덮어쓴다. 
### * Not (!)을 사용해서 특정 미디어 타입이 아닌 경우로 맵핑 할 수도 있다. 
<pre>
@GetMapping(value = "/hello", headers = "!" + HttpHeaders.AUTHORIZATION)
</pre>
<br/>

## 헤더와 매개변수
- 특정한 헤더가 있는 요청을 처리하고 싶은 경우
    - @RequestMapping(headers = "key")
- 특정한 헤더가 없는 요청을 처리하고 싶은 경우
    - @RequestMapping(headers = "!key")
- 특정한 헤더 키값이 있는 요청을 처리하고 싶은 경우
    - @RequestMapping(headers = "key=123")
- 특정한 요청 매개변수 키를 가지고 있는 요청을 처리하고 싶은 경우
    - @RequestMapping(params = "name")
- 특정한 요청 매개변수가 없는 요청을 처리하고 싶은 경우
    - @RequestMapping(params = "!name")
- 특정한 요청 매개변수 키값을 가지고 있는 요청을 처리하고 싶은 경우
    - @RequestMapping(params = "name=123")
<br/>

## HEAD와 OPTIONS 요청 처리
우리가 구현하지 않아도 스프링 웹 MVC에서 자동으로 처리하는 HTTP Method.
### HEAD
- GET 요청과 동일하지만 응답 본문(body)을 받아오지 않고 **_응답 헤더만_** 받아온다.
- 리소스를 받기 전에 사전에 리소스에 대한 정보를 확인하기 위해 사용.
### OPTIONS
- 서버 또는 특정 리소스가 제공하는 기능을 확인할 수 있다.
- 해당 서버가 살아있는지 해당 리소스에 대한 요청을 처리할 수 있는지 확인할 수 있다. 
- 사용할 수 있는 HTTP Method 제공.
- 서버는 Allow 응답 헤더에 사용할 수 있는 HTTP Method 목록을 제공해야 한다.
<br/><br/>

## 커스텀 애노테이션
### 메타(Meta) 애노테이션
- 애노테이션에 사용할 수 있는 애노테이션.
- 스프링이 제공하는 대부분의 애노테이션은 메타 애노테이션으로 사용할 수 있다.
### 조합(Composed) 애노테이션
- 한개 혹은 여러 메타 애노테이션을 조합해서 만든 애노테이션.
- 코드를 간결하게 줄일 수 있다.
- 보다 구체적인 의미를 부여할 수 있다.
### @Retention
- 해당 애노테이션 정보를 언제까지 유지할 것인가. 디폴트는 Class. (자바의 RetentionPolicy)
- Source: 소스 코드까지만 유지. 즉, 컴파일하면 해당 애노테이션 정보는 사라진다.
- Class: 컴파일한 .class 파일에도 유지. 즉, 런타임 시 클래스를 메모리로 읽어오면 해당 정보는 사라진다.
- Runtime: 클래스를 메모리에 읽어왔을 때까지 유지! 코드에서 이 정보를 바탕으로 특정 로직을 실행할 수 있다.<br/>
    자바의 기본 RetentionPolicy는 Class인데, 스프링에서는 모든 구성이 런타임 때 형성되기 때문에<br/>
    스프링에서 커스텀 애노테이션을 만들때에는 꼭 @Retention을 Runtime으로 설정해줘야 한다. <br/>
    그냥 주석 정도의 역할로 사용하고 싶을 때에는 Source를 사용해도 된다. <br/>
### @Target
- 해당 애노테이션을 어디에 사용할 수 있는지 결정한다.
### @Document
- 해당 애노테이션을 사용한 코드의 문서에 그 애노테이션에 대한 정보를 표기할지 결정한다.<br/>
- 예를 들어, 커스텀 애노테이션 @GetHelloMapping를 만들고,<br/>
    이 애노테이션의 메타 애노테이션으로 @Document를 설정한 후,<br/>
    이 애노테이션을 SampleController에 있는 핸들러에서 사용하도록 작성하면,<br/>
    SampleController에 대한 javadoc을 만들 때 해당 핸들러 부분에<br/>
    커스텀 애노테이션인 @GetHelloMapping까지 표기가 된다. <br/>
<br/><br/>
<br/><br/><br/>

# 핸들러 메소드 
<br/>

## 요청 매개변수 
아래 두 경우를 서블릿은 요청 매개변수로 처리를 한다. (사실상 서블릿은 둘 다 동일하게 봄.)<br/>
- 쿼리 매개변수
    - Key/Value에 해당하는 데이터를 쿼리 파라미터로 보내는 것.
    - '/events?name=sombrero104'
- 폼 데이터
    - HTTP 요청 본문에 form 데이터로 보내는 경우. 
<br/>
    
## @RequestParam
핸들러에 @RequestParam을 설정해서 요청 매개변수(파라미터)를 받음. <br/>
@RequestParam 애노테이션을 생략할 수도 있으나, 헷갈릴 수도 있기 때문에 생략하지 않는 것을 권장. <br/>
<pre>
@RequestParam(required = true) String name
</pre>
위 처럼 required 옵션은 기본적으로 true임.<br/>
<pre>
@RequestParam(required = false, defaultValue = "sombrero104") String name
</pre>
위 처럼 해당 파라미터 값이 없어도 되고, 없는 경우에는 기본값으로 'sombrero104'를 쓰겠다고 할수도 있음. <br/>
<pre>
@RequestParam(value = "name", required = false, defaultValue = "sombrero104") String nameValue
</pre>
위 처럼 파라미터 변수명이 다를 경우 value에 이름을 줄수도 있음. (하지만 그냥 파라미터명과 변수명을 같게 하는 것을 권장.)<br/>
<br/><br/>

### * 타임리프 표현식 
- @{}: 링크 URL 표현식.
- ${}: variable 표현식. (자원)
- *{}: selection 표현식. (위 자원에서 선택한 것.)

https://www.thymeleaf.org/doc/articles/standarddialect5minutes.html <br/>
<br/><br/>

## @ModelAttribute
- 여러 곳에 있는 단순 타입 데이터를 복합 타입 객체로 받아오거나 해당 객체를 새로 만들 때 사용할 수 있다.
- 서블릿에서는 요청 매개변수를 받아오는 방법들에 대해 동일하게 판단하기 때문에..
- URI 패스, 요청 매개변수, 세션 등 여러곳의 데이터를 하나의 복합 객체로 받아올 수 있어서 편리하다. 
- @ModelAttribute 애노테이션을 생략할 수 있다. 
- 값을 바인딩 할 수 없는 경우에는? <br/>
(예를 들어, 받아와야 하는 매개변수 타입이 Integer로 선언되어 있는데 문자열이 들어왔을 경우)
    - 400 에러 BindException 발생.
- 바인딩 에러를 직접 다루고 싶은 경우, ('당신이 폼에 입력한 이러이러한 값이 잘못되었다.'라고 보여주고 싶을 경우 사용.)
    - BindingResult 타입의 아규먼트를 바로 오른쪽에 추가한다. 
    <pre>
    public Event getEvents(@ModelAttribute Event event, BindingResult bindingResult) { ... }
    </pre>
    - 이렇게 선언하면 bindingResult 변수에 바인딩 관련 에러를 담아주고, 요청은 정상적으로 처리된다. 
<br/>

## @Valid
- 바인딩 이후(바인딩은 정상적으로 수행되었지만)에 검증 작업을 추가로 하고 싶은 경우 사용. 
- @Valid에서 발생한 에러 역시 bindingResult에 담아준다. 
<pre>
public Event getEvents(@Valid @ModelAttribute Event event, BindingResult bindingResult) { ... }
</pre>
<pre>
public class Event {
    @Min(0) // 인원수 값을 넣을 때 최소 0명 이상은 되어야 한다고 알려줌. @Valid에서 검증함.
    private Integer limit;
    ...
}
</pre>
<pre>
mockMvc.perform(post("/events")
        .param("name", "sombrero104")
        .param("limit", "-10")) // @Valid 테스트. 마이너스 인원을 준 경우. 
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("name").value("sombrero104"));
</pre>
- bindingResult에 에러가 담겨지며, 응답은 정상적으로 200 응답코드이고, limit의 값은 -10이 나온다. 
<br/>

## @Validated
- @Valid처럼 검증도 가능하고, 덧붙여 그룹을 지정할 수 있다. 
<pre>
public class Event {
    interface ValidateName {}
    interface ValidateLimit {}

    @NotBlank(groups = ValidateName.class)
    private String name;
    
    @Min(value = 0, groups = ValidateLimit.class)
    private Integer limit;
    ...
}
</pre>
<pre>
public Event getEvents(@Validated(Event.ValidateLimit.class) 
    @ModelAttribute Event event, BindingResult bindingResult) { ... }
</pre>
<br/>

## @Validated, BindingResult, 타임리프로 Form 값 검증
<pre>
@PostMapping("/events")
public String getEvents(@Validated @ModelAttribute Event event, BindingResult bindingResult) {
    if(bindingResult.hasErrors()) { // bindingResult에 에러가 있으면..
        return "/events/form"; // form 페이지로.. 
    }
    return "/events/list";
}
</pre>
<pre>
public class Event {
    @NotBlank
    private String name;
    
    @Min(0)
    private Integer limit;
    ...
}
</pre>
<pre>
❮form action="#" th:action="@{/events}" method="post" th:object="${event}"❯
    ❮p th:if="${#fields.hasErrors('name')}" th:errors="*{name}"❯Incorrect name❮/p❯ // name 관련된 에러가 있으면..
    ❮p th:if="${#fields.hasErrors('limit')}" th:errors="*{limit}"❯Incorrect limit❮/p❯ // limit 관련된 에러가 있으면.. 
    ❮input type="text" title="name" th:field="*{name}" /❯
    ❮input type="text" title="limit" th:field="*{limit}" /❯
    ❮input type="submit" value="Create" /❯
❮/form❯
</pre>
실행한 후 폼 값을 잘못 입력하면, 아래와 같이 타임리프에서 제공하는 메세지가 자동으로 출력된다.<br/><br/>
<img src="./images/valid_form.png" width="50%"><br/>
<br/><br/>

## @SessionAttributes
모델 정보를 HTTP 세션에 저장해주는 애노테이션.  <br/>
(이 애노테이션이 설정된 해당 클래스 안에서만 @SessionAttributes에 정의한 이름에 대한 모델을 저장해줌..) <br/>
- HttpSession을 직접 사용할 수도 있지만..
- 이 애노테이션에 설정한 이름에 해당하는 모델 정보를 자동으로 세션에 넣어준다. 
- @ModelAttribute는 세션에 있는 데이터도 바인딩한다.
- 여러 화면(또는 요청)에서 사용해야 하는 객체를 공유할 때 사용한다.
- SessionStatus를 사용해서 세션 처리 완료를 알려줄 수 있다.
    - 폼 처리 끝나고 세션을 비울 때 사용한다.
<pre>
@SessionAttributes({"event"})
public class SampleController {

    @GetMapping("/events/form")
    // public String eventsForm(Model model, HttpSession httpSession) {
    public String eventsForm(Model model) {
        // Form Backing Object
        // 폼에 채워질 데이터를 받아오는 객체를 제공.
        Event newEvent = new Event();
        newEvent.setLimit(50);
        model.addAttribute("event", newEvent);
        // httpSession.setAttribute("event", newEvent); // 세션에 event 저장하기.
        /**
         * 위 처럼 'httpSession.setAttribute("event", newEvent);'로 작성하지 않아도
         * (HttpSession을 인자로 받아서 사용하는 것은 로우레벨..)
         * 컨트롤러에 애노테이션 '@SessionAttributes({"event"})'를 설정하면
         * 'event' 이름에 해당하는 모델 애트리뷰트를 세션에 자동으로 저장해 준다.
         *
         * 'model.addAttribute("event", newEvent);' 처럼 모델 애트리뷰트에 추가된,
         * 모델 애튜리뷰트에 있는 애튜리뷰트들 중에 
         * @SessionAttributes에 있는 이름과 같은 애트리뷰트를 세션에도 저장한다.
         *
         * 세션에 데이터를 넣는 이유?
         * 여러 페이지에서 유지되어야 하는 정보인 장바구니 기능과 같은 경우나,
         * 또는 어떤 데이터를 생성할 때 여러 페이지에 걸쳐서 만들어야 하는 경우,
         * 입력받아야 하는 값이 많아서 여러 화면(페이지)에 나눠서 폼을 만들어야 하는 경우,
         * (@ModelAttribute는 세션에 있는 데이터도 바인딩한다.)
         *
         */
        return "events/form";
    }
    
    @PostMapping("/events")
    public String createEvents(@Validated @ModelAttribute Event event,
                               BindingResult bindingResult, SessionStatus sessionStatus) {
        ...
        sessionStatus.setComplete(); // 폼 처리가 끝났을 때 세션이 만료되도록 함. (세션을 비우도록 함.)
        ...
    }
}
</pre>
<br/><br/>

## 멀티 폼 서브밋
세션을 사용해서 여러 폼에 걸쳐 데이터를 나눠 입력 받고 저장하기.
- 예를 들어.. 
- 첫번째 페이지에서 이벤트 이름 입력 받고 서브밋, (model에 저장하면 세션에도 저장.)
- 두번째 페이지에서 이벤트 제한 인원 입력 받고 서브밋, (model에 저장하면 세션에도 저장.)
- 마지막으로 세션을 비우고, 이벤트 목록으로 돌아가기. 
<pre>
/**
 * [멀티 폼 서브밋]
 * 세션을 사용해서 여러 폼에 걸쳐 데이터를 나눠 입력 받고 저장하기.
 * - 첫번째 페이지에서 이벤트 이름 입력 받고 서브밋, 
 * - 두번째 페이지에서 이벤트 제한 인원 입력 받고 서브밋, 
 * - 마지막으로 이벤트 목록으로 돌아가기. 
 */

/**
 * name 값만 받는 form
 */
@GetMapping("/events/form/name")
public String eventsFormName(Model model) {
    model.addAttribute("event", new Event());
    // @SessionAttributes({"event"}) 설정으로 인해 위 medel에 저장된 event가 세션에도 들어감.
    // 때문에 form-name 페이지에서 값을 수정하면 세션에도 동일하게 적용됨.
    return "events/form-name";
}

/**
 * @ModelAttribute가 세션에 있는 정보도 바인딩 받기 때문에
 * form-name 페이지에서 수정한 후 모델에 저장했던 event도 세션에 들어있기 때문에 바인딩함.
 */
@PostMapping("/events/form/name")
public String createEventsNameSubmit(
                @Validated @ModelAttribute Event event, // @ModelAttribute가 세션에 있는 정보도 바인딩 받음.
                           BindingResult bindingResult) {
    if(bindingResult.hasErrors()) { // bindingResult에 에러가 있으면..
        return "/events/form-name"; // form-name 페이지로..
    }
    // name 값을 입력받는 form 페이지 에서 name 값을 받고,
    // 별 문제가 없으면 (에러가 없으면)
    // 아래처럼 이번엔 limit을 입력받는 form 페이지로 이동하게 한다.
    return "redirect:/events/form/limit"; // limit을 입력받는 form 페이지.
}

/**
 * limit 값만 받는 form
 * @ModelAttribute로 세션에 있는 event를 가져온다.
 */
@GetMapping("/events/form/limit")
public String eventsFormLimit(@ModelAttribute Event event, Model model) { // @ModelAttribute로 세션에 있는 event를 가져온다.
    model.addAttribute("event", event); // 세션에서 받은 event를 그대로 전달.
    // @SessionAttributes({"event"}) 설정으로 인해 위 medel에 저장된 event가 세션에도 들어감.
    // 때문에 form-limit 페이지에서 값을 수정하면 세션에도 동일하게 적용됨.
    return "events/form-limit";
}

/**
 * @ModelAttribute가 세션에 있는 정보도 바인딩 받기 때문에
 * form-limit 페이지에서 수정한 후 모델에 저장했던 event도 세션에 들어있기 때문에 바인딩함.
 */
@PostMapping("/events/form/limit")
public String createEventsLimitSubmit(
        @Validated @ModelAttribute Event event, // @ModelAttribute가 세션에 있는 정보도 바인딩 받음.
        BindingResult bindingResult,
        SessionStatus sessionStatus) {
    if(bindingResult.hasErrors()) { // bindingResult에 에러가 있으면..
        return "/events/form-limit"; // form-limit 페이지로..
    }
    // limit 값을 입력받는 form 페이지 에서 name 값을 받고,
    // 별 문제가 없으면 (에러가 없으면)
    // 세션에 있는 정보를 지워주고..
    sessionStatus.setComplete(); // 세션 비우기.

    // 마지막으로 list 페이지를 보여줌.
    return "redirect:/events/list"; // list 페이지를 보여줌.
}

@GetMapping("/events/list")
public String getEvents(Model model) {
    Event event = new Event();
    event.setName("sombrero104");
    event.setLimit(10);

    List<Event> eventList = new ArrayList<>();
    eventList.add(event);
    model.addAttribute("eventList", eventList);

    return "/events/list";
}
</pre>
<br/><br/>

## @SessionAttribute
@SessionAttributes와는 완전히 다른 기능이다. (s가 안붙음! 헷갈림 주의!) <br/>

## "@SessionAttributes"  vs  "@SessionAttribute"
@SessionAttributes와의 다른점은.. <br/>
@SessionAttributes는 애노테이션의 정의된 해당 클래스 내에서만 적용이 된다.  <br/>
(@SessionAttributes는 해당 클래스 내에서만 모델에 있는 정보를 세션에 저장해줌.) <br/>
@SessionAttributes는 여러 컨트롤러에 걸쳐서 적용해주거나 하지 않는다.  <br/>
만약, 컨트롤러 밖에서 서블릿 필터나.. 스프링이 제공하는 인터셉터 같은 곳에서..  <br/>
세션에 뭔가를 넣어 놓았을 경우, 그것을 가져와서 쓰고 싶은 경우, @SessionAttribute를 사용할 수 있다.  <br/>
<br/><br/>

## @RedirectAttributes
리다이렉트 할 때 기본적으로 Model에 들어있는 primitive type 데이터는 URI 쿼리 매개변수에 추가된다. <br/>
- 스프링 부트에서는 이 기능이 기본적으로 비활성화 되어 있다. 
- Ignore-default-model-on-redirect 프로퍼티를 사용해서 활성화 할 수 있다. 
<pre>
@PostMapping("/events/form/limit")
public String createEventsLimitSubmit(..., Model model) {
    ...
    model.addAttribute("name", event.getName());
    model.addAttribute("limit", event.getLimit());
    ...
    return "redirect:/events/list"; // list 페이지로 리다이렉트. 
}
</pre>
위 처럼 model 애트리뷰트에 데이터를 추가한 후,<br/>
아래와 같이 application.properties에 Ignore-default-model-on-redirect 프로퍼티를 false 한다. <br/>
<pre>
spring.mvc.ignore-default-model-on-redirect=false
</pre>



<br/><br/><br/>