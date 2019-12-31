package com.louddt.tag.entity.vo.task;

import java.util.List;

public class TaskDataJsonRelation {

    private String name;

    private Integer from;

    private Integer to;

    List<TaskDataJsonAttribute> attributes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public List<TaskDataJsonAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<TaskDataJsonAttribute> attributes) {
        this.attributes = attributes;
    }
}
