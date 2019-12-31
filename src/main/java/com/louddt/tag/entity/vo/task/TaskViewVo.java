package com.louddt.tag.entity.vo.task;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

public class TaskViewVo {

    @ApiModelProperty("标注任务id")
    private String id;
    @ApiModelProperty("标注任务名称")
    private String taskName;
    @ApiModelProperty("标注任务类型 1-算法模型应用  2-文档标注")
    private Integer taskType;
    @ApiModelProperty("标注任务子类型  1-文本分类  2-实体关系标注")
    private Integer subType;
    @ApiModelProperty("算法模型id")
    private String modelId;
    @ApiModelProperty("算法模型名称")
    private String modelName;
    @ApiModelProperty("一段文字有几个分类")
    private Integer classifyNum;
    @ApiModelProperty("文件夹路径")
    private String directoryPath;
    @ApiModelProperty("实体或分类")
    private String nodes;
    @ApiModelProperty("关系")
    private List<String> relations;
    @ApiModelProperty("属性")
    private List<String> properties;

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

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public Integer getClassifyNum() {
        return classifyNum;
    }

    public void setClassifyNum(Integer classifyNum) {
        this.classifyNum = classifyNum;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public List<String> getRelations() {
        return relations;
    }

    public void setRelations(List<String> relations) {
        this.relations = relations;
    }

    public List<String> getProperties() {
        return properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
