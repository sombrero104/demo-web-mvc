package me.sombrero.demowebmvc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    }

}