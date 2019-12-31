package com.louddt.tag.utils;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class Query implements Serializable {

    private static final long serialVersionUID = -201908130959102123L;

    @ApiModelProperty(value = "页码,默认为1",required = false)
    private Integer pageIndex = 1;
    @ApiModelProperty(value = "每页显示条数，默认为10",required = false)
    private Integer pageSize = 10;
    @ApiModelProperty(value = "分页起始",required = false,hidden = true)
    private Integer pageStart;

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
}
