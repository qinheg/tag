package com.louddt.tag.entity.vo.task;

import io.swagger.annotations.ApiModelProperty;

public class TaskModifyBO {

    @ApiModelProperty(value = "标注任务id" ,required = false)
    private String id;
    @ApiModelProperty(value = "标注任务名称" ,required = true)
    private String taskName;
    @ApiModelProperty(value = "标注任务类型  1-发算模型应用  2-文档标注" ,required = true)
    private Integer taskType;
    @ApiModelProperty(value = "标注任务子分类  1-文本分类  2-实体关系分类" ,required = true)
    private Integer subType;
    @ApiModelProperty(value = "模型id，未关联模型不传值" ,required = false)
    private String modelId;
    @ApiModelProperty(value = "文件夹本地路径" ,required = true)
    private String directoryPath;
    @ApiModelProperty(value = "文件附件id,多个id以英文逗号,隔开",required = false)
    private String fileIds;
    @ApiModelProperty(value = "实体或分类，多个实体以英文逗号,隔开" ,required = false)
    private String nodes;
    @ApiModelProperty(value = "一段文字有几个分类",required = false)
    private Integer classifyNum;
    @ApiModelProperty(value = "关系，多组关系以英文分号;隔开" ,required = false)
    private String relations;
    @ApiModelProperty(value = "属性，多组属性以英文分号;隔开" ,required = false)
    private String properties;

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

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public String getRelations() {
        return relations;
    }

    public void setRelations(String relations) {
        this.relations = relations;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public Integer getClassifyNum() {
        return classifyNum;
    }

    public void setClassifyNum(Integer classifyNum) {
        this.classifyNum = classifyNum;
    }
}
