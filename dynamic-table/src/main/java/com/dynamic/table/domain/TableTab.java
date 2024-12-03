package com.dynamic.table.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 列表标签对象 list_table_tab
 *
 * @author dwj
 * @date 2024-11-28
 */
public class TableTab extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 列表名称 */
    @Excel(name = "列表名称")
    private String tabName;

    /** 列表标题 */
    @Excel(name = "列表标题")
    private String tabTitle;

    /** 列表模型ID */
    @Excel(name = "列表模型ID")
    private Long tableModelId;

    /** 字段ID序列（临时字段） */
    public String fieldIds;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setTabName(String tabName)
    {
        this.tabName = tabName;
    }

    public String getTabName()
    {
        return tabName;
    }

    public void setTabTitle(String tabTitle)
    {
        this.tabTitle = tabTitle;
    }

    public String getTabTitle()
    {
        return tabTitle;
    }

    public void setTableModelId(Long tableModelId)
    {
        this.tableModelId = tableModelId;
    }

    public Long getTableModelId()
    {
        return tableModelId;
    }

    public String getFieldIds() {
        return fieldIds;
    }

    public void setFieldIds(String fieldIds) {
        this.fieldIds = fieldIds;
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
            .append("tabName", getTabName())
            .append("tabTitle", getTabTitle())
            .append("tableModelId", getTableModelId())
            .toString();
    }
}
