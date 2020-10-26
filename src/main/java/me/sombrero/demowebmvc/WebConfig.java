package me.sombrero.demowebmvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * @MatrixVariable
     * 핸들러 파라미터에 @MatrixVariable 추가.
     *
     * '/events/1;name=sombrero104'과 같이 요청할 수 있는 방법.
     * 사용하려면 uri에서 세미콜론(;)을 없애지 않도록 WebConfig에 configurePathMatch를 설정해줘야 함..
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }

}
