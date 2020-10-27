package me.sombrero.demowebmvc;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class Event {

    interface ValidateName {}
    interface ValidateLimit {}

    private Integer id;

    @NotBlank(groups = ValidateName.class)
    private String name;

    // @Min(0) // 인원수 값을 넣을 때 최소 0명 이상은 되어야 한다고 알려줌. @Valid에서 검증함.
    @Min(value = 0, groups = ValidateLimit.class)
    private Integer limit;

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
}
