package com.louddt.tag.entity.vo.task;

import java.util.ArrayList;
import java.util.List;

public class TaskDataJsonAnnotation {

    List<TaskDataJsonEntity> entity = new ArrayList<>();

    List<TaskDataJsonRelation> relation = new ArrayList<>();

    public List<TaskDataJsonEntity> getEntity() {
        return entity;
    }

    public void setEntity(List<TaskDataJsonEntity> entity) {
        this.entity = entity;
    }

    public List<TaskDataJsonRelation> getRelation() {
        return relation;
    }

    public void setRelation(List<TaskDataJsonRelation> relation) {
        this.relation = relation;
    }
}
