package com.louddt.tag.mapper;

import com.louddt.tag.entity.dbo.SysTaskMapping;
import com.louddt.tag.entity.vo.task.TaskDatasetMappingQuery;
import com.louddt.tag.entity.vo.task.TaskDbMappingVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysTaskMappingMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysTaskMapping record);

    int insertSelective(SysTaskMapping record);

    SysTaskMapping selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysTaskMapping record);

    int updateByPrimaryKey(SysTaskMapping record);

    TaskDbMappingVo queryTaskDBMapping(@Param("taskId") String taskId, @Param("nodeName") String nodeName);

    int batchInsert(List<SysTaskMapping> list);

    int deleteMappingByTaskId(String taskId);

    SysTaskMapping queryTaskDBNodeMapping(@Param("taskId") String taskId, @Param("nodeName") String nodeName);

    TaskDbMappingVo queryTaskDBClassify(String taskId);
}