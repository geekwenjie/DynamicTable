package com.dynamic.table.service;

import java.util.List;

import com.dynamic.table.dao.TableColumnDO;
import com.dynamic.table.domain.TabField;

/**
 * 列字段Service接口
 *
 * @author dwj
 * @date 2024-11-29
 */
public interface ITabFieldService
{
    /**
     * 查询列字段
     *
     * @param id 列字段主键
     * @return 列字段
     */
    public TabField selectTabFieldById(Long id);

    /**
     * 查询列字段列表
     *
     * @param tabField 列字段
     * @return 列字段集合
     */
    public List<TabField> selectTabFieldList(TabField tabField);

    /**
     * 新增列字段
     *
     * @param tabField 列字段
     * @return 结果
     */
    public int insertTabField(TabField tabField);

    /**
     * 修改列字段
     *
     * @param tabField 列字段
     * @return 结果
     */
    public int updateTabField(TabField tabField);

    /**
     * 批量删除列字段
     *
     * @param ids 需要删除的列字段主键集合
     * @return 结果
     */
    public int deleteTabFieldByIds(String ids);

    /**
     * 删除列字段信息
     *
     * @param id 列字段主键
     * @return 结果
     */
    public int deleteTabFieldById(Long id);

    /**
     * 根据模型编码和tab名称查询列字段
     * @param modelCode
     * @param tabName
     * @return
     */
    public List<TableColumnDO> selectTabField(String modelCode, String tabName);
}
