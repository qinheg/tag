package com.louddt.tag.entity.vo.task;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class TaskDbMappingVo {

    @ApiModelProperty("数据库类型 ")
    private String dbType;
    @ApiModelProperty("服务器ip")
    private String ip;
    @ApiModelProperty("服务器端口")
    private Integer port;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("数据库")
    private String databaseName;
    @ApiModelProperty("表名")
    private String tableName;
    @ApiModelProperty("实体名")
    private String nodeName;
    @ApiModelProperty("已配置的表字段与属性映射字符串")
    private String colPropertyJson;
    @ApiModelProperty("标注任务id")
    private String taskId;
    @ApiModelProperty("任务实体列表")
    private List<TaskEntryVo> entries;
    @ApiModelProperty("当前实体或关系的属性列表")
    private List<String> properties;

    public TaskDbMappingVo(){}

    public TaskDbMappingVo(TaskDbMappingModifyVo modify) {
        this.dbType = modify.getDbType();
        this.ip = modify.getIp();
        this.port = modify.getPort();
        this.username = modify.getUsername();
        this.password = modify.getPassword();
        this.databaseName = modify.getDatabaseName();
        this.taskId = modify.getTaskId();
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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getColPropertyJson() {
        return colPropertyJson;
    }

    public void setColPropertyJson(String colPropertyJson) {
        this.colPropertyJson = colPropertyJson;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<TaskEntryVo> getEntries() {
        return entries;
    }

    public void setEntries(List<TaskEntryVo> entries) {
        this.entries = entries;
    }

    public List<String> getProperties() {
        return properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
    }
}
