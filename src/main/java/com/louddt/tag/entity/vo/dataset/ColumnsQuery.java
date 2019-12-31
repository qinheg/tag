package com.louddt.tag.entity.vo.dataset;

import io.swagger.annotations.ApiModelProperty;

public class ColumnsQuery extends TableQuery{

    @ApiModelProperty(value = "表名",required = true)
    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
