package com.louddt.tag.entity.dbo;

import com.louddt.tag.entity.vo.task.TaskDbMappingVo;

import java.util.Date;

public class SysTaskMapping {
    private String id;

    private String dbType;

    private String ip;

    private Integer port;

    private String username;

    private String password;

    private String databaseName;

    private String tableName;

    private String nodeName;

    private String colPropertyJson;

    private String taskId;

    private Date createTime;

    public SysTaskMapping(){}

    public SysTaskMapping(TaskDbMappingVo mapping) {
        this.dbType = mapping.getDbType();
        this.ip = mapping.getIp();
        this.port = mapping.getPort();
        this.username = mapping.getUsername();
        this.password = mapping.getPassword();
        this.databaseName = mapping.getDatabaseName();
        this.tableName = mapping.getTableName();
        this.nodeName = mapping.getNodeName();
        this.colPropertyJson = mapping.getColPropertyJson();
        this.taskId = mapping.getTaskId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
    }

    public String getColPropertyJson() {
        return colPropertyJson;
    }

    public void setColPropertyJson(String colPropertyJson) {
        this.colPropertyJson = colPropertyJson == null ? null : colPropertyJson.trim();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}