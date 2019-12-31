package com.louddt.tag.mapper;

import com.louddt.tag.entity.dbo.SysTaskRelation;
import com.louddt.tag.entity.vo.task.TaskDatasetMappingQuery;
import com.louddt.tag.entity.vo.task.TaskEntry;
import com.louddt.tag.entity.vo.task.TaskEntryVo;
import com.louddt.tag.entity.vo.task.TaskRelationRuleVo;

import java.util.List;

public interface SysTaskRelationMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysTaskRelation record);

    int insertSelective(SysTaskRelation record);

    SysTaskRelation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysTaskRelation record);

    int updateByPrimaryKey(SysTaskRelation record);

    int batchInsert(List<SysTaskRelation> list);

    int deleteTaskRelationByTaskId(String taskId);

    List<SysTaskRelation> queryTaskRelations(String taskId);

    List<TaskEntryVo> queryRelationEntries(String taskId);

    SysTaskRelation queryTaskRelationByQuery(TaskDatasetMappingQuery query);

    List<TaskEntry> queryModeNodeEntries(String taskId);

    List<TaskRelationRuleVo> queryTaskRelationRules(String taskId);
}