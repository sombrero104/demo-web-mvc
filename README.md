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

## 요청 매개변수 
아래 두 경우를 서블릿은 요청 매개변수로 처리를 한다. (사실상 서블릿은 둘 다 동일하게 봄.)<br/>
핸들러에 @RequestParam을 설정해서 파라미터를 받음. @RequestParam 애노테이션을 생략할 수도 있음.<br/>
- 쿼리 매개변수
    - Key/Value에 해당하는 데이터를 쿼리 파라미터로 보내는 것.
    - '/events?name=sombrero104'
- 폼 데이터
    - HTTP 요청 본문에 form 데이터로 보내는 경우. 



<br/><br/><br/>