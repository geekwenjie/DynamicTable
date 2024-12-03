package com.dynamic.table.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.dynamic.table.domain.TabField;
import com.dynamic.table.service.ITabFieldService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 列字段Controller
 * 
 * @author dwj
 * @date 2024-11-29
 */
@Controller
@RequestMapping("/table/field")
public class TabFieldController extends BaseController
{
    private String prefix = "table/field";

    @Autowired
    private ITabFieldService tabFieldService;

    @RequiresPermissions("table:field:view")
    @GetMapping()
    public String field()
    {
        return prefix + "/field";
    }

    /**
     * 查询列字段列表
     */
    @RequiresPermissions("table:field:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TabField tabField)
    {
        startPage();
        List<TabField> list = tabFieldService.selectTabFieldList(tabField);
        return getDataTable(list);
    }

    /**
     * 导出列字段列表
     */
    @RequiresPermissions("table:field:export")
    @Log(title = "列字段", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TabField tabField)
    {
        List<TabField> list = tabFieldService.selectTabFieldList(tabField);
        ExcelUtil<TabField> util = new ExcelUtil<TabField>(TabField.class);
        return util.exportExcel(list, "列字段数据");
    }

    /**
     * 新增列字段
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存列字段
     */
    @RequiresPermissions("table:field:add")
    @Log(title = "列字段", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TabField tabField)
    {
        return toAjax(tabFieldService.insertTabField(tabField));
    }

    /**
     * 修改列字段
     */
    @RequiresPermissions("table:field:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        TabField tabField = tabFieldService.selectTabFieldById(id);
        mmap.put("tabField", tabField);
        return prefix + "/edit";
    }

    /**
     * 修改保存列字段
     */
    @RequiresPermissions("table:field:edit")
    @Log(title = "列字段", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TabField tabField)
    {
        return toAjax(tabFieldService.updateTabField(tabField));
    }

    /**
     * 删除列字段
     */
    @RequiresPermissions("table:field:remove")
    @Log(title = "列字段", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tabFieldService.deleteTabFieldByIds(ids));
    }
}
