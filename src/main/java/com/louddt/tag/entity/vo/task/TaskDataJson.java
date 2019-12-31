package com.louddt.tag.entity.vo.task;

public class TaskDataJson {

    private TaskDataJsonAnnotation annotation = new TaskDataJsonAnnotation();

    private String content;

    public TaskDataJsonAnnotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(TaskDataJsonAnnotation annotation) {
        this.annotation = annotation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
