package me.sombrero.demowebmvc;

import javax.validation.constraints.Min;

public class Event {

    private Integer id;

    private String name;

    @Min(0) // 인원수 값을 넣을 때 최소 0명 이상은 되어야 한다고 알려줌. @Valid에서 검증함.
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
