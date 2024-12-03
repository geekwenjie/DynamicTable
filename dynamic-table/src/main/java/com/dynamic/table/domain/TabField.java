package com.dynamic.table.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 列字段对象 list_tab_field
 *
 * @author dwj
 * @date 2024-11-29
 */
public class TabField extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 表模型ID */
    @Excel(name = "表模型ID")
    private Long tableModelId;

    /** 列表标签ID */
    @Excel(name = "列表标签ID")
    private Long tabId;

    /** 字段代码 */
    @Excel(name = "字段代码")
    private String fieldCode;

    /** 字段ID */
    @Excel(name = "字段ID")
    private Long fieldId;

    /** 字段顺序 */
    @Excel(name = "字段顺序")
    private Integer fieldOrder;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setTableModelId(Long tableModelId)
    {
        this.tableModelId = tableModelId;
    }

    public Long getTableModelId()
    {
        return tableModelId;
    }

    public void setTabId(Long tabId)
    {
        this.tabId = tabId;
    }

    public Long getTabId()
    {
        return tabId;
    }

    public void setFieldCode(String fieldCode)
    {
        this.fieldCode = fieldCode;
    }

    public String getFieldCode()
    {
        return fieldCode;
    }

    public void setFieldId(Long fieldId)
    {
        this.fieldId = fieldId;
    }

    public Long getFieldId()
    {
        return fieldId;
    }

    public void setFieldOrder(Integer fieldOrder)
    {
        this.fieldOrder = fieldOrder;
    }

    public Integer getFieldOrder()
    {
        return fieldOrder;
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
            .append("tabId", getTabId())
            .append("fieldCode", getFieldCode())
            .append("fieldId", getFieldId())
            .append("fieldOrder", getFieldOrder())
            .toString();
    }
}
