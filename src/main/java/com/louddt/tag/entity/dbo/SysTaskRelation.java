package com.louddt.tag.entity.dbo;

import java.util.Date;

public class SysTaskRelation {
    private String id;

    private String name;

    private String startNodeName;

    private String endNodeName;

    private String taskId;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getStartNodeName() {
        return startNodeName;
    }

    public void setStartNodeName(String startNodeName) {
        this.startNodeName = startNodeName == null ? null : startNodeName.trim();
    }

    public String getEndNodeName() {
        return endNodeName;
    }

    public void setEndNodeName(String endNodeName) {
        this.endNodeName = endNodeName == null ? null : endNodeName.trim();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}