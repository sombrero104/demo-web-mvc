package me.sombrero.demowebmvc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest // 스프링부트의 전반적인 빈을 사용할 수 있음. 하지만 @WebMvcTest처럼 @AutoConfigureMockMvc을 자동으로 등록해주지는 않음.
@AutoConfigureMockMvc // @SpringBootTest를 쓰면서 MVC 테스트를 하고 싶은 경우에는 직접 설정해줘야 함. // 그래야 MockMvc를 주입받아서 사용할 수 있다.
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void fileUploadTest() throws Exception {
        // 가짜 파일 만들기.
        // new MockMultipartFile([name: form에서 사용한 이름], [OriginalFilename: 실제 업로드한 파일 이름], [Content-Type], [파일에 들어갈 본문]);
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.txt", "text/plain", "hello file".getBytes());

        // multipart는 POST 요청이고, enctype이 form-data(multipart/form-data)이다.
        // 아래 multipart() 함수가 이런 설정들이 되어 있는 함수라고 생각하면 된다.
        this.mockMvc.perform(multipart("/file").file(file))
            .andDo(print())
            .andExpect(status().is3xxRedirection());
    }

}