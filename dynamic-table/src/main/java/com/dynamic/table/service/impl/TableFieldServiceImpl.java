package com.dynamic.table.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dynamic.table.mapper.TableFieldMapper;
import com.dynamic.table.domain.TableField;
import com.dynamic.table.service.ITableFieldService;
import com.ruoyi.common.core.text.Convert;

/**
 * 表格模型字段Service业务层处理
 *
 * @author dwj
 * @date 2024-11-22
 */
@Service
public class TableFieldServiceImpl implements ITableFieldService
{
    @Autowired
    private TableFieldMapper tableFieldMapper;

    /**
     * 查询表格模型字段
     *
     * @param id 表格模型字段主键
     * @return 表格模型字段
     */
    @Override
    public TableField selectTableFieldById(Long id)
    {
        return tableFieldMapper.selectTableFieldById(id);
    }

    /**
     * 查询表格模型字段列表
     *
     * @param tableField 表格模型字段
     * @return 表格模型字段
     */
    @Override
    public List<TableField> selectTableFieldList(TableField tableField)
    {
        return tableFieldMapper.selectTableFieldList(tableField);
    }

    /**
     * 新增表格模型字段
     *
     * @param tableField 表格模型字段
     * @return 结果
     */
    @Override
    public int insertTableField(TableField tableField)
    {
        tableField.setCreateTime(DateUtils.getNowDate());
        return tableFieldMapper.insertTableField(tableField);
    }

    /**
     * 修改表格模型字段
     *
     * @param tableField 表格模型字段
     * @return 结果
     */
    @Override
    public int updateTableField(TableField tableField)
    {
        tableField.setUpdateTime(DateUtils.getNowDate());
        return tableFieldMapper.updateTableField(tableField);
    }

    /**
     * 批量删除表格模型字段
     *
     * @param ids 需要删除的表格模型字段主键
     * @return 结果
     */
    @Override
    public int deleteTableFieldByIds(String ids)
    {
        return tableFieldMapper.deleteTableFieldByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除表格模型字段信息
     *
     * @param id 表格模型字段主键
     * @return 结果
     */
    @Override
    public int deleteTableFieldById(Long id)
    {
        return tableFieldMapper.deleteTableFieldById(id);
    }

    /**
     * 根据模型id查询字段列表
     * @param tableModelId
     * @return
     */
    @Override
    public List<TableField> selectByModelId(Long tableModelId) {
        return tableFieldMapper.selectByModelId(tableModelId);
    }
}
