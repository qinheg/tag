package com.louddt.tag.controller;

import com.louddt.tag.config.FtpConfig;
import com.louddt.tag.entity.dbo.SysFtpFile;
import com.louddt.tag.entity.dbo.SysModelDataset;
import com.louddt.tag.entity.vo.model.ModelDatasetBO;
import com.louddt.tag.entity.vo.model.ModelDeteleBO;
import com.louddt.tag.entity.vo.model.ModelListQuery;
import com.louddt.tag.entity.vo.model.ModelListVo;
import com.louddt.tag.service.FtpFileService;
import com.louddt.tag.service.ModelService;
import com.louddt.tag.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

@Api(tags = {},description = "算法模型操作api")
@Slf4j
@RestController
@RequestMapping("model")
public class ModelController extends BaseController{

    @Autowired
    private ModelService modelService;
    @Autowired
    private FtpConfig ftpConfig;
    @Autowired
    private FtpUtil ftpUtil;
    @Autowired
    private FtpFileService ftpFileService;

    @ApiOperation(value = "导入模型",notes = "导入模型配置文件，类型支持xml")
    @RequestMapping(value = "importModel",method = RequestMethod.POST)
    public ResponseWrapper<String> importModelMethod(
            @ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token,
            @ApiParam(value = "模型配置文件") @RequestParam("file") MultipartFile file) {
        InputStream in = null;
        InputStream data = null;
        //FTPClient ftpClient = ftpUtil.getFTPClient();
        boolean flag = false;
        String userId = tokens.get(token);
        try{
            in = file.getInputStream();
            data = file.getInputStream();

            String fileName = file.getOriginalFilename();
            String suffix = ResourceUtils.suffix(fileName);
            long fileSize = file.getSize();
            String alisName = UUID.uuId()+SymbolConstants.DOT+suffix;
            String path = "";
//            ftpClient.makeDirectory(ftpConfig.getPath());
//            String path = ftpConfig.getPath()+"/"+alisName;
//
//            flag = ftpClient.storeFile(path,in);
            String contents = FileUploadUtil.file2String(in,fileName);
            flag = true;
            if(flag){
                modelService.importModelAndSaveInfo(data,path,fileName,alisName,fileSize,suffix,contents,userId);
            }
        }catch(Exception e){
            e.printStackTrace();
            log.error("导入模型配置出错",e);
            return error("导入模型配置失败");
        }finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return success("导入成功",null);
    }

    @ApiOperation(value = "导出模型",notes = "导出模型的配置文件")
    @RequestMapping(value = "exportModel",method = RequestMethod.GET)
    public void exportModelMethod(HttpServletRequest request, HttpServletResponse response,
                     @ApiParam(value = "模型id") @RequestParam("modelId") String modelId){

        String fileId = modelService.getModelFileIdByModelId(modelId);
        if(StringUtils.isEmpty(fileId)){
            return;
        }
        try{
            SysFtpFile file = ftpFileService.selectFtpFileByFileId(fileId);
            if(file != null){

//                String downloadPath = ftpConfig.getDownload();
//                File down = new File(downloadPath);
//                if (!down.exists()) {
//                    down.mkdirs();
//                }
//
//                String ftpFilePath = file.getPath();
//                int endIndex = ftpFilePath.lastIndexOf("/") + 1;
//                String realFileName = ftpFilePath.substring(endIndex);
//                String fileFullPath = downloadPath + File.separator +realFileName;
//                File tFile = new File(fileFullPath);
//                boolean isDownloadSuccess = true;
//                if(!tFile.exists()){
//                    FTPClient client = ftpUtil.getFTPClient();
//                    isDownloadSuccess = FtpUtil.downloadFtpFile(client,ftpFilePath, downloadPath, file.getRealName());
//                }
//                if(isDownloadSuccess){
//                    File f = new File(downloadPath + File.separator + realFileName);
//                    InputStream input = new FileInputStream(f);
//                    f.delete();
//                    FtpUtil.downloadFileUtil(input, file.getRealName(), request, response);
//                }
                String contents = file.getContents();
                InputStream input = new ByteArrayInputStream(contents.getBytes());
                FtpUtil.downloadFileUtil(input, file.getRealName(), request, response);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("导出模型失败",e);
        }
    }

    @ApiOperation(value = "算法模型列表",notes = "获取算法模型列表")
    @RequestMapping(value = "modelList",method = RequestMethod.POST)
    public ResponseWrapper<Page<ModelListVo>> queryModelListMethod(
            @ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token,
            @ModelAttribute ModelListQuery query){
        try{
            String userId = tokens.get(token);
            query.setUserId(userId);
            Page<ModelListVo> models = modelService.queryModelList(query);
            return success("获取成功",models);
        }catch (Exception e){
            e.printStackTrace();
            log.info("算法模型列表出错",e);
            return error("获取模型列表失败");
        }
    }

    @ApiOperation(value = "删除算法模型",notes = "删除算法模型,同时删除多个时，算法模型id以英文逗号隔开")
    @RequestMapping(value = "deleteModels",method = RequestMethod.POST)
    public ResponseWrapper<Object> deleteModelsMethod(
            @ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token,
            @ModelAttribute ModelDeteleBO delete){
        try{
            int num = modelService.deleteModels(delete);
            return success("删除成功",num);
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除算法模型失败",e);
            return error("删除算法模型失败");
        }
    }

    @ApiOperation(value = "算法模型关联数据库配置信息",notes = "可以查看算法模型关联的数据库的配置信息")
    @RequestMapping(value = "modelDatasetView",method = RequestMethod.POST)
    public ResponseWrapper<SysModelDataset> modelDatasetViewMethod(
            @ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token,
            @ApiParam("模型id") @RequestParam("modelId") String modelId){
        try{
            SysModelDataset dataset = modelService.queryModelDatasetView(modelId);
            return success("获取成功",dataset);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取失败",e);
            return error("获取失败");
        }
    }

    @ApiOperation(value = "保存或修改算法关联模型数据库配置信息",notes = "保存或修改算法模型关联数据库配置信息，不传数据配置id为保存，传数据配置id为修改")
    @RequestMapping(value = "saveOrUpdateDataset",method = RequestMethod.POST)
    public ResponseWrapper<String> saveOrUpdateModelDatesetMethod(
            @ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token,
            @ModelAttribute ModelDatasetBO dataset){
        try{
            String datasetId = modelService.saveOrUpdateModelDataset(dataset);
            return success("修改保存成功",datasetId);
        }catch (Exception e){
            e.printStackTrace();
            log.error("修改保存失败",e);
            return error("修改保存失败");
        }
    }

    @ApiOperation(value = "根据模型id获取实体、关系、属性",notes = "可以根据模型id获取模型配置的实体、关系、属性信息")
    @RequestMapping(value = "modelInfoView",method = RequestMethod.POST)
    public ResponseWrapper<Map<String,Object>> queryModelInfoViewMethod(
            @ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token,
            @ApiParam(value = "模型id") @RequestParam("modelId") String modelId){
        try{
            Map<String,Object> data = modelService.queryModelInfoView(modelId);
            return success("获取模型信息成功",data);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取模型信息失败",e);
            return error("获取模型信息失败");
        }
    }


}
