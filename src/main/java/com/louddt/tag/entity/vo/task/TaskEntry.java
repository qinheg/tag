package com.louddt.tag.entity.vo.task;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class TaskEntry {

    @ApiModelProperty("实体/分类/关系名称")
    private String name;
    @ApiModelProperty("属性")
    private List<TaskEntryProperty> properties;
    @ApiModelProperty("起始实体，只有关系时字段才有用")
    private String startNodeName;
    @ApiModelProperty("终止实体，只有关系时字段才有用")
    private String endNodeName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TaskEntryProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<TaskEntryProperty> properties) {
        this.properties = properties;
    }

    public String getStartNodeName() {
        return startNodeName;
    }

    public void setStartNodeName(String startNodeName) {
        this.startNodeName = startNodeName;
    }

    public String getEndNodeName() {
        return endNodeName;
    }

    public void setEndNodeName(String endNodeName) {
        this.endNodeName = endNodeName;
    }
}
