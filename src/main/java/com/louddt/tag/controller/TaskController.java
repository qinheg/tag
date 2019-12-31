package com.louddt.tag.controller;

import com.louddt.tag.entity.vo.task.*;
import com.louddt.tag.exception.DBNotConfigureException;
import com.louddt.tag.service.TaskService;
import com.louddt.tag.utils.FileUploadUtil;
import com.louddt.tag.utils.Page;
import com.louddt.tag.utils.ResponseWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

@Api(tags = {},description = "标注任务操作API")
@Slf4j
@RestController
@RequestMapping("task")
public class TaskController extends BaseController{

    @Autowired
    private TaskService taskService;

    @ApiOperation(value = "新建或修改标注任务基本信息",notes = "新建或修改标注任务基本信息，不传任务id为新建，传任务id为修改")
    @RequestMapping(value = "saveOrUpdateTask",method = RequestMethod.POST)
    public ResponseWrapper<String> saveOrUpdateTaskMethod(
            @ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token,
            @ModelAttribute TaskModifyBO taskBo){
        String userId = tokens.get(token);
        try{
            String taskId = taskService.saveOrUpdateTask(taskBo,userId);
            return success("保存成功",taskId);
        }catch (DBNotConfigureException e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
            return error(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            log.error("新建或修改标注任务基本信息出错",e);
            return error("新建或修改标注任务基本信息失败");
        }
    }

    @ApiOperation(value = "删除标注任务",notes = "删除标注任务，同时会删除任务的相关内容")
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public ResponseWrapper<Object> deleteTaskMethod(@ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token,
            @ApiParam(value = "标注任务id") @RequestParam("taskId") String taskId){

        try{
            int result = taskService.deleteTask(taskId);
            return success("删除成功",result);
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除标注任务失败",e);
            return error("删除标注任务失败");
        }
    }

    @ApiOperation(value = "标注任务信息-不包含文本内容",notes = "标注任务信息数据回显")
    @RequestMapping(value = "taskView",method = RequestMethod.POST)
    public ResponseWrapper<TaskViewVo> taskInfoViewMethod(
            @ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token,
            @ApiParam("任务id") @RequestParam("taskId") String taskId){

        try{
            TaskViewVo data = taskService.queryTaskInfoView(taskId);
            return success("获取成功",data);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取标注任务信息失败",e);
            return error("获取标注人文信息失败");
        }
    }

    @ApiOperation(value = "标注任务列表",notes = "获取标注任务列表")
    @RequestMapping(value = "taskList" ,method = RequestMethod.POST)
    public ResponseWrapper<Page<TaskListVo>> taskListMethod(@ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token,
              @ModelAttribute TaskListQuery query){
        String userId = tokens.get(token);
        try{
            query.setUserId(userId);
            Page<TaskListVo> data = taskService.queryTaskList(query);
            return success("获取成功",data);
        }catch (Exception e){
            e.printStackTrace();
            log.error("标注任务列表出错",e);
            return error("标注任务列表失败");
        }
    }

    @ApiOperation(value = "获取标注任务数据映射",notes = "获取标注任务数据映射信息")
    @RequestMapping(value = "mapping",method = RequestMethod.POST)
    public ResponseWrapper<TaskDbMappingVo> taskDatasetMappingMethod(
            @ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token,
            @ModelAttribute TaskDatasetMappingQuery query){

        try{
            TaskDbMappingVo vo = taskService.queryTaskDBMapping(query);
            return success("获取成功",vo);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取任务数据映射出错",e);
            return error("获取任务数据映射失败");
        }
    }


    @ApiOperation(value = "标注任务数据映射保存",notes = "标注任务数据映射配置保存")
    @RequestMapping(value = "mappingSave",method = RequestMethod.POST)
    public ResponseWrapper<Object> taskDBMappingSave(
            @ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token,
            @ApiParam(value = "数据库配置信息") @ModelAttribute TaskDbMappingModifyVo modify){

        try{
            int i = taskService.saveTaskMappingInfo(modify);
            return success("保存成功",i);
        }catch (Exception e){
            e.printStackTrace();
            log.info("保存标注任务数据映射出错",e);
            return error("标准任务数据映射失败");
        }
    }

    @ApiOperation(value = "标注任务信息-包含文本内容及标注内容",notes = "标注任务信息，包含文本内容及已标注过的内容信息及文本列表。第一次加载默认加载第一个文本")
    @RequestMapping(value = "taskTagView",method = RequestMethod.POST)
    public ResponseWrapper<TaskTagVo> taskTagViewMethod(
            @ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token,
            @ModelAttribute TaskTagQuery query){

        try{
            TaskTagVo data = taskService.queryTaskTagView(query);
            return success("获取成功",data);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取标注任务信息失败",e);
            return error("获取标注人文信息失败");
        }
    }

    @ApiOperation(value = "保存标注内容",notes = "保存手动标注的内容")
    @RequestMapping(value = "saveTaskTag",method = RequestMethod.POST)
    public ResponseWrapper<Object> saveTaskTagMethod(
            @ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token,
            @ModelAttribute TaskTagSaveBO ttsb){

        try{
            int result = taskService.saveTaskTagData(ttsb);
            if(result == 0){
                return success("保存标注内容成功",result);
            }else{
                return error("标注信息同步到数据库失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("保存标注内容出错",e);
            return error("保存标注内容失败");
        }

    }

    @ApiOperation(value = "获取标注任务的实体或分类列表",notes = "获取标注任务的实体关系或分类列表")
    @RequestMapping(value = "nodeList",method = RequestMethod.POST)
    public ResponseWrapper<List<TaskEntry>> queryTaskNodeListMethod(
            @ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token,
            @ModelAttribute TaskNodeListQuery query){

        try{
            List<TaskEntry> enties = taskService.queryTaskEntryList(query);
            return success("获取成功",enties);
        }catch(Exception e){
            e.printStackTrace();
            log.error("获取标注任务实体或分类出错",e);
            return error("获取标注任务信息失败");
        }
    }

    @ApiOperation(value = "获取标注任务文件列表",notes = "获取标注任务文件列表，主要用于导出时，文件列表展示")
    @RequestMapping(value = "taskFileList",method = RequestMethod.POST)
    public ResponseWrapper<List<TaskFileListVo>> taskFileListMethod(HttpServletRequest request,
            @ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token,
            @ApiParam(value = "标注任务id") @RequestParam("taskId") String taskId){
        try{
            String url = request.getRequestURL().toString();
            String servlet = request.getServletPath();
            String path = url.replace(servlet,"");

            List<TaskFileListVo> list = taskService.queryTaskFileList(path,taskId);
            return success("获取成功",list);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取标注任务文件列表出错",e);
            return error("获取标注任务文件列表失败");
        }
    }


    @ApiOperation(value = "导出文档标注结果",notes = "导出文档标注结果,导出格式为json字符串的txt文本类型")
    @RequestMapping(value = "exportJson",method = RequestMethod.GET)
    public void exportJsonTxtMethod(HttpServletRequest request, HttpServletResponse response,
            @ApiParam(value = "导出id") @RequestParam("id") String id){

        TaskExportDataVo export = taskService.getTaskExportDataById(id);
        FileUploadUtil.writeToTxt(response,export.getContent(),export.getFileName());
    }

    @ApiOperation(value = "导入文档标注结果",notes = "导入文档已有的标注结果，会覆盖当前的标注")
    @RequestMapping(value = "importJson",method = RequestMethod.POST)
    public ResponseWrapper<TaskTagVo> importJsonToFileMethod(@ApiParam("用户登录标识") @RequestHeader(value = "token", required = true) String token,
                 @ApiParam(value = "标注json数据文件") @RequestParam("file") MultipartFile file,@ModelAttribute TaskImportJsonVo json){

        InputStream in = null;
        try{
            in = file.getInputStream();
            TaskTagVo tag = taskService.importJsonToFile(in,json.getTaskId(),json.getFileSort());
            String content = FileUploadUtil.file2String(in,file.getOriginalFilename());
            tag.setDataJson(content);
            return success("导入成功",tag);
        }catch (Exception e){
            e.printStackTrace();
            log.error("导入文档标注结果失败",e);
            return error("导入失败");
        }finally {
            try {
                if(in != null){
                    in.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
