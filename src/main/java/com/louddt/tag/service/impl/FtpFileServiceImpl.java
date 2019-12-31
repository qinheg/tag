package com.louddt.tag.service.impl;

import com.louddt.tag.entity.dbo.SysFtpFile;
import com.louddt.tag.mapper.SysFtpFileMapper;
import com.louddt.tag.service.FtpFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class FtpFileServiceImpl implements FtpFileService {

    @Autowired
    private SysFtpFileMapper sysFtpFileMapper;

    @Override
    public SysFtpFile selectFtpFileByFileId(String fileId) {
        return sysFtpFileMapper.selectByPrimaryKey(fileId);
    }

    @Override
    public int insert(SysFtpFile ftpFile) {
        return sysFtpFileMapper.insert(ftpFile);
    }

    @Override
    public int batchInsert(List<SysFtpFile> ftpFiles) {
        return sysFtpFileMapper.batchInsert(ftpFiles);
    }
}
