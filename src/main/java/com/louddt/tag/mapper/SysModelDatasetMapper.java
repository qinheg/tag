package com.louddt.tag.mapper;

import com.louddt.tag.entity.dbo.SysModelDataset;

public interface SysModelDatasetMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysModelDataset record);

    int insertSelective(SysModelDataset record);

    SysModelDataset selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysModelDataset record);

    int updateByPrimaryKey(SysModelDataset record);

    SysModelDataset queryModelDatasetByModelId(String modelId);
}