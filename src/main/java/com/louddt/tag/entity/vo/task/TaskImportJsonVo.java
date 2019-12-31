package com.louddt.tag.entity.vo.task;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

public class TaskImportJsonVo {

    @ApiModelProperty(value = "任务id",required = true)
    private String taskId;
    @ApiModelProperty(value = "文本序号",required = true)
    private Integer fileSort;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getFileSort() {
        return fileSort;
    }

    public void setFileSort(Integer fileSort) {
        this.fileSort = fileSort;
    }
}
