package com.dynamic.table.service;

import java.util.List;
import com.dynamic.table.domain.TableField;

/**
 * 表格模型字段Service接口
 *
 * @author dwj
 * @date 2024-11-22
 */
public interface ITableFieldService
{
    /**
     * 查询表格模型字段
     *
     * @param id 表格模型字段主键
     * @return 表格模型字段
     */
    public TableField selectTableFieldById(Long id);

    /**
     * 查询表格模型字段列表
     *
     * @param tableField 表格模型字段
     * @return 表格模型字段集合
     */
    public List<TableField> selectTableFieldList(TableField tableField);

    /**
     * 新增表格模型字段
     *
     * @param tableField 表格模型字段
     * @return 结果
     */
    public int insertTableField(TableField tableField);

    /**
     * 修改表格模型字段
     *
     * @param tableField 表格模型字段
     * @return 结果
     */
    public int updateTableField(TableField tableField);

    /**
     * 批量删除表格模型字段
     *
     * @param ids 需要删除的表格模型字段主键集合
     * @return 结果
     */
    public int deleteTableFieldByIds(String ids);

    /**
     * 删除表格模型字段信息
     *
     * @param id 表格模型字段主键
     * @return 结果
     */
    public int deleteTableFieldById(Long id);

    /**
     * 根据模型id查询字段列表
     * @param tableModelId
     * @return
     */
    public List<TableField> selectByModelId(Long tableModelId);
}
