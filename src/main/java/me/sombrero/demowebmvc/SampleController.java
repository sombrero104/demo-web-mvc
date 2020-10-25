package me.sombrero.demowebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
// @RequestMapping(method = RequestMethod.GET) // 이 안의 모든 핸들러에 GET만 허용.
@RequestMapping("/hello")
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
    @GetMapping("/sombrero")
    @ResponseBody
    public String helloSombrero() {
        return "hello sombrero";
    }

}
