package com.louddt.tag.service;

import com.louddt.tag.entity.vo.task.*;
import com.louddt.tag.exception.DBNotConfigureException;
import com.louddt.tag.utils.Page;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public interface TaskService {

    String saveOrUpdateTask(TaskModifyBO taskBo,String userId) throws DBNotConfigureException, SQLException;

    TaskViewVo queryTaskInfoView(String taskId);

    Page<TaskListVo> queryTaskList(TaskListQuery query);

    TaskDbMappingVo queryTaskDBMapping(TaskDatasetMappingQuery query);

    int saveTaskMappingInfo(TaskDbMappingModifyVo modify) throws SQLException;

    TaskTagVo queryTaskTagView(TaskTagQuery query) throws Exception;

    int saveTaskTagData(TaskTagSaveBO ttsb) throws Exception;

    List<TaskEntry> queryTaskEntryList(TaskNodeListQuery query);

    TaskExportDataVo getTaskExportDataById(String id);

    List<TaskFileListVo> queryTaskFileList(String path,String taskId);

    TaskTagVo importJsonToFile(InputStream in, String taskId, Integer fileSort);

    int deleteTask(String taskId);
}
