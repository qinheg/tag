package com.louddt.tag.entity.vo.task;

import com.louddt.tag.utils.Query;
import io.swagger.annotations.ApiModelProperty;

public class TaskListQuery extends Query {

    @ApiModelProperty(value = "标注类型  1-算法模型应用  2-文档标注" , required = false)
    private Integer taskType;
    @ApiModelProperty(value = "子类型  1-文本分类  2-实体关系标注" , required = false)
    private Integer subType;
    @ApiModelProperty(value = "用户id",hidden = true)
    private String userId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
