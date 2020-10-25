package me.sombrero.demowebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

    /**
     * HTTP Method를 지정하지 않으면 모든 HTTP Method를 다 허용함.
     */
    // @RequestMapping("/hello")
    @RequestMapping(value = "/hello", method = { RequestMethod.GET, RequestMethod.PUT }) // GET만 허용.
    @ResponseBody
    public String hello() {
        return "hello";
    }

}
