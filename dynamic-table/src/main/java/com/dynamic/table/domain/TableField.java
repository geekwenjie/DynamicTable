package com.dynamic.table.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 表格模型字段对象 list_table_field
 *
 * @author dwj
 * @date 2024-11-22
 */
public class TableField extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 表模型ID */
    @Excel(name = "表模型ID")
    private Long tableModelId;

    /** 字段名称 */
    @Excel(name = "字段名称")
    private String fieldName;

    /** 字段代码 */
    @Excel(name = "字段代码")
    private String fieldCode;

    /** 是否显示（1-显示；0：隐藏） */
    @Excel(name = "是否显示", readConverterExp = "1=-显示；0：隐藏")
    private Integer showFlag;

    /** 是否排序（1-排序；0：不排序） */
    @Excel(name = "是否排序", readConverterExp = "1=-排序；0：不排序")
    private Integer sortFlag;

    /** 默认宽度 */
    @Excel(name = "默认宽度")
    private Integer width;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTableModelId() {
        return tableModelId;
    }

    public void setTableModelId(Long tableModelId) {
        this.tableModelId = tableModelId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public Integer getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(Integer showFlag) {
        this.showFlag = showFlag;
    }

    public Integer getSortFlag() {
        return sortFlag;
    }

    public void setSortFlag(Integer sortFlag) {
        this.sortFlag = sortFlag;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("tableModelId", getTableModelId())
            .append("fieldName", getFieldName())
            .append("fieldCode", getFieldCode())
            .append("showFlag", getShowFlag())
            .append("sortFlag", getSortFlag())
            .append("width", getWidth())
            .toString();
    }
}
