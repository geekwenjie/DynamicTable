package com.dynamic.table.mapper;

import java.util.List;
import com.dynamic.table.domain.TableModel;

/**
 * 列模型Mapper接口
 *
 * @author dwj
 * @date 2024-11-22
 */
public interface TableModelMapper
{
    /**
     * 查询列模型
     *
     * @param id 列模型主键
     * @return 列模型
     */
    public TableModel selectTableModelById(Long id);

    /**
     * 查询列模型列表
     *
     * @param tableModel 列模型
     * @return 列模型集合
     */
    public List<TableModel> selectTableModelList(TableModel tableModel);

    /**
     * 新增列模型
     *
     * @param tableModel 列模型
     * @return 结果
     */
    public int insertTableModel(TableModel tableModel);

    /**
     * 修改列模型
     *
     * @param tableModel 列模型
     * @return 结果
     */
    public int updateTableModel(TableModel tableModel);

    /**
     * 删除列模型
     *
     * @param id 列模型主键
     * @return 结果
     */
    public int deleteTableModelById(Long id);

    /**
     * 批量删除列模型
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTableModelByIds(String[] ids);

    /**
     * 根据模型编码查询
     * @param modelCode
     * @return
     */
    public List<TableModel> selectByModelCode(String modelCode);
}
