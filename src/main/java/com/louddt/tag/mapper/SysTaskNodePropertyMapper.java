package com.louddt.tag.mapper;

import com.louddt.tag.entity.dbo.SysTaskNodeProperty;
import com.louddt.tag.entity.vo.task.TaskDatasetMappingQuery;
import com.louddt.tag.entity.vo.task.TaskNodePropertyVo;

import java.util.List;

public interface SysTaskNodePropertyMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysTaskNodeProperty record);

    int insertSelective(SysTaskNodeProperty record);

    SysTaskNodeProperty selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysTaskNodeProperty record);

    int updateByPrimaryKey(SysTaskNodeProperty record);

    int batchInsert(List<SysTaskNodeProperty> list);

    int deleteTaskNodePropertyByTaskId(String taskId);

    List<TaskNodePropertyVo> queryTaskNodeProperties(String taskId);

    List<String> queryTaskEntryProperties(TaskDatasetMappingQuery query);
}