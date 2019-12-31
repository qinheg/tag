package com.louddt.tag.entity.vo.dataset;

import io.swagger.annotations.ApiModelProperty;

public class DatabaseQuery {

    @ApiModelProperty(value = "数据库类型：mysql、sqlserver、oracle" ,required = true)
    private String dbType;
    @ApiModelProperty(value = "服务器ip" ,required = true)
    private String ip;
    @ApiModelProperty(value = "服务器端口" ,required = true)
    private int port;
    @ApiModelProperty(value = "用户名" ,required = true)
    private String username;
    @ApiModelProperty(value = "密码" ,required = true)
    private String password;

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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
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
}
