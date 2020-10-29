package me.sombrero.demowebmvc;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

// @ControllerAdvice // 전역 컨트롤러가 된다. (모든 컨트롤러에 다 적용.)
// @ControllerAdvice(assignableTypes = EventController.class) // Event 컨트롤러에만 적용.
@ControllerAdvice(assignableTypes = {EventController.class, EventApi.class}) // Event, EventApi 컨트롤러에만 적용.
public class BaseController {

    /**
     * [ @ExceptionHandler ]
     * 여러 에러를 처리하고 싶은 경우.
     */
    @ExceptionHandler({EventException.class, RuntimeException.class}) // 여러 에러를 같이 처리하고 싶은 경우.
    public String eventErrorHandler(RuntimeException exception, Model model) { // 두 에러를 다 받을 수 있는 상위 타입으로 정의해야 한다.
        model.addAttribute("message", "runtime error");
        return "error"; // error 페이지 뷰를 보여준다.
    }

    /**
     * [ @InitBinder ]
     * 바인더를 커스텀하게 설정하기.
     * 이 컨트롤러의 모든 핸들러 메소드 요청 전에 이 바인더 메소드가 실행된다.
     * WebDataBinder 인자는 반드시 있어야 한다.
     */
    @InitBinder("event") // 'event'라는 이름의 모델 애트리뷰트를 바인딩 받을 때에만 @InitBinder를 사용하도록 설정할 수 있다.
    public void initEventBinder(WebDataBinder webDataBinder) {
        // 받고 싶지 않은 필드 값을 걸러낼 수 있다.
        // (원치 않는 데이터가 수정이 될수도 있기 때문에..)
        // 아래처럼 설정하면 폼에서 id 값을 입력하더라도 요청 받을 때에는 null로 받는다.
        webDataBinder.setDisallowedFields("id");
        // webDataBinder.addCustomFormatter(...); // 직접 커스텀한 포매터 등록.
        // webDataBinder.setValidator(new EventValidator()); // 커스텀한 Validator 등록.
    }

    /**
     * [ @ModelAttribute ]
     * 이 컨트롤러 내의 모든 핸들러의 모델에 동일하게 애트리뷰트를 추가하고 싶을 경우.
     * 예를 들어, 아래와 같이 공통적인 카테고리를 추가하고 싶은 경우.
     */
    @ModelAttribute
    public void categories(Model model) {
        model.addAttribute("categories", List.of("study", "seminar", "hobby", "social"));
    }

}
