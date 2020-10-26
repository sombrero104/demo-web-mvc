package me.sombrero.demowebmvc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.PushBuilder;
import java.io.*;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

@Controller
// @RequestMapping(method = RequestMethod.GET) // 이 안의 모든 핸들러에 GET만 허용.
// @RequestMapping("/hello")
public class SampleController {

    /**
     * HTTP Method를 지정하지 않으면 모든 HTTP Method를 다 허용함.
     */
    // @RequestMapping("/hello")
    // @RequestMapping(value = "/hello", method = { RequestMethod.GET, RequestMethod.PUT }) // GET, PUT만 허용.
    /*@DeleteMapping
    @PutMapping
    @PatchMapping*/
    // @GetMapping({"/hello", "/hi"})
    // @GetMapping("/hello?") // 한 글자.
    // @GetMapping("/hello/?") // 한 글자.
    // @GetMapping("/hello/**") // 여러 패스.
    // @GetMapping("/**") // 여러 패스.
    /*@GetMapping("/{name:[a-z]+}") // 정규 표현식으로 매핑.
    public String hello(@PathVariable String name) { // 패스에서 파라미터를 받아옴.
        return "hello " + name;
    }*/

    /*@GetMapping("/sombrero")
    @ResponseBody
    public String helloSombrero() {
        return "hello sombrero";
    }

    @GetMapping("/**")
    @ResponseBody
    public String hello() {
        return "hello";
    }*/

    /**
     * @GetMapping("/sombrero") 설정을 하면
     * 스프링 MVC가 암묵적으로 @GetMapping({"/sombrero", "/sombrero.*"})로 설정을 해준다.
     * 'sombrero.json', 'sombrero.xml', 'sombrero.html', 'sombrero.zip' 요청도 처리할 수 있게끔..
     * 하지만 스프링부트는 기본적으로 이 기능을 사용하지 않도록 설정되어 있다.
     */
    // @GetMapping({"/sombrero", "/sombrero.*"})
    /*@GetMapping("/sombrero")
    @ResponseBody
    public String helloSombrero() {
        return "hello sombrero";
    }*/

    /**
     * 핸들러 메소드의 @GetMapping에..
     *
     * consumes를 설정할 경우,
     * consumes에 설정한 미디어타입이 Content-Type 헤더에 들어있는 경우에만 이 요청이 처리됨.
     * 해당 미디어타입의 요청이 아닐 경우 415(Unsupported Type) 응답코드를 전달함.
     *
     * produces를 설정할 경우,
     * produces에 설정한 미디어타입이 Accept 헤더에 들어있는 경우에만 이 요청이 처리됨.
     * 해당 미디어타입으로 응답받기를 원하는 요청이 아닐 경우 406(Not Acceptable) 응답코드를 전달함.
     * Accept 헤더가 설정되지 않은 경우에도 요청을 처리함.
     */
    /*@GetMapping(
            value = "/hello",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, // 요청 타입이 json이 미디어 타입인 경우에만 처리함.
            produces = MediaType.TEXT_PLAIN_VALUE // '응답으로 plain text만 원한다'라고 하는 요청만 처리함.
    )*/

    /**
     * FROM이라는 헤더가 들어있는 요청만 처리하겠다는 뜻.
     */
    // @GetMapping(value = "/hello", headers = HttpHeaders.FROM)

    /**
     * AUTHORIZATION이라는 헤더가 없으면 요청을 처리하겠다는 뜻.
     */
    // @GetMapping(value = "/hello", headers = "!" + HttpHeaders.AUTHORIZATION)

    /**
     * AUTHORIZATION이라는 헤더값이 111인 경우 요청을 처리하겠다는 뜻.
     */
    // @GetMapping(value = "/hello", headers = HttpHeaders.AUTHORIZATION + "=" + "111")

    /**
     * name이라는 파라미터가 있으면 요청을 처리하겠다는 뜻.
     */
    // @GetMapping(value = "/hello", params = "name")

    /**
     * name 파라미터 값이 'sombrero104'일 경우 요청을 처리하겠다는 뜻.
     * 파라미터 값이 다를 경우 400(Bad Request) 응답코드가 전달됨.
     */
    // @GetMapping(value = "/hello", params = "name=sombrero104")



    /*@GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @PostMapping("/hello")
    @ResponseBody
    public String helloPost() {
        return "hello";
    }*/


    /**
     * 커스텀 애노테이션 만들기.
     */
    /*@GetHelloMapping
    @ResponseBody
    public String hello() {
        return "hello";
    }*/

    /**
     * WebRequest
     * 사용할 일은 거의 없..
     */
    /*@GetMapping("/events")
    @ResponseBody
    public String events(WebRequest request) {
        request.getHeader("Allow");
        request.getParameterMap();
        request.getUserPrincipal();
        request.getSessionId();

        return "events";
    }*/

    /**
     * NativeWebRequest
     * 사용할 일은 거의 없..
     */
    /*@GetMapping("/events")
    @ResponseBody
    public String events(NativeWebRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request.getNativeRequest();
        HttpServletResponse httpServletResponse = (HttpServletResponse) request.getNativeResponse();

        return "events";
    }*/

    /**
     * HttpServletRequest, HttpServletResponse, InputStream, OutputStream
     * 사용할 일이 거의 없는..
     */
    /*@GetMapping("/events")
    @ResponseBody
    public void events(HttpServletRequest request, HttpServletResponse response
            // , InputStream requestBody, OutputStream responseBody
            // , Reader requestBody, Writer responseBody
            , BufferedReader requestBody, BufferedWriter responseBody) {
        try {
            // HttpServletRequest에서 요청본문을 가져오고 싶을 때, InputStream을 가져올 수도 있다. 사용할 일은 없지만..
            // InputStream inputStream = request.getInputStream();
            // BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // 리더도 있다.... 사용할 일은 없지만..
            // BufferedReader reader = request.getReader();

            // InputStream 자체를 파라미터에서도 가져올 수 있다. 사용할 일은 없지만..
            // InputStream inputStream = requestBody;
            BufferedReader reader = requestBody;
            String line = null;
            while ((line = reader.readLine()) != null) {
                response.getWriter().println(line);
            }

            // 서블릿처럼 로우레벨로 응답 본문에 직접 쓸 수도 있다. 사용할 일은 없지만..
            response.getWriter().println("events!! :D");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


    /**
     * PushBuilder
     * 스프링5에 새로 추가된 PushBuilder
     * HTTP2에서 사용할 수 있는 방법.
     * 원래는 사용자가 웹 페이지를 요청하면 서버가 응답으로 어떠한 이미지가 필요한지 알려주고,
     * 다시 사용자가 필요한 이미지를 요청하는 식인데
     * HTTP2에서는 사용자가 필요한 이미지를 요청하기도 전에 PushBuilder가 이미지를 미리 보내버린다.
     * 그래서 사용자가 필요한 이미지에 대한 요청을 할 필요가 없어진다.
     */
    /*@GetMapping("/events")
    @ResponseBody
    public String events(PushBuilder pushBuilder) {
        return "events";
    }*/


    /**
     * HttpMethod
     */
    /*@RequestMapping("/events")
    @ResponseBody
    public String events(HttpMethod httpMethod) {
        // 이 핸들러에서 받을 수 있는 HTTP Method가 여러개인 경우,
        // HttpMethod를 사용하면
        // 현재 들어온 요청의 HTTP Method가 무엇인지 알 수 있다.
        System.out.println(httpMethod.name());
        return "events";
    }*/


    /**
     * Locale, TimeZone, ZoneId
     * LocaleResolver가 분석한 요청의 Locale 정보.
     */
    /*@RequestMapping("/events")
    @ResponseBody
    public String events(Locale locale, TimeZone timeZone, ZoneId zoneId) {
        return "events";
    }*/

    /**
     * ResponseEntity
     * 응답 STATUS 코드.
     * 조건에 따라 응답 STATUS 코드를 다르게 하고 싶을 경우 사용.
     * 결국엔 사용하게 될 기능..
     */
    /*@GetMapping("/events")
    public ResponseEntity<String> events() {
        ResponseEntity<String> responseEntity = ResponseEntity.ok().build();
        return responseEntity;
    }*/


    /**
     * HttpHeaders
     * 본문 없이 응답 헤더만 리턴하고 싶을 경우.
     */
    /*@GetMapping("/events")
    public HttpHeaders events() {
        HttpHeaders httpHeaders = new HttpHeaders();
        // httpHeaders.setAllow(...);
        // httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }*/


    /**
     * View
     * ViewResolver를 사용하지 않고, 직접 View를 지정해주는 경우..
     */
    /*@GetMapping("/events")
    public View events() {
        // ...
        return view;
    }*/


    /**
     * Model
     * Model을 반환하고 뷰 경로를 반환하지 않으면 어떻게 뷰를 찾는가?
     * RequestToViewNameTranslator가 뷰 네임을 유추해서 찾아주면 ViewResolver가 렌더링해줌.
     * 기본적으로는 uri path와 똑같음. 그래서 결국 events라는 뷰를 찾아감.
     */
    /*@GetMapping("/events")
    public Model events() {
        // ...
        return model;
    }*/


    /**
     * @PathVariable(required = false) 또는 Optional<>
     */
    /*@GetMapping("/events/{id}")
    @ResponseBody
    // public Event events(@PathVariable(required = false) Integer id) { // 굳이 id 파라미터가 없어도 된다는 의미.
    public Event events(@PathVariable Optional<Integer> id) { // 위에서 설정한 'required = false'와 같은 의미.
        Event event = new Event();
        // 대신 null일 가능성이 있으니 null 체크를 해줘야 함.
        if(!id.isEmpty()) {
            event.setId(id.get());
        }
        return event; // @ResponseBody를 해주면 자동으로 JSON 형태로 반환됨.
    }*/


    /**
     * @MatrixVariable
     * 핸들러 파라미터에 @MatrixVariable 추가.
     *
     * '/events/1;name=sombrero104'과 같이 요청할 수 있는 방법.
     * 사용하려면 WebConfig에 configurePathMatch를 설정해줘야 함..
     */
    @GetMapping("/events/{id}")
    @ResponseBody
    public Event events(@PathVariable Integer id, @MatrixVariable String name) {
        Event event = new Event();
        event.setId(id);
        event.setName(name);
        return event; // @ResponseBody를 해주면 자동으로 JSON 형태로 반환됨.
    }

}
