package com.louddt.tag.mapper;

import com.louddt.tag.entity.dbo.SysFtpFile;

import java.util.List;

public interface SysFtpFileMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysFtpFile record);

    int insertSelective(SysFtpFile record);

    SysFtpFile selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysFtpFile record);

    int updateByPrimaryKeyWithBLOBs(SysFtpFile record);

    int updateByPrimaryKey(SysFtpFile record);

    int batchInsert(List<SysFtpFile> list);
}