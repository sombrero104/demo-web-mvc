package me.sombrero.demowebmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventApiTest {

    @Autowired
    ObjectMapper objectMapper; // JSON을 객체로 변환, 객체를 JSON으로 변환.

    @Autowired
    MockMvc mockMvc;

    /*@Test
    public void createEvent() throws Exception {
        Event event = new Event();
        event.setName("sombrero104");
        event.setLimit(20);

        String jsonStr = objectMapper.writeValueAsString(event); // ObjectMapper로 Event 객체를 JSON 문자열로 변환.

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8) // 보내는 Content-Type이 무엇인지 알려줘야 한다. (그래야 맞는 컨버터가 선택되어 실행됨.)
                .content(jsonStr) // 본문(body)에 JSON 문자열을 보낸다.
                .accept(MediaType.APPLICATION_JSON)) // 어떠한 미디어 타입의 응답을 원하는지를 Accept 헤더에 담아서 보낸다.
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("sombrero104")) // 다시 결과로 받은 JSON을 jsonPath로 결과값 확인.
                .andExpect(jsonPath("limit").value(20));
    }*/

    @Test
    public void createEvent() throws Exception {
        Event event = new Event();
        event.setName("sombrero104");
        event.setLimit(-20);

        String jsonStr = objectMapper.writeValueAsString(event); // ObjectMapper로 Event 객체를 JSON 문자열로 변환.

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8) // 보내는 Content-Type이 무엇인지 알려줘야 한다. (그래야 맞는 컨버터가 선택되어 실행됨.)
                .content(jsonStr)) // 본문(body)에 JSON 문자열을 보낸다.
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}