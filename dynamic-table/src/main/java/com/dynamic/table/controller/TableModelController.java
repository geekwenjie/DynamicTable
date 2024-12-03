package com.dynamic.table.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.dynamic.table.domain.TableField;
import com.dynamic.table.service.IDbTableService;
import com.dynamic.table.service.ITableFieldService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.generator.domain.GenTable;
import com.ruoyi.generator.domain.GenTableColumn;
import com.ruoyi.generator.service.IGenTableService;
import com.ruoyi.generator.util.GenUtils;
import com.ruoyi.system.service.ISysConfigService;
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
import com.dynamic.table.domain.TableModel;
import com.dynamic.table.service.ITableModelService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 列模型Controller
 *
 * @author dwj
 * @date 2024-11-22
 */
@Controller
@RequestMapping("/table/model")
public class TableModelController extends BaseController
{
    private String prefix = "table/model";

    @Autowired
    private ITableModelService tableModelService;

    @Autowired
    private IDbTableService dbTableServiceImpl;

    @Autowired
    private ITableFieldService tableFieldService;



    @RequiresPermissions("table:model:view")
    @GetMapping()
    public String model()
    {
        return prefix + "/model";
    }

    /**
     * 查询列模型列表
     */
    @RequiresPermissions("table:model:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TableModel tableModel)
    {
        startPage();
        List<TableModel> list = tableModelService.selectTableModelList(tableModel);
        return getDataTable(list);
    }

    /**
     * 导出列模型列表
     */
    @RequiresPermissions("table:model:export")
    @Log(title = "列模型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TableModel tableModel)
    {
        List<TableModel> list = tableModelService.selectTableModelList(tableModel);
        ExcelUtil<TableModel> util = new ExcelUtil<TableModel>(TableModel.class);
        return util.exportExcel(list, "列模型数据");
    }

    /**
     * 新增列模型
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        //查询数据库表
        List<GenTable> dbTableList = dbTableServiceImpl.selectDbTableList(new GenTable());
        mmap.put("dbTableList", dbTableList);
        return prefix + "/add";
    }

    /**
     * 新增保存列模型
     */
    @RequiresPermissions("table:model:add")
    @Log(title = "列模型", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TableModel tableModel)
    {
        return toAjax(tableModelService.insertTableModel(tableModel));
    }

    /**
     * 修改列模型
     */
    @RequiresPermissions("table:model:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        TableModel tableModel = tableModelService.selectTableModelById(id);
        mmap.put("tableModel", tableModel);
        return prefix + "/edit";
    }

    /**
     * 修改保存列模型
     */
    @RequiresPermissions("table:model:edit")
    @Log(title = "列模型", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TableModel tableModel)
    {
        return toAjax(tableModelService.updateTableModel(tableModel));
    }

    /**
     * 删除列模型
     */
    @RequiresPermissions("table:model:remove")
    @Log(title = "列模型", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tableModelService.deleteTableModelByIds(ids));
    }

    /**
     * 查询表字段
     */
    @PostMapping("/getModelCode")
    @ResponseBody
    public AjaxResult getModelCode(String tableName)
    {
        return AjaxResult.success("获取成功",GenUtils.convertClassName(tableName));//模型编码
    }

    /**
     * 查询表字段
     */
    @PostMapping("/columnList")
    @ResponseBody
    public TableDataInfo columnList(Long tableModelId)
    {
        TableDataInfo dataInfo = new TableDataInfo();
        List<TableField> columnList = tableFieldService.selectByModelId(tableModelId);
        dataInfo.setRows(columnList);
        dataInfo.setTotal(columnList.size());
        return dataInfo;
    }
}
