package com.louddt.tag.mapper;

import com.louddt.tag.entity.dbo.SysTask;
import com.louddt.tag.entity.vo.task.TaskDbMappingVo;
import com.louddt.tag.entity.vo.task.TaskListQuery;
import com.louddt.tag.entity.vo.task.TaskListVo;
import com.louddt.tag.entity.vo.task.TaskViewVo;

import java.util.List;

public interface SysTaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysTask record);

    int insertSelective(SysTask record);

    SysTask selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysTask record);

    int updateByPrimaryKey(SysTask record);

    TaskViewVo queryTaskViewVo(String id);

    int queryTaskListTotal(TaskListQuery query);

    List<TaskListVo> queryTaskList(TaskListQuery query);

    TaskDbMappingVo queryTaskDefaultDBMapping(String taskId);
}