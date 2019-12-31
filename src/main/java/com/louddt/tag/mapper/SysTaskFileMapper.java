package com.louddt.tag.mapper;

import com.louddt.tag.entity.dbo.SysTaskFile;
import com.louddt.tag.entity.vo.task.TaskExportDataVo;
import com.louddt.tag.entity.vo.task.TaskFileListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysTaskFileMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysTaskFile record);

    int insertSelective(SysTaskFile record);
	
	SysTaskFile selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysTaskFile record);

    int updateByPrimaryKeyWithBLOBs(SysTaskFile record);

    int updateByPrimaryKey(SysTaskFile record);

    int batchInsert(List<SysTaskFile> list);

    int deleteTaskFilesByTaskId(String taskId);

    List<String> queryTaskFileIds(String taskId);

    String queryDataJsonByTaskIdFileId(@Param("fileId") String fileId, @Param("taskId") String taskId);

    int deteleOneByTaskIdFileId(SysTaskFile taskFile);

    TaskExportDataVo getTaskExportDataById(String id);

    SysTaskFile selectByTaskIdFileId(@Param("taskId")String taskId, @Param("fileId")String fileId);

    List<String> queryNotFinishFile(String taskId);

    List<TaskFileListVo> queryTaskFileList(String taskId);
}