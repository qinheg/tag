package com.louddt.tag.controller;

import com.louddt.tag.config.FtpConfig;
import com.louddt.tag.entity.dbo.SysFtpFile;
import com.louddt.tag.service.FtpFileService;
import com.louddt.tag.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(tags = {},description = "文件上传接口")
@Slf4j
@RestController
@RequestMapping("upload")
public class UploadController extends BaseController{

    @Autowired
    private FtpConfig ftpConfig;
    @Autowired
    private FtpUtil ftpUtil;
    @Autowired
    private FtpFileService ftpFileService;

    @ApiOperation(value = "上传文件", notes = "支持zip、txt、xml、excel")
    @RequestMapping(value = "file",method = RequestMethod.POST)
    public ResponseWrapper<String> pploadFile(HttpServletRequest request,
           @ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token,
           @ApiParam("文件") @RequestParam("file") MultipartFile file) {
        String userId = tokens.get(token);
        //FTPClient ftpClient = ftpUtil.getFTPClient();
        boolean flag = false;
        InputStream in = null;
        try{
            in = file.getInputStream();

            String fileName = file.getOriginalFilename();
            String suffix = ResourceUtils.suffix(fileName);
            String alisName = UUID.uuId()+SymbolConstants.DOT+suffix;

            long fileSize = file.getSize();

            String path = "";
//            ftpClient.makeDirectory(ftpConfig.getPath());
//            String path = ftpConfig.getPath()+"/"+alisName;
//
//            flag = ftpClient.storeFile(path,in);
            String content = FileUploadUtil.file2String(in,fileName);
            flag = true;
            if(flag){
                SysFtpFile ftpFile = new SysFtpFile();
                ftpFile.setId(UUID.uuId());
                ftpFile.setPath(path);
                ftpFile.setRealName(fileName);
                ftpFile.setName(alisName);
                ftpFile.setFileType("1");
                ftpFile.setCreateUser(userId);
                ftpFile.setCreateTime(new Date());
                ftpFile.setSize(String.valueOf(fileSize));
                ftpFile.setExtType(suffix);
                ftpFile.setContents(content);
                ftpFileService.insert(ftpFile);

                return success("文件上传成功",ftpFile.getId());
            }else{
                return error("文件上传失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("文件上传失败",e);
            return error("文件上传失败");
        }finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "上传文件夹", notes = "上传文件夹")
    @RequestMapping(value = "directory",method = RequestMethod.POST)
    public ResponseWrapper<String> uploadDirectory(HttpServletRequest request,
              @ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token) {
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        List<MultipartFile> files = params.getFiles("fileFolder");
        //FTPClient ftpClient = ftpUtil.getFTPClient();
        String userId = tokens.get(token);
        boolean flag = false;
        try{
            //ftpClient.makeDirectory(ftpConfig.getPath());
            List<String> fildIds = new ArrayList<>();
            List<SysFtpFile> ftpFiles = new ArrayList<>();

            SysFtpFile directory = new SysFtpFile();
            directory.setId(UUID.uuId());
            directory.setPath(null);
            directory.setRealName(null);
            directory.setName(null);
            directory.setFileType("2");
            directory.setCreateUser(userId);
            directory.setCreateTime(new Date());
            directory.setSize(null);
            directory.setExtType(null);
            ftpFiles.add(directory);

            for (MultipartFile file : files) {
                InputStream in = file.getInputStream();
                String fileName = file.getOriginalFilename();
                String suffix = ResourceUtils.suffix(fileName);
                String alisName = UUID.uuId()+SymbolConstants.DOT+suffix;

                long fileSize = file.getSize();
                String path = "";
//                ftpClient.makeDirectory(ftpConfig.getPath());
//                String path = ftpConfig.getPath()+"/"+alisName;
//
//                flag = ftpClient.storeFile(path,in);
                String content = FileUploadUtil.file2String(in,fileName);
                flag = true;
                if(flag){
                    SysFtpFile ftpFile = new SysFtpFile();
                    ftpFile.setId(UUID.uuId());
                    ftpFile.setPath(path);
                    ftpFile.setRealName(fileName);
                    ftpFile.setName(alisName);
                    ftpFile.setFileType("1");
                    ftpFile.setCreateUser(userId);
                    ftpFile.setCreateTime(new Date());
                    ftpFile.setContents(content);
                    ftpFile.setDirectory(directory.getId());
                    ftpFile.setSize(String.valueOf(fileSize));
                    ftpFile.setExtType(suffix);
                    ftpFiles.add(ftpFile);
                    fildIds.add(ftpFile.getId());
                }
            }
            if(ftpFiles.size()>0){
                ftpFileService.batchInsert(ftpFiles);
            }
            String fileIdsStr = StringUtils.join(fildIds.toArray(),SymbolConstants.COMMA);
            return success("文件上传成功",fileIdsStr);
        }catch (Exception e){
            e.printStackTrace();
            log.error("上传文件夹出错",e);
            return error("上传文件夹失败");
        }
    }
}
