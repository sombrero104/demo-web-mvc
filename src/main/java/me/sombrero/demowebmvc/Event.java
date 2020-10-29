package me.sombrero.demowebmvc;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Event {

    /*interface ValidateName {}
    interface ValidateLimit {}*/

    private Integer id;

    // @NotBlank(groups = ValidateName.class)
    @NotBlank
    private String name;

    // @Min(0) // 인원수 값을 넣을 때 최소 0명 이상은 되어야 한다고 알려줌. @Valid에서 검증함.
    // @Min(value = 0, groups = ValidateLimit.class)
    @Min(0)
    private Integer limit;

    /**
     * @DateTimeFormat
     * 이 애노테이션을 이해하는 포매터가 기본적으로 이미 등록되어 있기 때문에 사용가능하다.
     * 폼에서 문자열로 입력해도 자동으로 LocalDate 타입으로 변환해준다.
     */
    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate; // 날짜만 받고 싶은 경우.
    // private LocalDateTime startDateTime; // 시간까지 받고 싶은 경우.

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
