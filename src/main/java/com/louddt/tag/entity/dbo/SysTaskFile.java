package com.louddt.tag.entity.dbo;

public class SysTaskFile {
    private String id;

    private String taskId;

    private String fileId;

    private Integer status;

    private Integer kgStatus;

    private String dataJson;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getKgStatus() {
        return kgStatus;
    }

    public void setKgStatus(Integer kgStatus) {
        this.kgStatus = kgStatus;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson == null ? null : dataJson.trim();
    }
}