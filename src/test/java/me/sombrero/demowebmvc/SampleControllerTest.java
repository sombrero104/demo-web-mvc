package me.sombrero.demowebmvc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @RunWith(SpringRunner.class)
 *  => 내부적으로 테스트에서 사용할 ApplicationContext를 만들어줌.
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    /**
     * JUnit 테스트는 public void로 해야 인식 가능.
     */
    @Test
    public void helloTest() throws Exception {
        // mockMvc.perform(get("/hello"))
        // mockMvc.perform(post("/hello"))
        // mockMvc.perform(delete("/hello"))
        /*mockMvc.perform(put("/hello"))
                .andDo(print())
                // .andExpect(status().isOk())
                // .andExpect(content().string("hello"));
                .andExpect(status().isMethodNotAllowed());*/ // 응답코드 405. (4xx: 요청이 잘못된 경우.)
        /**
         * MockHttpServletResponse:
         *            Status = 405
*              Error message = Request method 'PUT' not supported
         *           Headers = [Allow:"GET"]        // 허용하는 메소드가 GET이라는 것도 알려줌.
         */


        /*mockMvc.perform(get("/hello"))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(put("/hello"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
        mockMvc.perform(post("/hello"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());*/

        /*mockMvc.perform(get("/hello"))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("/hi"))
                .andDo(print())
                .andExpect(status().isOk());*/
        /*mockMvc.perform(get("/hello1"))
                .andDo(print())
                .andExpect(status().isOk());*/
        /*mockMvc.perform(get("/hello/1"))
                .andDo(print())
                .andExpect(status().isOk());*/
        /*mockMvc.perform(get("/hello/123/123"))
                .andDo(print())
                .andExpect(status().isOk());*/
        /*mockMvc.perform(get("/hello/sombrero"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello sombrero"))
                .andExpect(handler().handlerType(SampleController.class)) // 어떤 핸들러가 사용되는지 출력.
                .andExpect(handler().methodName("helloSombrero")); // 핸들러의 메소드는 'helloSombrero'일 것이다.*/
        /**
         * Handler:
         *  Type = me.sombrero.demowebmvc.SampleController
         *  Method = public java.lang.String me.sombrero.demowebmvc.SampleController.helloSombrero(java.lang.String)
         */


        /**
         * 스프링MVC에서는 확장자가 붙은 URI를 기본적으로 지원하지만,
         * 스프링부트에서는 기본적으로 막아놨다.
         */
        /*mockMvc.perform(get("/hello/sombrero.json"))
                .andDo(print())
                .andExpect(status().isNotFound());*/


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
        /*mockMvc.perform(get("/hello")
            .contentType(MediaType.APPLICATION_JSON_UTF8) // 요청의 Content-Type에 미디어 타입 설정.
            // .accept(MediaType.TEXT_PLAIN_VALUE)) // 응답으로 plain text를 원한다.
        )
                .andDo(print())
                .andExpect(status().isOk());*/




        /*mockMvc.perform(get("/hello")
            .header(HttpHeaders.FROM, "localhost"))
                .andDo(print())
                .andExpect(status().isOk());*/
        /*mockMvc.perform(get("/hello")
            .header(HttpHeaders.AUTHORIZATION, "111"))
                .andDo(print())
                .andExpect(status().isOk());*/
        /*mockMvc.perform(get("/hello")
            .param("name", "sombrero104"))
                .andDo(print())
                .andExpect(status().isOk());*/


        /**
         * HEAD: 응답에 body를 보내지 않고 헤더만 보냄.
         *  리소스를 받기 전에 사전에 리소스에 대한 정보를 확인하기 위해 사용.
         */
        mockMvc.perform(head("/hello")
                .param("name", "sombrero104"))
                .andDo(print())
                .andExpect(status().isOk());

    }

}