package com.louddt.tag.entity.vo.dataset;

import io.swagger.annotations.ApiModelProperty;

public class TableQuery extends DatabaseQuery{

    @ApiModelProperty(value = "数据库database名称",required = true)
    private String databaseName;

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}
