package com.louddt.tag.mapper;

import com.louddt.tag.entity.dbo.SysTaskNode;
import com.louddt.tag.entity.vo.task.TaskDatasetMappingQuery;
import com.louddt.tag.entity.vo.task.TaskEntry;
import com.louddt.tag.entity.vo.task.TaskEntryVo;

import java.util.List;

public interface SysTaskNodeMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysTaskNode record);

    int insertSelective(SysTaskNode record);

    SysTaskNode selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysTaskNode record);

    int updateByPrimaryKey(SysTaskNode record);

    int batchInsert(List<SysTaskNode> list);

    int deleteTaskNodeByTaskId(String taskId);

    String queryTaskNodes(String taskId);

    List<TaskEntryVo> queryNodeEntries(String taskId);

    SysTaskNode queryTaskNodeByQuery(TaskDatasetMappingQuery query);

    List<TaskEntry> queryTxtNodeEntries(String taskId);

    List<TaskEntry> queryModeNodeEntries(String taskId);
}