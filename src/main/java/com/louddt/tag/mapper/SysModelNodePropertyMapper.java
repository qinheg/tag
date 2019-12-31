package com.louddt.tag.mapper;

import com.louddt.tag.entity.dbo.SysModelNodeProperty;
import com.louddt.tag.entity.vo.model.ModelNodePropertyVo;

import java.util.List;

public interface SysModelNodePropertyMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysModelNodeProperty record);

    int insertSelective(SysModelNodeProperty record);

    SysModelNodeProperty selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysModelNodeProperty record);

    int updateByPrimaryKey(SysModelNodeProperty record);

    int batchInsert(List<SysModelNodeProperty> list);

    List<ModelNodePropertyVo> queryModelNodePropertyByModelId(String modelId);
}