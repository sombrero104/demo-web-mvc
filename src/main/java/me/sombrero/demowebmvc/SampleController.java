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
    @GetMapping("/{name:[a-z]+}") // 정규 표현식으로 매핑.
    @ResponseBody
    public String hello(@PathVariable String name) {
        return "hello " + name;
    }

}
