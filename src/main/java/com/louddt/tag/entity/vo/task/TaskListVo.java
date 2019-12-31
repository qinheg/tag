package com.louddt.tag.entity.vo.task;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class TaskListVo {

    @ApiModelProperty("标注任务id")
    private String id;
    @ApiModelProperty("标注任务名称")
    private String taskName;
    @ApiModelProperty("标注任务类型 1-算法模型应用  2-文档标注")
    private Integer taskType;
    @ApiModelProperty("子类型： 1-文本分类  2-实体关系标注")
    private Integer subType;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("标注任务类型")
    private String taskTypeName;
    @ApiModelProperty("子类型")
    private String subTypeName;
    @ApiModelProperty("任务状态  0-未开始  1-进行中  2-已完成")
    private Integer status;
    @ApiModelProperty("更新时间字符串")
    private String updateTimeStr;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTaskTypeName() {
        return taskTypeName;
    }

    public void setTaskTypeName(String taskTypeName) {
        this.taskTypeName = taskTypeName;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }
}
