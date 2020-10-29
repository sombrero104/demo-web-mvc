package me.sombrero.demowebmvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import java.util.List;

@Configuration
@EnableWebMvc
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

    /**
     * VisitTimeInterceptor 인터셉터 등록.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new VisitTimeInterceptor());
    }

    /**
     * MessageConverter 추가. (기본 MessageConverter들을 사용하면서 동시에 추가만 하고 싶을 경우.)
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

    }

    /**
     * MessageConverter 설정. (기본 MessageConverter들을 없애고 아예 새로 MessageConverter들을 등록할 경우.)
     */
    /*@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

    }*/

}
