package com.louddt.tag.controller;

import com.louddt.tag.entity.vo.dataset.ColumnsQuery;
import com.louddt.tag.entity.vo.dataset.DatabaseQuery;
import com.louddt.tag.entity.vo.dataset.TableQuery;
import com.louddt.tag.service.DatasetService;
import com.louddt.tag.utils.ResponseWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = {},description = "数据库连接操作API")
@Slf4j
@RestController
@RequestMapping("dataset")
public class DatasetController extends BaseController{

    @Autowired
    private DatasetService datasetService;

    @ApiOperation(value = "数据库列表", notes = "获取数据库名列表")
    @RequestMapping(value = "databases", method = RequestMethod.POST)
    public ResponseWrapper<List<String>> queryDatabaseNames(
            @ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token,
            @ModelAttribute DatabaseQuery query){
        try{
            List<String> remoteDatabaseNames = datasetService.queryRemoteDatabaseNames(query);
            return success("远程数据库列表获取成功", remoteDatabaseNames);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取远程数据库列表出错",e);
            return error("获取远程数据库列表失败");
        }
    }

    @ApiOperation(value = "数据库中表名列表", notes = "数据表名列表")
    @RequestMapping(value = "tables", method = RequestMethod.POST)
    public ResponseWrapper<List<String>> queryTableNames(
            @ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token,
            @ModelAttribute TableQuery query){

        try{
            List<String> remoteTableNames = datasetService.queryRemoteTableNames(query);
            return success("远程数据表列表获取成功", remoteTableNames);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取远程数据库列表出错",e);
            return error("获取远程数据库列表失败");
        }
    }

    @ApiOperation(value = "列名列表", notes = "数据表列名列表")
    @RequestMapping(value = "columns", method = RequestMethod.POST)
    public ResponseWrapper<List<String>> queryColumnNames(
            @ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token,
            @ModelAttribute ColumnsQuery query){
        try{
            List<String> remoteColumnNames = datasetService.queryRemoteColumnNames(query);
            return success("列信息获取成功", remoteColumnNames);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取远程数据库列表出错",e);
            return error("获取远程数据库列表失败");
        }

    }

}
