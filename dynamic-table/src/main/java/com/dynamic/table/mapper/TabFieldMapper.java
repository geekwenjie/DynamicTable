package com.dynamic.table.mapper;

import java.util.List;

import com.dynamic.table.dao.TableColumnDO;
import com.dynamic.table.domain.TabField;
import com.dynamic.table.domain.TableField;
import org.apache.ibatis.annotations.Param;

/**
 * 列字段Mapper接口
 *
 * @author dwj
 * @date 2024-11-29
 */
public interface TabFieldMapper
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
     * 删除列字段
     *
     * @param id 列字段主键
     * @return 结果
     */
    public int deleteTabFieldById(Long id);

    /**
     * 批量删除列字段
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTabFieldByIds(String[] ids);

    /**
     * 使用ids查询
     * @param ids
     * @return
     */
    public List<TabField> selectByIds(String[] ids);

    /**
     * 批量插入
     * @param tableFieldList
     * @return
     */
    public int batchInsertTabField(List<TabField> tableFieldList);

    /**
     * 根据tabId查询
     * @param tabId
     * @return
     */
    public List<TabField> selectByTabId(Long tabId);

    /**
     * 根据tabId删除
     * @param tabId
     * @return
     */
    public int deleteByTabId(Long tabId);

    /**
     * 根据tabIds批量删除
     * @param tabIds
     * @return
     */
    public int deleteByTabIds(String[] tabIds);

    /**
     * 根据modelIds批量删除
     * @param modelIds
     * @return
     */
    public int deleteByModelIds(String[] modelIds);

    /**
     * 根据模型编码和tab名称查询列字段
     * @param modelCode
     * @param tabName
     * @return
     */
    public List<TableColumnDO> selectTabField(@Param("modelCode") String modelCode, @Param("tabName") String tabName);
}
