package com.louddt.tag.entity.dbo;

import com.louddt.tag.entity.vo.task.TaskModifyBO;

import java.util.Date;

public class SysTask {
    private String id;

    private String taskName;

    private Integer taskType;

    private Integer subType;

    private String modelId;

    private Integer classifyNum;

    private Integer status;

    private String createUser;

    private Date createTime;

    private Date updateTime;

    private String directoryPath;

    public SysTask(){}

    public SysTask(TaskModifyBO taskBo) {
        this.taskName = taskBo.getTaskName();
        this.taskType = taskBo.getTaskType();
        this.subType = taskBo.getSubType();
        this.modelId = taskBo.getModelId();
        this.directoryPath = taskBo.getDirectoryPath();
        this.classifyNum = taskBo.getClassifyNum();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId == null ? null : modelId.trim();
    }

    public Integer getClassifyNum() {
        return classifyNum;
    }

    public void setClassifyNum(Integer classifyNum) {
        this.classifyNum = classifyNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath == null ? null : directoryPath.trim();
    }
}