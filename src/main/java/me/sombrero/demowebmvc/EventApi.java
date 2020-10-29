package me.sombrero.demowebmvc;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * [HttpMessageConverter]
 * JSON 요청 받기.
 * JSON 요청을 객체로 변환해서 받기.
 */
@RestController
@RequestMapping("/api/events")
public class EventApi {

    /**
     * [ 1. @RequestBody 사용하는 방법. ]
     * 스프링의 핸들러어댑터가 HttpMessageConverter를 사용해서
     * 요청 받은 본문을 Event로 Converting한다.
     */
    /*@PostMapping
    public Event createEvent(@RequestBody Event event) {
        // event를 받아서 repository에 저장하는 부분.
        // 현재는 생략.
        // 저장 후 저장된 아이디를 가지고 있는 event를 리턴.
        return event;
    }*/

    /**
     * [ 2. HttpEntity 사용하는 방법. ]
     * @RequestBody와 다른점은..
     * HttpEntity는 본문 외에도 헤더 정보를 들고 올 수 있다.
     */
    @PostMapping
    public Event createEvent(HttpEntity<Event> request) {
        // event를 받아서 repository에 저장하는 부분.
        // 현재는 생략.

        MediaType contentType = request.getHeaders().getContentType(); // HttpEntity는 헤더정보도 들고 올 수 있다.
        System.out.println("##### contentType: " + contentType);

        // 저장 후 저장된 아이디를 가지고 있는 event를 리턴.
        return request.getBody(); // HttpMessageConverter가 본문을 Event 타입으로 컨버전해준다.
    }

}
