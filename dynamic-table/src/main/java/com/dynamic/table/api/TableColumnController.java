package com.dynamic.table.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dynamic.table.dao.TableColumnDO;
import com.dynamic.table.service.ITabFieldService;
import com.dynamic.table.service.ITableFieldService;
import com.ruoyi.common.core.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表格列字段
 * @author dwj
 * @date 2024/12/3
 */
@RestController
@RequestMapping("/api/tablecolumn")
@CrossOrigin
@Slf4j
@Validated
public class TableColumnController {

    @Autowired
    private ITabFieldService tabFieldService;

    /**
     * 获取表格列字段
     * @return
     */
    @GetMapping("/getTableColumnList")
    public AjaxResult getTableColumnList (@NotBlank(message = "模型编码不能为空") String modelCode,
                                       @NotBlank(message = "tab名称不能为空") String tabName) {
        List<TableColumnDO> columnList = tabFieldService.selectTabField(modelCode, tabName);
        return AjaxResult.success(CollectionUtils.isEmpty(columnList) ? null : (JSONArray)JSON.toJSON(columnList));
    }

}
