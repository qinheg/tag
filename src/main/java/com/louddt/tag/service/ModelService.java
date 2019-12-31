package com.louddt.tag.service;

import com.louddt.tag.entity.dbo.SysModelDataset;
import com.louddt.tag.entity.vo.model.ModelDatasetBO;
import com.louddt.tag.entity.vo.model.ModelDeteleBO;
import com.louddt.tag.entity.vo.model.ModelListQuery;
import com.louddt.tag.entity.vo.model.ModelListVo;
import com.louddt.tag.utils.Page;
import org.dom4j.DocumentException;

import java.io.InputStream;
import java.util.Map;

public interface ModelService {
    void importModelAndSaveInfo(InputStream in, String path, String fileName, String aliseName, long fileSize, String suffix, String contents,String userId)  throws DocumentException;

    Page<ModelListVo> queryModelList(ModelListQuery query);

    int deleteModels(ModelDeteleBO delete);

    SysModelDataset queryModelDatasetView(String modelId);

    String saveOrUpdateModelDataset(ModelDatasetBO dataset);

    String getModelFileIdByModelId(String modelId);

    Map<String, Object> queryModelInfoView(String modelId);
}
