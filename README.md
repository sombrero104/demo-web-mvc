# 스프링 MVC 활용

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
<br/><br/><br/>

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

<br/><br/><br/>