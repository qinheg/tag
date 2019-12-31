package com.louddt.tag.mapper;

import com.louddt.tag.entity.dbo.SysAlgoModel;
import com.louddt.tag.entity.vo.model.ModelListQuery;
import com.louddt.tag.entity.vo.model.ModelListVo;

import java.util.List;

public interface SysAlgoModelMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysAlgoModel record);

    int insertSelective(SysAlgoModel record);

    SysAlgoModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysAlgoModel record);

    int updateByPrimaryKey(SysAlgoModel record);

    List<ModelListVo> queryModelList(ModelListQuery query);

    int queryModelListTotal(ModelListQuery query);

    int deteleModelsByIds(List<String> list);
}