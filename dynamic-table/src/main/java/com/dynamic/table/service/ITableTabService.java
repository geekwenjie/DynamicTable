package com.dynamic.table.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dynamic.table.domain.TableTab;

/**
 * 列表标签Service接口
 *
 * @author dwj
 * @date 2024-11-28
 */
public interface ITableTabService
{
    /**
     * 查询列表标签
     *
     * @param id 列表标签主键
     * @return 列表标签
     */
    public TableTab selectTableTabById(Long id);

    /**
     * 查询列表标签列表
     *
     * @param tableTab 列表标签
     * @return 列表标签集合
     */
    public List<TableTab> selectTableTabList(TableTab tableTab);

    /**
     * 新增列表标签
     *
     * @param tableTab 列表标签
     * @return 结果
     */
    public int insertTableTab(TableTab tableTab);

    /**
     * 修改列表标签
     *
     * @param tableTab 列表标签
     * @return 结果
     */
    public int updateTableTab(TableTab tableTab);

    /**
     * 批量删除列表标签
     *
     * @param ids 需要删除的列表标签主键集合
     * @return 结果
     */
    public int deleteTableTabByIds(String ids);

    /**
     * 删除列表标签信息
     *
     * @param id 列表标签主键
     * @return 结果
     */
    public int deleteTableTabById(Long id);


    /**
     * 查询tab字段列表（多选组件专用）
     * @param tableTabId
     * @param tableModelId
     * @return
     */
    public JSONArray selectTabFieldList(Long tableTabId, Long tableModelId);
}
