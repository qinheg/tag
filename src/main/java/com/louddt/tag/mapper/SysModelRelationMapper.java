package com.louddt.tag.mapper;

import com.louddt.tag.entity.dbo.SysModelRelation;

import java.util.List;

public interface SysModelRelationMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysModelRelation record);

    int insertSelective(SysModelRelation record);

    SysModelRelation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysModelRelation record);

    int updateByPrimaryKey(SysModelRelation record);

    int batchInsert(List<SysModelRelation> list);

    List<SysModelRelation> queryModelRelationByModelId(String modelId);
}