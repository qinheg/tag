package com.louddt.tag.entity.vo.task;

import io.swagger.annotations.ApiModelProperty;

public class TaskDbMappingModifyVo {

    @ApiModelProperty("数据库类型  mysql")
    private String dbType;
    @ApiModelProperty("数据库ip")
    private String ip;
    @ApiModelProperty("数据库端口")
    private Integer port;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("数据库名")
    private String databaseName;
    @ApiModelProperty("任务id")
    private String taskId;

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
