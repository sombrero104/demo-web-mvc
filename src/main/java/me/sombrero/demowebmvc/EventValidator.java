package me.sombrero.demowebmvc;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class EventValidator implements Validator {

    /**
     * 어떠한 도메인 클래스에 대한 validation을 지원하는 것인지를 설정.
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Event.class.isAssignableFrom(clazz);
    }

    /**
     * Event 객체의 name이 'aaa'인 경우, 거부하는 메시지를 보낸다.
     */
    @Override
    public void validate(Object target, Errors errors) {
        Event event = (Event)target;
        if(event.getName().equalsIgnoreCase("aaa")) {
            errors.rejectValue("name", "wrongValue", "the value is not allowed");
        }
    }

}
