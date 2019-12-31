package com.louddt.tag.service.impl;

import com.louddt.tag.entity.dbo.*;
import com.louddt.tag.entity.vo.model.*;
import com.louddt.tag.mapper.*;
import com.louddt.tag.service.ModelService;
import com.louddt.tag.utils.*;
import com.louddt.tag.utils.UUID;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.*;

@Slf4j
@Service
@Transactional
public class ModelServiceImpl implements ModelService {

    @Autowired
    private SysAlgoModelMapper sysAlgoModelMapper;
    @Autowired
    private SysModelNodeMapper sysModelNodeMapper;
    @Autowired
    private SysModelRelationMapper sysModelRelationMapper;
    @Autowired
    private SysModelNodePropertyMapper sysModelNodePropertyMapper;
    @Autowired
    private SysFtpFileMapper sysFtpFileMapper;
    @Autowired
    private SysModelDatasetMapper sysModelDatasetMapper;

    @Override
    public void importModelAndSaveInfo(InputStream in, String path, String fileName, String aliseName, long fileSize, String suffix, String contents,String userId) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(in);
        Element root = doc.getRootElement();
        Date time = new Date();

        //保存文件信息到附件表
        SysFtpFile file = new SysFtpFile();
        file.setId(UUID.uuId());
        file.setPath(path);
        file.setRealName(fileName);
        file.setName(aliseName);
        file.setFileType("1");
        file.setCreateTime(time);
        file.setContents(contents);
        file.setSize(String.valueOf(fileSize));
        file.setExtType(suffix);
        file.setCreateUser(userId);
        sysFtpFileMapper.insert(file);

        //获取算法模型信息
        String modelName = root.elementText("name");
        String modelCode = root.elementText("code");
        String modelType = root.elementText("modelType");
        String descript = root.elementText("descript");
        String url = root.elementText("url");
        String variable = root.elementText("variable");

        SysAlgoModel model = new SysAlgoModel();
        model.setId(UUID.uuId());
        model.setName(modelName);
        model.setCode(modelCode);
        model.setType(Integer.valueOf(modelType));
        model.setDescri(descript);
        model.setUrl(url);
        model.setVariable(variable);
        model.setFileId(file.getId());
        model.setCreateTime(time);
        model.setCreateUser(userId);
        sysAlgoModelMapper.insert(model);

        //获取实体信息
        String entities = root.elementText("entity");
        entities = entities.replaceAll("\n","");
        entities = entities.replaceAll("\t","");
        entities = entities.replaceAll("\r","");
        List<String> nodes = new ArrayList<String>();
        if(entities != null && !entities.isEmpty()){
            String[] entityArr = entities.split(",");
            nodes = Arrays.asList(entityArr);
        }

        List<SysModelNode> modelNodes = new ArrayList<>();
        for (String nodeName : nodes) {
            SysModelNode node = new SysModelNode();
            node.setId(UUID.uuId());
            node.setNodeName(nodeName);
            node.setCreateTime(time);
            node.setModelId(model.getId());
            modelNodes.add(node);
        }
        if(modelNodes.size() > 0){
            sysModelNodeMapper.batchInsert(modelNodes);
        }

        //获取关系信息
        List<SysModelRelation> modelRelations = new ArrayList<>();
        Element relations = root.element("relations");
        if(relations != null){
            List<Element> rs = relations.elements("relation");
            if(rs != null && rs.size() > 0){
                for (Element r : rs) {
                    String starNode = r.elementText("startNode");
                    String endNode = r.elementText("endNode");
                    String link = r.elementText("link");
                    SysModelRelation relation  = new SysModelRelation();
                    relation.setId(UUID.uuId());
                    relation.setName(link);
                    relation.setStartNodeName(starNode);
                    relation.setEndNodeName(endNode);
                    relation.setCreateTime(time);
                    relation.setModelId(model.getId());
                    modelRelations.add(relation);
                }
                if(modelRelations.size() > 0){
                    sysModelRelationMapper.batchInsert(modelRelations);
                }
            }
        }


        //获取实体属性
        List<SysModelNodeProperty> nodeProperties = new ArrayList<>();
        Element properties = root.element("properties");
        if(properties != null){
            List<Element> ps = properties.elements("property");
            if(ps != null && ps.size() > 0){
                for (Element p : ps) {
                    String name = p.elementText("name");
                    String value = p.elementText("value");
                    String entity = p.elementText("entity");
                    String type = p.elementText("type");

                    SysModelNodeProperty property = new SysModelNodeProperty();
                    property.setId(UUID.uuId());
                    property.setName(name);
                    property.setValue(value);
                    property.setType(type);
                    property.setNodeName(entity);
                    property.setModelId(model.getId());
                    nodeProperties.add(property);
                }
                if(nodeProperties.size() > 0){
                    sysModelNodePropertyMapper.batchInsert(nodeProperties);
                }
            }
        }
    }

    @Override
    public Page<ModelListVo> queryModelList(ModelListQuery query) {

        Integer pageIndex = query.getPageIndex();
        Integer pageSize = query.getPageSize();
        if(pageIndex != null){
            Integer pageStart = (pageIndex - 1) * pageSize;
            query.setPageStart(pageStart);
        }

        List<ModelListVo> models = new ArrayList<>();
        int total = sysAlgoModelMapper.queryModelListTotal(query);
        if(!NumberUtils.numberBlank(total)){
            models = sysAlgoModelMapper.queryModelList(query);
        }
        if(models != null && models.size() > 0){
            for (ModelListVo model : models) {
                if(model.getType() == 1){
                    model.setTypeName("实体关系标注");
                }else{
                    model.setTypeName("文档分类");
                }
            }
        }
        Page<ModelListVo> datas = Page.page(total,models,pageIndex,pageSize);
        return datas;
    }

    @Override
    public int deleteModels(ModelDeteleBO delete) {
        String modelIds = delete.getModelIds();
        String[] models = modelIds.split(SymbolConstants.COMMA);
        List<String> ids = Arrays.asList(models);

        int num = sysAlgoModelMapper.deteleModelsByIds(ids);

        return num;
    }

    @Override
    public SysModelDataset queryModelDatasetView(String modelId) {
        return sysModelDatasetMapper.queryModelDatasetByModelId(modelId);
    }

    @Override
    public String saveOrUpdateModelDataset(ModelDatasetBO dataset) {
        SysModelDataset set = sysModelDatasetMapper.queryModelDatasetByModelId(dataset.getModelId());
        SysModelDataset md = new SysModelDataset(dataset);
        if(set == null){
            md.setId(UUID.uuId());
            md.setCreateTime(new Date());
            sysModelDatasetMapper.insert(md);
        }else {
            md.setId(set.getId());
            sysModelDatasetMapper.updateByPrimaryKeySelective(md);
        }
        return md.getId();
    }

    @Override
    public String getModelFileIdByModelId(String modelId) {
        SysAlgoModel model = sysAlgoModelMapper.selectByPrimaryKey(modelId);
        if(model == null){
            return null;
        }
        return model.getFileId();
    }

    @Override
    public Map<String, Object> queryModelInfoView(String modelId) {
        Map<String,Object> data = new HashMap<String,Object>();
        String nodes = sysModelNodeMapper.queryModelNodeName(modelId);
        data.put("nodes",nodes);

        List<SysModelRelation> mrs = sysModelRelationMapper.queryModelRelationByModelId(modelId);
        List<String> relations = new ArrayList<String>();
        if(mrs != null && mrs.size() > 0){
            for (SysModelRelation mr : mrs) {
                StringBuilder sb = new StringBuilder();
                sb.append(mr.getStartNodeName())
                        .append(SymbolConstants.HLINE)
                        .append(mr.getName())
                        .append(SymbolConstants.HLINE)
                        .append(mr.getEndNodeName());
                relations.add(sb.toString());
            }
        }
        data.put("relations",relations);

        List<ModelNodePropertyVo> nps = sysModelNodePropertyMapper.queryModelNodePropertyByModelId(modelId);
        List<String> properties = new ArrayList<>();
        for (ModelNodePropertyVo np : nps) {
            StringBuilder sb = new StringBuilder();
            sb.append(np.getName())
                    .append(SymbolConstants.HLINE)
                    .append(np.getNodeName());
            if(StringUtil.isNotEmptyOrNull(np.getValue())){
                sb.append(SymbolConstants.HLINE)
                        .append(np.getValue());
            }
            properties.add(sb.toString());
        }
        data.put("properties",properties);

        return data;
    }
}
