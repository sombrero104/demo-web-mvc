package me.sombrero.demowebmvc;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventApi {

    /**
     * HttpMessageConverter를 사용해서
     * 요청 받은 본문을 Event로 Converting한다.
     */
    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        // event를 받아서 repository에 저장하는 부분.
        // 현재는 생략.
        // 저장 후 저장된 아이디를 가지고 있는 event를 리턴.
        return event;
    }

}
