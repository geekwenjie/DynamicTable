package com.dynamic.table.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dynamic.table.domain.TableField;
import com.dynamic.table.domain.TableModel;
import com.dynamic.table.service.ITableFieldService;
import com.dynamic.table.service.ITableModelService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.dynamic.table.domain.TableTab;
import com.dynamic.table.service.ITableTabService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 列表标签Controller
 *
 * @author dwj
 * @date 2024-11-28
 */
@Controller
@RequestMapping("/table/tab")
public class TableTabController extends BaseController
{
    private String prefix = "table/tab";

    @Autowired
    private ITableTabService tableTabService;

    @Autowired
    private ITableFieldService tableFieldService;

    @Autowired
    private ITableModelService tableModelService;

    @RequiresPermissions("table:tab:view")
    @GetMapping()
    public String tab()
    {
        return prefix + "/tab";
    }

    /**
     * 查询列表标签列表
     */
    @RequiresPermissions("table:tab:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TableTab tableTab)
    {
        startPage();
        List<TableTab> list = tableTabService.selectTableTabList(tableTab);
        return getDataTable(list);
    }

    /**
     * 导出列表标签列表
     */
    @RequiresPermissions("table:tab:export")
    @Log(title = "列表标签", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TableTab tableTab)
    {
        List<TableTab> list = tableTabService.selectTableTabList(tableTab);
        ExcelUtil<TableTab> util = new ExcelUtil<TableTab>(TableTab.class);
        return util.exportExcel(list, "列表标签数据");
    }

    /**
     * 新增列表标签
     */
    @GetMapping("/add")
    public String add(ModelMap mmap,Long tableModelId)
    {
        mmap.put("tableModelId", tableModelId);
        return prefix + "/add";
    }

    /**
     * 新增保存列表标签
     */
    @RequiresPermissions("table:tab:add")
    @Log(title = "列表标签", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TableTab tableTab)
    {
        return toAjax(tableTabService.insertTableTab(tableTab));
    }

    /**
     * 修改列表标签
     */
    @RequiresPermissions("table:tab:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        TableTab tableTab = tableTabService.selectTableTabById(id);
        mmap.put("tableTab", tableTab);
        return prefix + "/edit";
    }

    /**
     * 修改保存列表标签
     */
    @RequiresPermissions("table:tab:edit")
    @Log(title = "列表标签", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TableTab tableTab)
    {
        return toAjax(tableTabService.updateTableTab(tableTab));
    }

    /**
     * 删除列表标签
     */
    @RequiresPermissions("table:tab:remove")
    @Log(title = "列表标签", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tableTabService.deleteTableTabByIds(ids));
    }


    /**
     * 查询tab字段
     */
    @PostMapping("/tabFieldList")
    @ResponseBody
    public AjaxResult tabFieldList(Long tableTabId, Long tableModelId)
    {
        return AjaxResult.success(tableTabService.selectTabFieldList(tableTabId, tableModelId));
    }

}
