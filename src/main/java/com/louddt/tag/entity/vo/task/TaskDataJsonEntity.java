package com.louddt.tag.entity.vo.task;

import java.util.ArrayList;
import java.util.List;

public class TaskDataJsonEntity {

    private Integer id;

    private String name;

    private String value;

    private Integer start;

    private Integer end;

    List<TaskDataJsonAttribute> attributes = new ArrayList<>();

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public List<TaskDataJsonAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<TaskDataJsonAttribute> attributes) {
        this.attributes = attributes;
    }
}
