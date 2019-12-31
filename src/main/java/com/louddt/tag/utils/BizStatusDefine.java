package com.louddt.tag.utils;

import java.util.ArrayList;
import java.util.List;

public class BizStatusDefine {

    public static List<String> SPECIAL_COLUMNS = new ArrayList<>();
    static {
        SPECIAL_COLUMNS.add("要点内容");
    }
    /** 建表特殊字段长度 */
    public static final int SPECIAL_COLUMN_LENGTH = 4000;

    /** 任务分类：1-算法模型应用*/
    public static final int TASK_TYPE_CODE_MODEL = 1;
    /** 任务分类：2-人工文档标注*/
    public static final int TASK_TYPE_CODE_MANUAL = 2;
    /** 任务子分类：1-文本标注 */
    public static final int TASK_SUB_TYPE_CODE_TXT = 1;
    /** 任务子分类：2-实体关系标注 */
    public static final int TASK_SUB_TYPE_CODE_RELATION = 2;

    /** 任务分类：1-算法模型应用*/
    public static final String TASK_TYPE_NAME_MODEL = "算法模型应用";
    /** 任务分类：2-人工文档标注*/
    public static final String TASK_TYPE_NAME_MANUAL = "人工文档标注";
    /** 任务子分类：1-文本标注 */
    public static final String TASK_SUB_TYPE_NAME_TXT = "文本标注";
    /** 任务子分类：2-实体关系标注 */
    public static final String TASK_SUB_TYPE_NAME_RELATION = "实体关系标注";
    /** 任务对象类型 1-实体 */
    public static final Integer TASK_ENTRY_TYPE_NODE = 1;
    /** 任务对象类型 2-关系 */
    public static final Integer TASK_ENTRY_TYPE_RELATION = 2;
    /** 文本标注入库属性：分类 */
    public static final String TASK_TXT_PROPERTIES_TYPE = "分类";
    /** 文本标注入库属性：内容 */
    public static final String TASK_TXT_PROPERTIES_CONTENT = "内容";
    /** 标注任务状态：0-未开始 */
    public static final Integer TASK_STATUS_PEDING = 0;
    /** 标注任务状态：1-进行中 */
    public static final Integer TASK_STATUS_DOING = 1;
    /** 标注任务状态：2-已完成 */
    public static final Integer TASK_STATUS_FINISH = 2;
    /** 标注任务状态：0-未开始 */
    public static final String TASK_STATUS_NAME_PEDING = "未开始";
    /** 标注任务状态：1-进行中 */
    public static final String TASK_STATUS_NAME_DOING = "进行中";
    /** 标注任务状态：2-已完成 */
    public static final String TASK_STATUS_NAME_FINISH = "已完成";
    /** 数据交互状态：0- 未对接 */
    public static final Integer TASK_KG_STATUS_PEDING = 0;
    /** 数据交互状态：1- 已对接 */
    public static final Integer TASK_KG_STATUS_DOING = 1;
    /** 数据交互状态：2- 对接后修改过 */
    public static final Integer TASK_KG_STATUS_REDONE = 2;
    /** 导出路径 */
    public static final String TASK_JSON_DATA_EXPORT_PATH = "/task/exportJson?id=";

    /** 常量：token */
    public static final String TASK_LOGIN_TOKEN_CODE = "token";

    /** 文本标注表名 */
    public static final String TASK_MODEL_TEXT_TABLE_NAME = "classify";
    /** 自动建表表主键字段 */
    public static final String TASK_MODEL_TABLE_PRIMARY_KEY_ID = "id int(10) unsigned NOT NULL AUTO_INCREMENT";
    /** 数据加密 */
    public static final String TASK_MODEL_TABLE_MD5_CODE = "md_code";
}
