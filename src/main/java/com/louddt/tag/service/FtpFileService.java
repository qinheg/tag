package com.louddt.tag.service;

import com.louddt.tag.entity.dbo.SysFtpFile;

import java.util.List;

public interface FtpFileService {
    SysFtpFile selectFtpFileByFileId(String fileId);

    int insert(SysFtpFile ftpFile);

    int batchInsert(List<SysFtpFile> ftpFiles);
}
