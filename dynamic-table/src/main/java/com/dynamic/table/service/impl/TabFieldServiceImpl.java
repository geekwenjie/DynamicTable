package com.dynamic.table.service.impl;

import java.util.List;

import com.dynamic.table.dao.TableColumnDO;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dynamic.table.mapper.TabFieldMapper;
import com.dynamic.table.domain.TabField;
import com.dynamic.table.service.ITabFieldService;
import com.ruoyi.common.core.text.Convert;

/**
 * 列字段Service业务层处理
 *
 * @author dwj
 * @date 2024-11-29
 */
@Service
public class TabFieldServiceImpl implements ITabFieldService
{
    @Autowired
    private TabFieldMapper tabFieldMapper;

    /**
     * 查询列字段
     *
     * @param id 列字段主键
     * @return 列字段
     */
    @Override
    public TabField selectTabFieldById(Long id)
    {
        return tabFieldMapper.selectTabFieldById(id);
    }

    /**
     * 查询列字段列表
     *
     * @param tabField 列字段
     * @return 列字段
     */
    @Override
    public List<TabField> selectTabFieldList(TabField tabField)
    {
        return tabFieldMapper.selectTabFieldList(tabField);
    }

    /**
     * 新增列字段
     *
     * @param tabField 列字段
     * @return 结果
     */
    @Override
    public int insertTabField(TabField tabField)
    {
        tabField.setCreateTime(DateUtils.getNowDate());
        return tabFieldMapper.insertTabField(tabField);
    }

    /**
     * 修改列字段
     *
     * @param tabField 列字段
     * @return 结果
     */
    @Override
    public int updateTabField(TabField tabField)
    {
        tabField.setUpdateTime(DateUtils.getNowDate());
        return tabFieldMapper.updateTabField(tabField);
    }

    /**
     * 批量删除列字段
     *
     * @param ids 需要删除的列字段主键
     * @return 结果
     */
    @Override
    public int deleteTabFieldByIds(String ids)
    {
        return tabFieldMapper.deleteTabFieldByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除列字段信息
     *
     * @param id 列字段主键
     * @return 结果
     */
    @Override
    public int deleteTabFieldById(Long id)
    {
        return tabFieldMapper.deleteTabFieldById(id);
    }

    /**
     * 根据模型编码和tab名称查询列字段
     * @param modelCode
     * @param tabName
     * @return
     */
    @Override
    public List<TableColumnDO> selectTabField(String modelCode, String tabName) {
        return tabFieldMapper.selectTabField(modelCode,tabName);
    }
}
