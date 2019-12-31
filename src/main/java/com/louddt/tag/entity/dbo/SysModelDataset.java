package com.louddt.tag.entity.dbo;

import com.louddt.tag.entity.vo.model.ModelDatasetBO;

import java.util.Date;

public class SysModelDataset {
    private String id;

    private String serverName;

    private String dbType;

    private String ip;

    private Integer port;

    private String username;

    private String password;

    private String databaseName;

    private String modelId;

    private Date createTime;

    public SysModelDataset(){}

    public SysModelDataset(ModelDatasetBO dataset) {
       this.id = dataset.getId();
       this.dbType = dataset.getDbType();
       this.ip = dataset.getIp();
       this.port = dataset.getPort();
       this.username = dataset.getUsername();
       this.password = dataset.getPassword();
       this.databaseName = dataset.getDatabaseName();
       this.modelId = dataset.getModelId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType == null ? null : dbType.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
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
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName == null ? null : databaseName.trim();
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId == null ? null : modelId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}