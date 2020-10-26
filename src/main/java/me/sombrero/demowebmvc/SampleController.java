package me.sombrero.demowebmvc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/hello", headers = "!" + HttpHeaders.AUTHORIZATION)
    @ResponseBody
    public String hello() {
        return "hello";
    }

}
