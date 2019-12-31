package com.louddt.tag.entity.vo.task;

import io.swagger.annotations.ApiModelProperty;

public class TaskFileListVo {

    @ApiModelProperty("导出id")
    private String id;
    @ApiModelProperty("文件名称")
    private String fileName;
    @ApiModelProperty("文件状态code")
    private Integer status;
    @ApiModelProperty("文件状态名称")
    private String statusName;
    @ApiModelProperty("文件导出地址")
    private String exportUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getExportUrl() {
        return exportUrl;
    }

    public void setExportUrl(String exportUrl) {
        this.exportUrl = exportUrl;
    }
}
