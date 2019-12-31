package com.louddt.tag.entity.vo.model;

import io.swagger.annotations.ApiModelProperty;

public class ModelListVo {

    @ApiModelProperty("模型id")
    private String id;
    @ApiModelProperty("模型名称")
    private String name;
    @ApiModelProperty("模型代码")
    private String code;
    @ApiModelProperty("模型类型")
    private Integer type;
    @ApiModelProperty("模型描述")
    private String descri;
    @ApiModelProperty("模型附件")
    private String fileId;
    @ApiModelProperty("模型类型名称")
    private String typeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
