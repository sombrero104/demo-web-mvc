package me.sombrero.demowebmvc;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component // 커스텀한 Validator 자체를 빈으로 등록해서 사용해도 된다.
public class EventValidator { // 빈으로 등록할 경우, 굳이 Validator 인터페이스를 구현하지 않아도 된다.
// public class EventValidator implements Validator {

    /**
     * 어떠한 도메인 클래스에 대한 validation을 지원하는 것인지를 설정.
     */
    /*@Override
    public boolean supports(Class<?> clazz) {
        return Event.class.isAssignableFrom(clazz);
    }*/

    /**
     * Event 객체의 name이 'aaa'인 경우, 거부하는 메시지를 보낸다.
     */
    /*@Override
    public void validate(Object target, Errors errors) {
        Event event = (Event)target;
        if(event.getName().equalsIgnoreCase("aaa")) {
            errors.rejectValue("name", "wrongValue", "the value is not allowed");
        }
    }*/

    /**
     * EventValidator를 빈으로 등록해서 사용하는 경우.
     */
    public void validate(Event event, Errors errors) {
        if(event.getName().equalsIgnoreCase("aaa")) {
            errors.rejectValue("name", "wrongValue", "the value is not allowed");
        }
    }

}
