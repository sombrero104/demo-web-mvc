package me.sombrero.demowebmvc;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

public class VisitTimeInterceptor implements HandlerInterceptor {

    /**
     * [처음 방문한 시간을 세션에 저장하기.]
     * 세션에 visitTime 애트리뷰트 값이 없으면 현재 서버 시간을 저장해줌.
     * 어떤 핸들러로 접근하던 처음 방문한 시간을 한번만 저장하게 된다.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if(session.getAttribute("visitTime") == null) {
            session.setAttribute("visitTime", LocalDateTime.now()); // 처음 방문한 시간.
        }
        return true;
    }

}
