package me.sombrero.demowebmvc;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    /*@PostMapping
    public Event createEvent(HttpEntity<Event> request) {
        // event를 받아서 repository에 저장하는 부분.
        // 현재는 생략.

        MediaType contentType = request.getHeaders().getContentType(); // HttpEntity는 헤더정보도 들고 올 수 있다.
        System.out.println("##### contentType: " + contentType);

        // 저장 후 저장된 아이디를 가지고 있는 event를 리턴.
        return request.getBody(); // HttpMessageConverter가 본문을 Event 타입으로 컨버전해준다.
    }*/





    /**
     * [Valid 사용하기. & BindingResult로 에러 담기.]
     * BindingResult로 바인딩 에러를 담을수도 있다. (에러를 커스텀하게 코드에서 처리하고 싶은 경우.)
     *  => 원래 BindingResult를 선언하지 않으면 바인딩 에러가 발생했을 때, Model Attribute에서 400에러가 발생하는데..
     *      BindingResult를 선언하면, 바인딩 에러가 발생하더라도 400에러가 발생하진 않는다.
     *      그냥 에러를 담아주고 끝. 담겨진 에러를 우리가 처리해줘야 한다.
     *      커스텀하게 직접 400에러를 보내면서 에러 본문에 우리가 원하는 응답을 넣거나, status 값을 좀 더 구체적으로 바꿔주거나..
     *      우리가 원하는 에러 처리를 할 수 있다.
     */
    @PostMapping
    public Event createEvent(@RequestBody @Valid Event event, BindingResult bindingResult) {
        // event를 받아서 repository에 저장하는 부분.
        // 현재는 생략.

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error);
            });
        }

        // 저장 후 저장된 아이디를 가지고 있는 event를 리턴.
        return event;
    }

}
