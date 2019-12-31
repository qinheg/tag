package com.louddt.tag.entity.vo.model;

import io.swagger.annotations.ApiModelProperty;

public class ModelListQuery{

    @ApiModelProperty(value = "页码",required = false)
    private Integer pageIndex;
    @ApiModelProperty(value = "每页显示条数",required = false)
    private Integer pageSize;
    @ApiModelProperty(value = "分页起始",required = false,hidden = true)
    private Integer pageStart;

    @ApiModelProperty(value = "模型类型   1-实体关系标注  2-文档分类",required = false)
    private Integer type;
    @ApiModelProperty(value = "用户id",hidden = true)
    private String userId;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageStart() {
        return pageStart;
    }

    public void setPageStart(Integer pageStart) {
        this.pageStart = pageStart;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
