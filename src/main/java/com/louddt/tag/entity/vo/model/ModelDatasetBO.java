package com.louddt.tag.entity.vo.model;

import io.swagger.annotations.ApiModelProperty;

public class ModelDatasetBO {

    @ApiModelProperty(value = "数据配置id",required = false)
    private String id;
    @ApiModelProperty(value = "数据集名称  mysql、oracle、sqlserver、pgsql",required = true)
    private String dbType;
    @ApiModelProperty(value = "服务器地址",required = true)
    private String ip;
    @ApiModelProperty(value = "端口号",required = true)
    private Integer port;
    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    @ApiModelProperty(value = "数据库名称",required = true)
    private String databaseName;
    @ApiModelProperty(value = "算法模型id",required = true)
    private String modelId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
}
