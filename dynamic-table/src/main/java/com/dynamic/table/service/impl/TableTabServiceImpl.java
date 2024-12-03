package com.dynamic.table.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dynamic.table.domain.TabField;
import com.dynamic.table.domain.TableField;
import com.dynamic.table.domain.TableModel;
import com.dynamic.table.mapper.TabFieldMapper;
import com.dynamic.table.mapper.TableFieldMapper;
import com.dynamic.table.mapper.TableModelMapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dynamic.table.mapper.TableTabMapper;
import com.dynamic.table.domain.TableTab;
import com.dynamic.table.service.ITableTabService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.util.CollectionUtils;

/**
 * 列表标签Service业务层处理
 *
 * @author dwj
 * @date 2024-11-28
 */
@Service
public class TableTabServiceImpl implements ITableTabService
{
    @Autowired
    private TableTabMapper tableTabMapper;

    @Autowired
    private TableModelMapper tableModelMapper;

    @Autowired
    private TableFieldMapper tableFieldMapper;

    @Autowired
    private TabFieldMapper tabFieldMapper;

    /**
     * 查询列表标签
     *
     * @param id 列表标签主键
     * @return 列表标签
     */
    @Override
    public TableTab selectTableTabById(Long id)
    {
        return tableTabMapper.selectTableTabById(id);
    }

    /**
     * 查询列表标签列表
     *
     * @param tableTab 列表标签
     * @return 列表标签
     */
    @Override
    public List<TableTab> selectTableTabList(TableTab tableTab)
    {
        return tableTabMapper.selectTableTabList(tableTab);
    }

    /**
     * 新增列表标签
     *
     * @param tableTab 列表标签
     * @return 结果
     */
    @Override
    public int insertTableTab(TableTab tableTab)
    {
        //判断是否重复
        List<TableTab> tabList = tableTabMapper.selectByTabName(tableTab.getTabName());
        Validate.isTrue(CollectionUtils.isEmpty(tabList), "列表名称已存在");
        tableTab.setCreateTime(DateUtils.getNowDate());
        Validate.notBlank(tableTab.getFieldIds(), "列表字段不能为空");
        int result = tableTabMapper.insertTableTab(tableTab);
        if(result > 0){
            String[] fieldIds = Convert.toStrArray(tableTab.getFieldIds());
            List<TabField> tableFieldList = Lists.newArrayList();
            //批量插入
            for (int i = 0; i < fieldIds.length; i++) {
                TabField tabField = new TabField();
                tabField.setFieldId(Long.valueOf(fieldIds[i]));
                tabField.setTabId(tableTab.getId());
                tabField.setTableModelId(tableTab.getTableModelId());
                tabField.setFieldOrder(i);
                tableFieldList.add(tabField);
            }
            result = tabFieldMapper.batchInsertTabField(tableFieldList);
        }
        return result;
    }

    /**
     * 修改列表标签
     *
     * @param tableTab 列表标签
     * @return 结果
     */
    @Override
    public int updateTableTab(TableTab tableTab)
    {
        //判断是否重复
        List<TableTab> tabList = tableTabMapper.selectByTabName(tableTab.getTabName());
        Validate.isTrue(CollectionUtils.isEmpty(tabList) || tabList.get(0).getId().equals(tableTab.getId()), "列表名称已存在");
        Validate.notBlank(tableTab.getFieldIds(), "列表字段不能为空");
        tableTab.setUpdateTime(DateUtils.getNowDate());
        int result = tabFieldMapper.deleteByTabId(tableTab.getId());
        Validate.isTrue(result > 0, "删除失败");
        String[] fieldIds = Convert.toStrArray(tableTab.getFieldIds());
        List<TabField> tableFieldList = Lists.newArrayList();
        //批量插入
        for (int i = 0; i < fieldIds.length; i++) {
            TabField tabField = new TabField();
            tabField.setFieldId(Long.valueOf(fieldIds[i]));
            tabField.setTabId(tableTab.getId());
            tabField.setTableModelId(tableTab.getTableModelId());
            tabField.setFieldOrder(i);
            tableFieldList.add(tabField);
        }
        tabFieldMapper.batchInsertTabField(tableFieldList);
        return tableTabMapper.updateTableTab(tableTab);
    }

    /**
     * 批量删除列表标签
     *
     * @param ids 需要删除的列表标签主键
     * @return 结果
     */
    @Override
    public int deleteTableTabByIds(String ids)
    {
        //删除tab字段
        tabFieldMapper.deleteByTabIds(Convert.toStrArray(ids));
        return tableTabMapper.deleteTableTabByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除列表标签信息
     *
     * @param id 列表标签主键
     * @return 结果
     */
    @Override
    public int deleteTableTabById(Long id)
    {
        return tableTabMapper.deleteTableTabById(id);
    }

    /**
     * 查询tab字段列表（多选组件专用）
     * @param tableTabId
     * @param tableModelId
     * @return
     */
    @Override
    public JSONArray selectTabFieldList(Long tableTabId, Long tableModelId) {
        TableModel tableModel = tableModelMapper.selectTableModelById(tableModelId);
        List<TableField> columnList = tableFieldMapper.selectByModelId(tableModelId);
        Validate.notEmpty(columnList, "表格模型字段为空");
        List<TabField> tabFieldList = tabFieldMapper.selectByTabId(tableTabId);
        Map<Long, TabField> tabFieldMap = CollectionUtils.isEmpty(tabFieldList) ? new HashMap<Long, TabField>() : tabFieldList.stream()
                .collect(Collectors.toMap(TabField::getFieldId, tabField -> tabField));
        JSONArray jsonArray = columnList.stream()
                .map(field -> {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", field.getId());
                    jsonObject.put("title", field.getFieldName());
                    jsonObject.put("checkect", tabFieldMap.containsKey(field.getId()));
                    jsonObject.put("isfield", true);
                    jsonObject.put("sort", tabFieldMap.containsKey(field.getId()) ? tabFieldMap.get(field.getId()).getFieldOrder() : null);
                    return jsonObject;
                })
                .collect(Collectors.toCollection(JSONArray::new));
        JSONObject parent = new JSONObject();
        parent.put("title", tableModel.getModelName());
        parent.put("checkect", false);
        parent.put("isfield", false);
        parent.put("children", jsonArray);
        JSONArray data = new JSONArray();
        data.add(parent);
        return data;
    }
}
