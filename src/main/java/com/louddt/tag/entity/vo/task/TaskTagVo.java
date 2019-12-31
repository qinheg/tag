package com.louddt.tag.entity.vo.task;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class TaskTagVo {

    @ApiModelProperty("文件总数")
    private Integer fileTotal;
    @ApiModelProperty("文件当前位置")
    private Integer fileSort;
    @ApiModelProperty("json字符串")
    private String dataJson;
    @ApiModelProperty("关系起始限制")
    private List<TaskRelationRuleVo> rules;

    public Integer getFileTotal() {
        return fileTotal;
    }

    public void setFileTotal(Integer fileTotal) {
        this.fileTotal = fileTotal;
    }

    public Integer getFileSort() {
        return fileSort;
    }

    public void setFileSort(Integer fileSort) {
        this.fileSort = fileSort;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }

    public List<TaskRelationRuleVo> getRules() {
        return rules;
    }

    public void setRules(List<TaskRelationRuleVo> rules) {
        this.rules = rules;
    }
}
