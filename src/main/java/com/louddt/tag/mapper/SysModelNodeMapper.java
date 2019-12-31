package com.louddt.tag.mapper;

import com.louddt.tag.entity.dbo.SysModelNode;

import java.util.List;

public interface SysModelNodeMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysModelNode record);

    int insertSelective(SysModelNode record);

    SysModelNode selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysModelNode record);

    int updateByPrimaryKey(SysModelNode record);

    int batchInsert(List<SysModelNode> list);

    String queryModelNodeName(String modelId);
}