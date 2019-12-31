package com.louddt.tag.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.louddt.tag.config.FtpConfig;
import com.louddt.tag.entity.dbo.*;
import com.louddt.tag.entity.vo.task.*;
import com.louddt.tag.exception.DBNotConfigureException;
import com.louddt.tag.mapper.*;
import com.louddt.tag.okhttp.OkHttp3Utils;
import com.louddt.tag.service.FtpFileService;
import com.louddt.tag.service.TaskService;
import com.louddt.tag.utils.*;
import com.louddt.tag.utils.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private SysTaskMapper sysTaskMapper;
    @Autowired
    private SysFtpFileMapper sysFtpFileMapper;
    @Autowired
    private SysTaskFileMapper sysTaskFileMapper;
    @Autowired
    private SysTaskNodeMapper sysTaskNodeMapper;
    @Autowired
    private SysTaskRelationMapper sysTaskRelationMapper;
    @Autowired
    private SysTaskNodePropertyMapper sysTaskNodePropertyMapper;
    @Autowired
    private SysTaskMappingMapper sysTaskMappingMapper;
    @Autowired
    private FtpConfig ftpConfig;
    @Autowired
    private FtpUtil ftpUtil;
    @Autowired
    private FtpFileService ftpFileService;
    @Autowired
    @Qualifier("okHttp3PostUtils")
    private OkHttp3Utils post;
    @Autowired
    private SysAlgoModelMapper sysAlgoModelMapper;

    @Override
    public String saveOrUpdateTask(TaskModifyBO taskBo,String userId) throws DBNotConfigureException,SQLException{
        String taskId = taskBo.getId();
        Date time = new Date();
        SysTask task = null;
        if(StringUtil.isEmptyOrNull(taskId)){
            //创建标注任务
            task = new SysTask(taskBo);
            taskId = UUID.uuId();
            task.setId(taskId);
            task.setCreateTime(time);
            task.setUpdateTime(time);
            task.setCreateUser(userId);
            task.setStatus(BizStatusDefine.TASK_STATUS_PEDING);
            sysTaskMapper.insert(task);

            //文件内容
            String fileIds = taskBo.getFileIds();
            if(StringUtil.isNotEmptyOrNull(fileIds)){
                List<String> files = Arrays.asList(fileIds.split(SymbolConstants.COMMA));

                //更新文件夹内容
                SysFtpFile file = sysFtpFileMapper.selectByPrimaryKey(files.get(0));
                SysFtpFile directory = sysFtpFileMapper.selectByPrimaryKey(file.getDirectory());
                if(directory != null){
                    directory.setRealName(task.getTaskName());
                    directory.setPath(task.getDirectoryPath());
                    sysFtpFileMapper.updateByPrimaryKey(directory);
                }

                List<SysTaskFile> tfs = new ArrayList<>();
                for (String fileId : files) {
                    SysTaskFile tf = new SysTaskFile();
                    tf.setId(UUID.uuId());
                    tf.setFileId(fileId);
                    tf.setTaskId(taskId);
                    tf.setKgStatus(BizStatusDefine.TASK_KG_STATUS_PEDING);
                    tf.setStatus(BizStatusDefine.TASK_STATUS_PEDING);
                    tfs.add(tf);
                }
                if(tfs.size() > 0){
                    sysTaskFileMapper.batchInsert(tfs);
                }
            }
        }else{
            task = sysTaskMapper.selectByPrimaryKey(taskId);
            task.setTaskName(taskBo.getTaskName());
            task.setDirectoryPath(taskBo.getDirectoryPath());
            task.setModelId(taskBo.getModelId());
            task.setClassifyNum(taskBo.getClassifyNum());
            task.setUpdateTime(time);
            sysTaskMapper.updateByPrimaryKey(task);

            //删除实体
            sysTaskNodeMapper.deleteTaskNodeByTaskId(taskId);
            if(BizStatusDefine.TASK_SUB_TYPE_CODE_RELATION == task.getSubType()){
                //删除关系
                sysTaskRelationMapper.deleteTaskRelationByTaskId(taskId);
                //删除属性
                sysTaskNodePropertyMapper.deleteTaskNodePropertyByTaskId(taskId);
            }
        }
        //模型信息
        SysAlgoModel model = null;
        if(task.getTaskType() == BizStatusDefine.TASK_TYPE_CODE_MODEL){
            model = sysAlgoModelMapper.selectByPrimaryKey(task.getModelId());
        }
        //入库表信息
        Map<String,String> table = new HashMap<>();
        //实体
        List<SysTaskNode> taskNodes = new ArrayList<SysTaskNode>();
        if(StringUtil.isNotEmptyOrNull(taskBo.getNodes())){
            List<String> nodes = Arrays.asList(taskBo.getNodes().split(SymbolConstants.COMMA));

            if(task.getTaskType() == BizStatusDefine.TASK_TYPE_CODE_MODEL && task.getSubType() == BizStatusDefine.TASK_SUB_TYPE_CODE_TXT){
                String tableFiled = BizStatusDefine.TASK_TXT_PROPERTIES_TYPE + SymbolConstants.COMMA + BizStatusDefine.TASK_TXT_PROPERTIES_CONTENT;
                table.put(BizStatusDefine.TASK_MODEL_TEXT_TABLE_NAME,tableFiled);
            }

            for (String nodeName : nodes) {
                if(StringUtil.isEmptyOrNull(nodeName)){
                    continue;
                }

                if(task.getTaskType() == BizStatusDefine.TASK_TYPE_CODE_MODEL && task.getSubType() == BizStatusDefine.TASK_SUB_TYPE_CODE_RELATION){
                    table.put(nodeName,nodeName);
                }

                SysTaskNode node = new SysTaskNode();
                node.setId(UUID.uuId());
                node.setNodeName(nodeName);
                node.setCreateTime(time);
                node.setTaskId(taskId);
                taskNodes.add(node);
            }
            if(taskNodes.size() > 0){
                sysTaskNodeMapper.batchInsert(taskNodes);
            }
        }

        List<SysTaskRelation> relations = new ArrayList<>();
        if(BizStatusDefine.TASK_SUB_TYPE_CODE_RELATION == task.getSubType()){
            //关系
            if(StringUtil.isNotEmptyOrNull(taskBo.getRelations())){
                List<String> relationStrs = Arrays.asList(taskBo.getRelations().split(SymbolConstants.SEMICOLON));
                for (String str : relationStrs) {
                    if(StringUtil.isEmptyOrNull(str)){
                        continue;
                    }
                    String[] arr = str.split(SymbolConstants.HLINE);
                    if(arr.length < 3){
                        continue;
                    }

                    if(task.getTaskType() == BizStatusDefine.TASK_TYPE_CODE_MODEL){
                        String tableFiled = arr[0] + SymbolConstants.COMMA + arr[2];
                        table.put(arr[1],tableFiled);
                    }


                    SysTaskRelation relation = new SysTaskRelation();
                    relation.setId(UUID.uuId());
                    relation.setStartNodeName(arr[0]);
                    relation.setName(arr[1]);
                    relation.setEndNodeName(arr[2]);
                    relation.setTaskId(taskId);
                    relation.setCreateTime(time);
                    relations.add(relation);
                }
                if(relations.size() > 0){
                    sysTaskRelationMapper.batchInsert(relations);
                }
            }

            //实体属性
            List<SysTaskNodeProperty> properties = new ArrayList<>();
            if(StringUtil.isNotEmptyOrNull(taskBo.getProperties())){
                List<String> propertiesStr = Arrays.asList(taskBo.getProperties().split(SymbolConstants.SEMICOLON));
                for (String str : propertiesStr) {
                    if(StringUtil.isEmptyOrNull(str)){
                        continue;
                    }

                    String[] arr = str.split(SymbolConstants.HLINE);
                    SysTaskNodeProperty property = new SysTaskNodeProperty();
                    property.setId(UUID.uuId());
                    property.setName(arr[0]);
                    property.setEntryName(arr[1]);
                    property.setTaskId(taskId);
                    if(arr.length == 2){
                        property.setValue(null);
                        property.setType("4");
                    }else{
                        property.setValue(arr[2]);
                        if(arr[2].contains(SymbolConstants.VB)){
                            property.setType("1");
                        }else if(arr[2].contains(SymbolConstants.COMMA)){
                            property.setType("2");
                        }else if(arr[2].contains(SymbolConstants.AND)){
                            property.setType("3");
                        }
                    }
                    properties.add(property);

                    if(task.getTaskType() == BizStatusDefine.TASK_TYPE_CODE_MODEL){
                        String tableFiled = table.get(arr[1]);
                        if(StringUtil.isNotEmptyOrNull(tableFiled)){
                            tableFiled = tableFiled + SymbolConstants.COMMA + arr[0];
                            table.put(arr[1],tableFiled);
                        }
                    }
                }

                if(properties.size() > 0){
                    sysTaskNodePropertyMapper.batchInsert(properties);
                }
            }
        }

        //初始化数据库及表
        if(task.getTaskType() == BizStatusDefine.TASK_TYPE_CODE_MODEL){
            TaskDbMappingVo dbmapping = queryTaskDBMapping(task);
            if(dbmapping == null){
                throw new DBNotConfigureException("模型未配置数据集信息,请先配置数据集");
            }

            //创建映射
            createTaskMapping(task.getId(),dbmapping,model.getName(),table);
            //初始化表
            createDBTable(dbmapping,model.getName(),table);
        }
        return taskId;
    }

    private void createTaskMapping(String taskId, TaskDbMappingVo dbmapping, String modelName, Map<String, String> tables) {
        sysTaskMappingMapper.deleteMappingByTaskId(taskId);
        List<SysTaskMapping> mappings = new ArrayList<>();
        Date time = new Date();
        for (String table : tables.keySet()) {
            StringBuilder colPropertyJson = new StringBuilder();
            String columns = tables.get(table);
            List<String> colList = Arrays.asList(columns.split(SymbolConstants.COMMA));
            boolean isUse = false;
            for (String col : colList) {
                if(isUse){
                    colPropertyJson.append(SymbolConstants.SEMICOLON);
                }
                if(!isUse){
                    isUse = true;
                }
                colPropertyJson.append(col).append(SymbolConstants.HLINE).append(col);
            }

            String tableName = modelName + SymbolConstants.UNDERLINE + table;
            SysTaskMapping mapping = new SysTaskMapping();
            mapping.setId(UUID.uuId());
            mapping.setDbType(dbmapping.getDbType());
            mapping.setIp(dbmapping.getIp());
            mapping.setPort(dbmapping.getPort());
            mapping.setUsername(dbmapping.getUsername());
            mapping.setPassword(dbmapping.getPassword());
            mapping.setDatabaseName(dbmapping.getDatabaseName());
            mapping.setTableName(tableName);
            mapping.setNodeName(table);
            mapping.setTaskId(taskId);
            mapping.setColPropertyJson(colPropertyJson.toString());
            mapping.setCreateTime(time);
            mappings.add(mapping);
        }

        if(mappings.size() > 0){
            sysTaskMappingMapper.batchInsert(mappings);
        }
    }

    private void createDBTable(TaskDbMappingVo dbmapping, String modelName, Map<String, String> tables) throws SQLException{
        Connection conn = DBUtil.getMySqlConnection(dbmapping.getIp(),dbmapping.getPort(),dbmapping.getDatabaseName(),dbmapping.getUsername(),dbmapping.getPassword());
        List<String> tableNames = DBUtil.queryTableNames(conn);
        for (String table : tables.keySet()) {
            String tableName = modelName + SymbolConstants.UNDERLINE + table;
            String cols = tables.get(table);
            tableName = tableName.replaceAll(SymbolConstants.LF,SymbolConstants.EMPTY);
            cols = cols.replaceAll(SymbolConstants.LF,SymbolConstants.EMPTY);
            List<String> colNews = Arrays.asList(cols.split(SymbolConstants.COMMA));
            if(tableNames.contains(tableName)){
                List<String> addCols = new ArrayList<>();
                List<String> columns = DBUtil.queryColumnNames(dbmapping.getDbType(),conn,tableName);
                for (String colNew : colNews) {
                    if(!columns.contains(colNew)){
                        addCols.add(colNew);
                    }
                }

                for (String addCol : addCols) {
                    StringBuilder executeSql = new StringBuilder("ALTER TABLE ");
                    if(BizStatusDefine.SPECIAL_COLUMNS.contains(addCol)){
                        executeSql.append(tableName).append(" ADD ")
                                  .append(addCol)
                                  .append(" varchar(")
                                  .append(BizStatusDefine.SPECIAL_COLUMN_LENGTH).append(") DEFAULT NULL ");
                    }else {
                        executeSql.append(tableName).append(" ADD ").append(addCol).append(" varchar(255) null");
                    }
                    log.info("扩充字段SQL：" + executeSql.toString());
                    PreparedStatement ps = conn.prepareStatement(executeSql.toString());
                    ps.executeUpdate();
                    ps.close();
                    ps = null;
                }
            }else {
               StringBuilder executeSQL = new StringBuilder("create table ");
               executeSQL.append(tableName).append("(");
                executeSQL.append(BizStatusDefine.TASK_MODEL_TABLE_PRIMARY_KEY_ID);
                for (String colNew : colNews) {
                    executeSQL.append(",");
                    if(BizStatusDefine.SPECIAL_COLUMNS.contains(colNew)){
                        executeSQL.append(colNew)
                                  .append(" varchar(")
                                  .append(BizStatusDefine.SPECIAL_COLUMN_LENGTH).append(") DEFAULT NULL ");
                    }else {
                        executeSQL.append(colNew).append(" varchar(255) DEFAULT NULL ");
                    }
                }
                executeSQL.append(",");
                executeSQL.append(BizStatusDefine.TASK_MODEL_TABLE_MD5_CODE);
                executeSQL.append(" varchar(255) DEFAULT NULL ");
                executeSQL.append(",");
                executeSQL.append("PRIMARY KEY (id)");
                executeSQL.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8");
                log.info("自动建表SQL：" + executeSQL.toString());
                PreparedStatement ps = conn.prepareStatement(executeSQL.toString());
                ps.executeUpdate();
                ps.close();
                ps = null;
            }
        }
    }



    private TaskDbMappingVo queryTaskDBMapping(SysTask task) {
        String taskId = task.getId();
        TaskDbMappingVo vo = null;
        if(task.getSubType() == BizStatusDefine.TASK_SUB_TYPE_CODE_TXT){
            vo = sysTaskMappingMapper.queryTaskDBClassify(taskId);
            if(vo == null){
                vo = sysTaskMapper.queryTaskDefaultDBMapping(taskId);
            }
        }else if(task.getSubType() == BizStatusDefine.TASK_SUB_TYPE_CODE_RELATION){
            vo = sysTaskMappingMapper.queryTaskDBMapping(taskId,null);
            if(vo == null){
                vo = sysTaskMapper.queryTaskDefaultDBMapping(taskId);
            }
        }
        return vo;
    }

    @Override
    public TaskViewVo queryTaskInfoView(String taskId) {
        TaskViewVo view = sysTaskMapper.queryTaskViewVo(taskId);
        if(view == null){
            return view;
        }

        String nodes = sysTaskNodeMapper.queryTaskNodes(taskId);
        view.setNodes(nodes);

        if(BizStatusDefine.TASK_SUB_TYPE_CODE_RELATION == view.getSubType()){
            List<SysTaskRelation> trs = sysTaskRelationMapper.queryTaskRelations(taskId);
            List<String> relations = new ArrayList<String>();
            if(trs != null && trs.size() > 0){
                for (SysTaskRelation tr : trs) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(tr.getStartNodeName())
                            .append(SymbolConstants.HLINE)
                            .append(tr.getName())
                            .append(SymbolConstants.HLINE)
                            .append(tr.getEndNodeName());
                    relations.add(sb.toString());
                }
            }
            view.setRelations(relations);

            List<TaskNodePropertyVo> nps = sysTaskNodePropertyMapper.queryTaskNodeProperties(taskId);
            List<String> properties = new ArrayList<>();
            for (TaskNodePropertyVo np : nps) {
                StringBuilder sb = new StringBuilder();
                sb.append(np.getName())
                        .append(SymbolConstants.HLINE)
                        .append(np.getEntryName());
                if(StringUtil.isNotEmptyOrNull(np.getValue())){
                    sb.append(SymbolConstants.HLINE)
                      .append(np.getValue());
                }
                properties.add(sb.toString());
            }
            view.setProperties(properties);
        }
        return view;
    }

    @Override
    public Page<TaskListVo> queryTaskList(TaskListQuery query) {

        Integer pageIndex = query.getPageIndex();
        Integer pageSize = query.getPageSize();
        Integer pageStart = (pageIndex - 1) * pageSize;
        query.setPageStart(pageStart);
        int total = sysTaskMapper.queryTaskListTotal(query);
        List<TaskListVo> list = new ArrayList<>();
        if(!NumberUtils.numberBlank(total)){
            list = sysTaskMapper.queryTaskList(query);
        }
        if(list != null && list.size() > 0){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (TaskListVo vo : list) {
                if(BizStatusDefine.TASK_TYPE_CODE_MODEL == vo.getTaskType()){
                    vo.setTaskTypeName(BizStatusDefine.TASK_TYPE_NAME_MODEL);
                }else if(BizStatusDefine.TASK_TYPE_CODE_MANUAL == vo.getTaskType()){
                    vo.setTaskTypeName(BizStatusDefine.TASK_TYPE_NAME_MANUAL);
                }
                if(BizStatusDefine.TASK_SUB_TYPE_CODE_TXT == vo.getSubType()){
                    vo.setSubTypeName(BizStatusDefine.TASK_SUB_TYPE_NAME_TXT);
                }else if(BizStatusDefine.TASK_SUB_TYPE_CODE_RELATION == vo.getSubType()){
                    vo.setSubTypeName(BizStatusDefine.TASK_SUB_TYPE_NAME_RELATION);
                }

                vo.setUpdateTimeStr(format.format(vo.getUpdateTime()));
            }
        }
        Page<TaskListVo> datas = Page.page(total,list,pageIndex,pageSize);
        return datas;
    }

    @Override
    public TaskDbMappingVo queryTaskDBMapping(TaskDatasetMappingQuery query) {

        String taskId = query.getTaskId();
        String nodeName = query.getNode();
        TaskDbMappingVo vo = null;

        SysTask task = sysTaskMapper.selectByPrimaryKey(taskId);
        if(task.getTaskType() == BizStatusDefine.TASK_TYPE_CODE_MANUAL){
            return null;
        }

        if(task.getSubType() == BizStatusDefine.TASK_SUB_TYPE_CODE_TXT){
            vo = sysTaskMappingMapper.queryTaskDBClassify(taskId);
            if(vo == null){
                vo = sysTaskMapper.queryTaskDefaultDBMapping(taskId);
            }
            if(vo == null){
                vo = new TaskDbMappingVo();
            }
            List<String> properties = new ArrayList<>();
            properties.add(BizStatusDefine.TASK_TXT_PROPERTIES_TYPE);
            properties.add(BizStatusDefine.TASK_TXT_PROPERTIES_CONTENT);
            vo.setProperties(properties);
        }else if(task.getSubType() == BizStatusDefine.TASK_SUB_TYPE_CODE_RELATION){
            vo = sysTaskMappingMapper.queryTaskDBMapping(query.getTaskId(),query.getNode());
            if(vo == null){
                vo = sysTaskMapper.queryTaskDefaultDBMapping(taskId);
            }
            if(vo == null){
                vo = new TaskDbMappingVo();
            }
            if(StringUtil.isNotEmptyOrNull(nodeName)){
                List<String> properties = sysTaskNodePropertyMapper.queryTaskEntryProperties(query);
                if(properties == null){
                    properties = new ArrayList<>();
                }
                if(BizStatusDefine.TASK_ENTRY_TYPE_NODE == query.getType()){
                    SysTaskNode node = sysTaskNodeMapper.queryTaskNodeByQuery(query);
                    if(node != null){
                        properties.add(node.getNodeName());
                    }
                }else if(BizStatusDefine.TASK_ENTRY_TYPE_RELATION == query.getType()){
                    SysTaskRelation relation = sysTaskRelationMapper.queryTaskRelationByQuery(query);
                    if(relation != null){
                        properties.add(relation.getStartNodeName());
                        properties.add(relation.getEndNodeName());
                    }
                }
                vo.setProperties(properties);
            }
            List<TaskEntryVo> enties = new ArrayList<>();
            List<TaskEntryVo> nodes = sysTaskNodeMapper.queryNodeEntries(taskId);
            enties.addAll(nodes);

            List<TaskEntryVo> relations = sysTaskRelationMapper.queryRelationEntries(taskId);
            enties.addAll(relations);
            vo.setEntries(enties);
        }
        return vo;
    }

    @Override
    public int saveTaskMappingInfo(TaskDbMappingModifyVo modify) throws SQLException{
        String taskId = modify.getTaskId();
        SysTask task = sysTaskMapper.selectByPrimaryKey(taskId);

        SysAlgoModel model = sysAlgoModelMapper.selectByPrimaryKey(task.getModelId());
        TaskDbMappingVo dbmapping = new TaskDbMappingVo(modify);
        //入库表信息
        Map<String,String> table = new HashMap<>();

        if(task.getSubType() == BizStatusDefine.TASK_SUB_TYPE_CODE_TXT){
            String tableFiled = BizStatusDefine.TASK_TXT_PROPERTIES_TYPE + SymbolConstants.COMMA + BizStatusDefine.TASK_TXT_PROPERTIES_CONTENT;
            table.put(BizStatusDefine.TASK_MODEL_TEXT_TABLE_NAME,tableFiled);
        }else{
            String node = sysTaskNodeMapper.queryTaskNodes(taskId);
            List<String> nodes = Arrays.asList(node.split(SymbolConstants.COMMA));
            for (String nodeName : nodes) {
                table.put(nodeName,nodeName);
            }
        }

        if(task.getSubType() == BizStatusDefine.TASK_SUB_TYPE_CODE_RELATION){
            List<SysTaskRelation> trs = sysTaskRelationMapper.queryTaskRelations(taskId);
            if(trs != null && trs.size() > 0){
                for (SysTaskRelation tr : trs) {
                    String tableFiled = tr.getStartNodeName() + SymbolConstants.COMMA + tr.getEndNodeName();
                    table.put(tr.getName(),tableFiled);
                }
            }

            List<TaskNodePropertyVo> nps = sysTaskNodePropertyMapper.queryTaskNodeProperties(taskId);
            for (TaskNodePropertyVo np : nps) {
                String tableFiled = table.get(np.getEntryName());
                if(StringUtil.isNotEmptyOrNull(tableFiled)){
                    tableFiled = tableFiled + SymbolConstants.COMMA + np.getName();
                    table.put(np.getEntryName(),tableFiled);
                }
            }
        }
        //创建映射
        createTaskMapping(modify.getTaskId(),dbmapping,model.getName(),table);
        //初始化表
        createDBTable(dbmapping,model.getName(),table);

        return 0;
    }

    @Override
    public TaskTagVo queryTaskTagView(TaskTagQuery query) throws Exception {
        String taskId = query.getTaskId();
        Integer fileSort = query.getFileSort();

        TaskTagVo vo = new TaskTagVo();
        vo.setFileSort(fileSort);

        SysTask task = sysTaskMapper.selectByPrimaryKey(taskId);
        if(task == null){
            return null;
        }

        if(task.getSubType() == BizStatusDefine.TASK_SUB_TYPE_CODE_RELATION){
            List<TaskRelationRuleVo> rules = sysTaskRelationMapper.queryTaskRelationRules(taskId);
            vo.setRules(rules);
        }

        List<String> fileIds = sysTaskFileMapper.queryTaskFileIds(taskId);
        vo.setFileTotal(fileIds.size());

        String fileId = fileIds.get(fileSort - 1);
        SysTaskFile taskFile = sysTaskFileMapper.selectByTaskIdFileId(taskId,fileId);
        String dataJson = taskFile.getDataJson();
        if(StringUtil.isEmptyOrNull(dataJson)){
            TaskDataJson json = new TaskDataJson();
            String content = null;
            SysFtpFile file = sysFtpFileMapper.selectByPrimaryKey(fileId);
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
//                    content = FileUploadUtil.file2String(input,file.getRealName());
//                    json.setContent(content);
//                    f.delete();
//                }

                InputStream input = new ByteArrayInputStream(file.getContents().getBytes());
                content = FileUploadUtil.file2String(input,file.getRealName());
                json.setContent(content);
            }

            //如果是算法模型应用，调用nlp算法抽取实体
            if(task.getTaskType() == BizStatusDefine.TASK_TYPE_CODE_MODEL){
                SysAlgoModel model = sysAlgoModelMapper.selectByPrimaryKey(task.getModelId());
                JSONObject jo = new JSONObject();
                jo.put("content",content);
                log.info("算法调用请求路径：" + model.getUrl());
                long start = System.currentTimeMillis();
                dataJson = post.request(model.getUrl(),jo.toString(), OkHttp3Utils.JSON);
                log.info("算法请求成功：用时:" + (System.currentTimeMillis() - start)/1000 + "s");
            }
            if(StringUtil.isEmptyOrNull(dataJson)){
                dataJson = JSON.toJSONString(json);
            }
            taskFile.setDataJson(dataJson);
            sysTaskFileMapper.updateByPrimaryKeySelective(taskFile);
        }
        vo.setDataJson(dataJson);
        return vo;
    }

    @Override
    public int saveTaskTagData(TaskTagSaveBO ttsb) throws SQLException {

        SysTask task = sysTaskMapper.selectByPrimaryKey(ttsb.getTaskId());

        List<String> fileIds = sysTaskFileMapper.queryTaskFileIds(ttsb.getTaskId());
        String fileId = fileIds.get(ttsb.getFileSort() - 1);

        SysTaskFile taskFile = sysTaskFileMapper.selectByTaskIdFileId(ttsb.getTaskId(),fileId);
        String jsonData = taskFile.getDataJson();
        if(StringUtil.isNotEmptyOrNull(jsonData)){
            TaskDataJson jsonOld = JSONObject.parseObject(jsonData,TaskDataJson.class);
            TaskDataJson jsonNew = JSONObject.parseObject(ttsb.getDataJson(),TaskDataJson.class);
            jsonNew.setContent(jsonOld.getContent());
            jsonData = JSON.toJSONString(jsonNew);
        }else {
            jsonData = ttsb.getDataJson();
        }

        taskFile.setDataJson(jsonData);
        if(taskFile.getKgStatus() == BizStatusDefine.TASK_KG_STATUS_DOING){
            taskFile.setKgStatus(BizStatusDefine.TASK_KG_STATUS_REDONE);
        }
        taskFile.setStatus(BizStatusDefine.TASK_STATUS_FINISH);
        sysTaskFileMapper.updateByPrimaryKeySelective(taskFile);

        List<String> state = sysTaskFileMapper.queryNotFinishFile(ttsb.getTaskId());
        if(state == null || state.size() == 0){
            task.setStatus(BizStatusDefine.TASK_STATUS_FINISH);
        }else {
            if(state.size() == 1 && state.contains(taskFile.getId())){
                task.setStatus(BizStatusDefine.TASK_STATUS_FINISH);
            }else {
                task.setStatus(BizStatusDefine.TASK_STATUS_DOING);
            }
        }
        sysTaskMapper.updateByPrimaryKey(task);

        if(task.getTaskType() == BizStatusDefine.TASK_TYPE_CODE_MODEL){
            Connection conn = null;
            PreparedStatement ps = null;
            try{
                //算法模型应用数据需要入库
                TaskDataJson json = JSONObject.parseObject(jsonData,TaskDataJson.class);
                TaskDataJsonAnnotation annotation = json.getAnnotation();
                if(annotation != null){
                    TaskDbMappingVo mapping = null;
                    if(task.getSubType() == BizStatusDefine.TASK_SUB_TYPE_CODE_TXT){
                        //文本标注
                        List<TaskDataJsonEntity> enties = annotation.getEntity();
                        List<Map<String,Object>> sos = new ArrayList<>();
                        for (TaskDataJsonEntity ab : enties) {
                            Map<String,Object> so = new HashMap<>();
                            so.put(BizStatusDefine.TASK_TXT_PROPERTIES_TYPE,ab.getName());
                            so.put(BizStatusDefine.TASK_TXT_PROPERTIES_CONTENT,ab.getValue());
                            sos.add(so);
                        }
                        mapping = sysTaskMappingMapper.queryTaskDBClassify(task.getId());
                        if(mapping == null){
                            return 0;
                        }
                        if(conn == null){
                            conn = DBUtil.getMySqlConnection(mapping.getIp(),mapping.getPort(),
                                    mapping.getDatabaseName(),mapping.getUsername(),mapping.getPassword());

                            conn.setAutoCommit(false);
                        }

                        //拼接SQL
                        String cols = mapping.getColPropertyJson();
                        List<String> col =Arrays.asList(cols.split(SymbolConstants.SEMICOLON));
                        List<String> sqlCols = new ArrayList<>();
                        List<String> attrs = new ArrayList<>();
                        for (String s : col) {
                            String[] arr = s.split(SymbolConstants.HLINE);
                            sqlCols.add(arr[1]);
                            attrs.add(arr[0]);
                        }
                        String executeSQL = DBUtil.executeSQL(sqlCols,mapping.getTableName());
                        log.info("-----------批量插入SQL:" + executeSQL);
                        ps = conn.prepareStatement(executeSQL);

                        for(int i = 0 ;i < sos.size() ; i++){
                            Map<String,Object> so = sos.get(i);
                            for(int j = 0;j < attrs.size();j++){
                                ps.setObject(j+1,so.get(attrs.get(j)));
                            }
                            ps.addBatch();

                            if(i > 0 && i%500 == 0){
                                ps.executeBatch();
                            }
                        }
                        ps.executeBatch();
                        ps.close();

                        conn.commit();
                        conn.setAutoCommit(true);
                        conn.close();
                    }else if(task.getSubType() == BizStatusDefine.TASK_SUB_TYPE_CODE_RELATION){
                        //实体
                        List<TaskDataJsonEntity> enties = annotation.getEntity();
                        //需要对实体归类
                        Map<String,List<TaskDataJsonEntity>> map = new HashMap<>();
                        Map<Integer,String> relationNodeValue = new HashMap<>();
                        Map<Integer,String> relationNode = new HashMap<>();
                        for (TaskDataJsonEntity enty : enties) {
                            String nodeName = enty.getName();
                            List<TaskDataJsonEntity> entry = map.get(nodeName);
                            if(entry == null){
                                entry = new ArrayList<>();
                            }
                            entry.add(enty);
                            map.put(nodeName,entry);

                            relationNode.put(enty.getId(),enty.getName());
                            relationNodeValue.put(enty.getId(),enty.getValue());
                        }
                        for (String nodeName : map.keySet()) {
                            List<TaskDataJsonEntity> entry = map.get(nodeName);
                            mapping = sysTaskMappingMapper.queryTaskDBMapping(task.getId(),nodeName);
                            if(mapping == null){
                                continue;
                            }
                            if(conn == null){
                                conn = DBUtil.getMySqlConnection(mapping.getIp(),mapping.getPort(),
                                        mapping.getDatabaseName(),mapping.getUsername(),mapping.getPassword());

                                conn.setAutoCommit(false);
                            }
                            String executeSQL = null;
                            List<String> mdCodes = new ArrayList<>();
                            for (TaskDataJsonEntity enty : entry) {
                                String nodeValue = enty.getValue();
                                List<TaskDataJsonAttribute>  abs = enty.getAttributes();
                                Map<String,Object> so = new HashMap<>();
                                so.put(nodeName,nodeValue);
                                if(abs!= null && abs.size() > 0){
                                    for (TaskDataJsonAttribute ab : abs) {
                                        so.put(ab.getName(),ab.getValue());
                                    }
                                }

                                StringBuilder mdCodeSb = new StringBuilder();
                                boolean isUse = false;
                                for (String s : so.keySet()) {
                                    if(isUse){
                                        mdCodeSb.append(SymbolConstants.AND);
                                    }
                                    isUse = true;
                                    Object val = so.get(s);
                                    mdCodeSb.append(s).append(SymbolConstants.EQUAL).append(val);
                                }
                                String mdCode = MD5Util.getMD5(mdCodeSb.toString());
                                mdCodes.add(mdCode);
                                so.put(BizStatusDefine.TASK_MODEL_TABLE_MD5_CODE,mdCode);

                                List<String> attrs = new ArrayList<>();
                                if(StringUtil.isEmptyOrNull(executeSQL)){
                                    //拼接SQL
                                    String cols = mapping.getColPropertyJson();
                                    List<String> col =Arrays.asList(cols.split(SymbolConstants.SEMICOLON));
                                    List<String> sqlCols = new ArrayList<>();
                                    for (String s : col) {
                                        String[] arr = s.split(SymbolConstants.HLINE);
                                        sqlCols.add(arr[1]);
                                        attrs.add(arr[0]);
                                    }
                                    sqlCols.add(BizStatusDefine.TASK_MODEL_TABLE_MD5_CODE);
                                    attrs.add(BizStatusDefine.TASK_MODEL_TABLE_MD5_CODE);
                                    executeSQL = DBUtil.executeSQL(sqlCols,mapping.getTableName());
                                    log.info("-----------批量插入SQL:" + executeSQL);
                                    ps = conn.prepareStatement(executeSQL);
                                }
                                for(int i = 0 ;i < attrs.size() ; i++){
                                    ps.setObject(i+1,so.get(attrs.get(i)));
                                }
                                ps.addBatch();
                            }

                            if(mdCodes.size() > 0){
                                String condition = StringUtils.join(mdCodes,SymbolConstants.COMMA);
                                condition = SymbolConstants.SINGLE_QUOTES + condition;
                                condition.replaceAll(SymbolConstants.COMMA,"','");
                                condition += SymbolConstants.SINGLE_QUOTES;
                                StringBuilder deleteSQL= new StringBuilder("delete from ");
                                deleteSQL.append(mapping.getTableName())
                                        .append(" where ")
                                        .append(BizStatusDefine.TASK_MODEL_TABLE_MD5_CODE)
                                        .append(" in (")
                                        .append(condition)
                                        .append(")");
                                log.info("实体数据去重SQL：" + deleteSQL.toString());
                                PreparedStatement stmt = conn.prepareStatement(deleteSQL.toString());
                                stmt.executeUpdate();

                                stmt.close();
                                stmt = null;
                            }

                            ps.executeBatch();
                            ps.close();

                            conn.commit();
                            conn.setAutoCommit(true);
                            conn.close();
                            conn = null;
                            ps = null;
                        }

                        //关系
                        List<TaskDataJsonRelation> relationLis = annotation.getRelation();
                        Map<String,List<TaskDataJsonRelation>> rmap = new HashMap<>();
                        for (TaskDataJsonRelation enty : relationLis) {
                            String nodeName = enty.getName();
                            List<TaskDataJsonRelation> entry = rmap.get(nodeName);
                            if(entry == null){
                                entry = new ArrayList<>();
                            }
                            entry.add(enty);
                            rmap.put(nodeName,entry);
                        }
                        for (String nodeName : rmap.keySet()) {
                            List<TaskDataJsonRelation> entry = rmap.get(nodeName);
                            mapping = sysTaskMappingMapper.queryTaskDBMapping(task.getId(),nodeName);
                            if(mapping == null){
                                continue;
                            }
                            if(conn == null){
                                conn = DBUtil.getMySqlConnection(mapping.getIp(),mapping.getPort(),
                                        mapping.getDatabaseName(),mapping.getUsername(),mapping.getPassword());

                                conn.setAutoCommit(false);
                            }
                            String executeSQL = null;
                            List<String> mdCodes = new ArrayList<>();
                            for (TaskDataJsonRelation enty : entry) {

                                String startNode = relationNode.get(enty.getFrom());
                                String endNode = relationNode.get(enty.getTo());
                                String startNodeValue = relationNodeValue.get(enty.getFrom());
                                String endNodeValue = relationNodeValue.get(enty.getTo());

                                //获取字段+数据
                                List<TaskDataJsonAttribute>  abs = enty.getAttributes();
                                Map<String,Object> so = new HashMap<>();
                                so.put(startNode,startNodeValue);
                                so.put(endNode,endNodeValue);
                                if(abs != null && abs.size() > 0){
                                    for (TaskDataJsonAttribute ab : abs) {
                                        so.put(ab.getName(),ab.getValue());
                                    }
                                }

                                StringBuilder mdCodeSb = new StringBuilder();
                                boolean isUse = false;
                                for (String s : so.keySet()) {
                                    if(isUse){
                                        mdCodeSb.append(SymbolConstants.AND);
                                    }
                                    isUse = true;
                                    Object val = so.get(s);
                                    mdCodeSb.append(s).append(SymbolConstants.EQUAL).append(val);
                                }
                                String mdCode = MD5Util.getMD5(mdCodeSb.toString());
                                mdCodes.add(mdCode);
                                so.put(BizStatusDefine.TASK_MODEL_TABLE_MD5_CODE,mdCode);

                                List<String> attrs = new ArrayList<>();
                                if(StringUtil.isEmptyOrNull(executeSQL)){
                                    //拼接SQL
                                    String cols = mapping.getColPropertyJson();
                                    List<String> col =Arrays.asList(cols.split(SymbolConstants.SEMICOLON));
                                    List<String> sqlCols = new ArrayList<>();
                                    for (String s : col) {
                                        String[] arr = s.split(SymbolConstants.HLINE);
                                        sqlCols.add(arr[1]);
                                        attrs.add(arr[0]);
                                    }
                                    sqlCols.add(BizStatusDefine.TASK_MODEL_TABLE_MD5_CODE);
                                    attrs.add(BizStatusDefine.TASK_MODEL_TABLE_MD5_CODE);
                                    executeSQL = DBUtil.executeSQL(sqlCols,mapping.getTableName());
                                    log.info("-----------批量插入SQL:" + executeSQL);
                                    ps = conn.prepareStatement(executeSQL);
                                }
                                for(int i = 0 ;i < attrs.size() ; i++){
                                    ps.setObject(i+1,so.get(attrs.get(i)));
                                }
                                ps.addBatch();
                            }

                            if(mdCodes.size() > 0){
                                String condition = StringUtils.join(mdCodes,SymbolConstants.COMMA);
                                condition = SymbolConstants.SINGLE_QUOTES + condition;
                                condition.replaceAll(SymbolConstants.COMMA,"','");
                                condition += SymbolConstants.SINGLE_QUOTES;
                                StringBuilder deleteSQL= new StringBuilder("delete from ");
                                deleteSQL.append(mapping.getTableName())
                                        .append(" where ")
                                        .append(BizStatusDefine.TASK_MODEL_TABLE_MD5_CODE)
                                        .append(" in (")
                                        .append(condition)
                                        .append(")");
                                log.info("关系数据去重SQL：" + deleteSQL.toString());
                                PreparedStatement stmt = conn.prepareStatement(deleteSQL.toString());
                                stmt.executeUpdate();

                                stmt.close();
                                stmt = null;
                            }

                            ps.executeBatch();
                            ps.close();

                            conn.commit();
                            conn.setAutoCommit(true);
                            conn.close();
                            conn = null;
                            ps = null;
                        }
                    }

                }
            }catch (Exception e){
                e.printStackTrace();
                log.info("保存标注数据到数据库失败");
                return -1;
            }finally {
                if(ps != null){
                    ps.close();
                }
                if(conn != null){
                    conn.close();
                }
            }
        }
        return 0;
    }

    @Override
    public List<TaskEntry> queryTaskEntryList(TaskNodeListQuery query) {
        String taskId= query.getTaskId();
        SysTask task = sysTaskMapper.selectByPrimaryKey(taskId);
        List<TaskEntry> entries = new ArrayList<>();
        if(task.getSubType() == BizStatusDefine.TASK_SUB_TYPE_CODE_TXT){
            entries = sysTaskNodeMapper.queryTxtNodeEntries(taskId);
        }else if(task.getSubType() == BizStatusDefine.TASK_SUB_TYPE_CODE_RELATION){
            Integer type = query.getType();
            if(type == BizStatusDefine.TASK_ENTRY_TYPE_NODE){
                entries = sysTaskNodeMapper.queryModeNodeEntries(taskId);
            }else {
                entries = sysTaskRelationMapper.queryModeNodeEntries(taskId);
            }

        }
        return entries;
    }

    @Override
    public TaskExportDataVo getTaskExportDataById(String id) {
        return sysTaskFileMapper.getTaskExportDataById(id);
    }

    @Override
    public List<TaskFileListVo> queryTaskFileList(String path,String taskId) {
        List<TaskFileListVo> list = sysTaskFileMapper.queryTaskFileList(taskId);
        if(list != null && list.size() > 0){
            path += BizStatusDefine.TASK_JSON_DATA_EXPORT_PATH;
            for (TaskFileListVo taskFileListVo : list) {
                taskFileListVo.setExportUrl(path + taskFileListVo.getId());
                if(taskFileListVo.getStatus() == BizStatusDefine.TASK_STATUS_PEDING){
                    taskFileListVo.setStatusName(BizStatusDefine.TASK_STATUS_NAME_PEDING);
                }else if(taskFileListVo.getStatus() == BizStatusDefine.TASK_STATUS_DOING){
                    taskFileListVo.setStatusName(BizStatusDefine.TASK_STATUS_NAME_DOING);
                }else if(taskFileListVo.getStatus() == BizStatusDefine.TASK_STATUS_FINISH){
                    taskFileListVo.setStatusName(BizStatusDefine.TASK_STATUS_NAME_FINISH);
                }
            }
        }
        return list;
    }

    @Override
    public TaskTagVo importJsonToFile(InputStream in, String taskId, Integer fileSort) {
        TaskTagVo vo = new TaskTagVo();
        vo.setFileSort(fileSort);

        SysTask task = sysTaskMapper.selectByPrimaryKey(taskId);
        if(task == null){
            return null;
        }

        if(task.getSubType() == BizStatusDefine.TASK_SUB_TYPE_CODE_RELATION){
            List<TaskRelationRuleVo> rules = sysTaskRelationMapper.queryTaskRelationRules(taskId);
            vo.setRules(rules);
        }

        List<String> fileIds = sysTaskFileMapper.queryTaskFileIds(taskId);
        vo.setFileTotal(fileIds.size());

        return vo;
    }

    @Override
    public int deleteTask(String taskId) {
        sysTaskMapper.deleteByPrimaryKey(taskId);
        sysTaskNodeMapper.deleteTaskNodeByTaskId(taskId);
        sysTaskFileMapper.deleteTaskFilesByTaskId(taskId);
        sysTaskRelationMapper.deleteTaskRelationByTaskId(taskId);
        sysTaskNodePropertyMapper.deleteTaskNodePropertyByTaskId(taskId);
        sysTaskMappingMapper.deleteMappingByTaskId(taskId);
        return 1;
    }
}
