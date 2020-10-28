package me.sombrero.demowebmvc;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest // 스프링부트의 전반적인 빈을 사용할 수 있음. 하지만 @WebMvcTest처럼 @AutoConfigureMockMvc을 자동으로 등록해주지는 않음.
@AutoConfigureMockMvc // @SpringBootTest를 쓰면서 MVC 테스트를 하고 싶은 경우에는 직접 설정해줘야 함. // 그래야 MockMvc를 주입받아서 사용할 수 있다.
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

}