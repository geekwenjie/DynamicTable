package com.dynamic.table.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.dynamic.table.domain.TableField;
import com.dynamic.table.enums.YesNoEnum;
import com.dynamic.table.mapper.DbTableMapper;
import com.dynamic.table.mapper.TabFieldMapper;
import com.dynamic.table.mapper.TableFieldMapper;
import com.dynamic.table.service.IDbTableService;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.generator.domain.GenTableColumn;
import com.ruoyi.system.service.ISysConfigService;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dynamic.table.mapper.TableModelMapper;
import com.dynamic.table.domain.TableModel;
import com.dynamic.table.service.ITableModelService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.util.CollectionUtils;

/**
 * 列模型Service业务层处理
 *
 * @author dwj
 * @date 2024-11-22
 */
@Service
public class TableModelServiceImpl implements ITableModelService
{
    @Autowired
    private TableModelMapper tableModelMapper;

    @Autowired
    private DbTableMapper dbTableMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private TableFieldMapper tableFieldMapper;

    @Autowired
    private TabFieldMapper tabFieldMapper;

    /**
     * 查询列模型
     *
     * @param id 列模型主键
     * @return 列模型
     */
    @Override
    public TableModel selectTableModelById(Long id)
    {
        return tableModelMapper.selectTableModelById(id);
    }

    /**
     * 查询列模型列表
     *
     * @param tableModel 列模型
     * @return 列模型
     */
    @Override
    public List<TableModel> selectTableModelList(TableModel tableModel)
    {
        return tableModelMapper.selectTableModelList(tableModel);
    }

    /**
     * 新增列模型
     *
     * @param tableModel 列模型
     * @return 结果
     */
    @Override
    public int insertTableModel(TableModel tableModel)
    {
        //判断是否重复
        List<TableModel> tableModelList = tableModelMapper.selectByModelCode(tableModel.getModelCode());
        Validate.isTrue(CollectionUtils.isEmpty(tableModelList), "模型编码已存在");
        tableModel.setCreateTime(DateUtils.getNowDate());
        int result = tableModelMapper.insertTableModel(tableModel);
        if(result > 0){
            String defaultWidth = configService.selectConfigByKey("tableModel.defaultWidth");
            //查询数据库表列数据
            List<GenTableColumn> columnList = dbTableMapper.selectDbTableColumnsByName(tableModel.getTableName());
            if(!CollectionUtils.isEmpty(columnList)){
                List<TableField> tableFieldList = columnList.stream().map(column ->{
                    TableField tableField = new TableField();
                    tableField.setFieldCode(StringUtils.toCamelCase(column.getColumnName()));
                    tableField.setFieldName(column.getColumnComment());
                    tableField.setTableModelId(tableModel.getId());
                    tableField.setWidth(Integer.parseInt(defaultWidth));
                    tableField.setShowFlag(YesNoEnum.YES.getValue());
                    tableField.setSortFlag(YesNoEnum.NO.getValue());
                    tableField.setCreateTime(DateUtils.getNowDate());
                    return tableField;
                }).collect(Collectors.toList());
                result = tableFieldMapper.batchInsertTableField(tableFieldList);
            }
        }
        return result;
    }


    /**
     * 修改列模型
     *
     * @param tableModel 列模型
     * @return 结果
     */
    @Override
    public int updateTableModel(TableModel tableModel)
    {
        //判断是否重复
        List<TableModel> tableModelList = tableModelMapper.selectByModelCode(tableModel.getModelCode());
        Validate.isTrue(CollectionUtils.isEmpty(tableModelList) || tableModelList.get(0).getId().equals(tableModel.getId()), "模型编码已存在");
        tableModel.setUpdateTime(DateUtils.getNowDate());
        //存在字段列表
        if(!CollectionUtils.isEmpty(tableModel.getFieldList())){
            //查询本地记录
            List<TableField> dbTableFieldList = tableFieldMapper.selectByModelId(tableModel.getId());
            // 转换为 Map，方便查找
            final Map<Long, TableField> fieldMap = tableModel.getFieldList().stream()
                    .filter(field -> Objects.nonNull(field.getId()))
                    .collect(Collectors.toMap(TableField::getId, field -> field));
            final Map<Long, TableField> dbFieldMap =  CollectionUtils.isEmpty(dbTableFieldList) ? null : dbTableFieldList.stream()
                    .collect(Collectors.toMap(TableField::getId, field -> field));
            // 找出删除的字段
            List<TableField> deletedFields = dbTableFieldList.stream()
                    .filter(field -> !fieldMap.containsKey(field.getId()))
                    .collect(Collectors.toList());
            // 找出更新的字段
            List<TableField> updateFields = tableModel.getFieldList().stream()
                    .filter(field -> dbFieldMap.containsKey(field.getId()))
                    .collect(Collectors.toList());
            // 找出新增的字段
            List<TableField> insertFields = tableModel.getFieldList().stream()
                    .filter(field -> Objects.isNull(field.getId()))
                    .collect(Collectors.toList());
            //检查字段编码是否重复
            List<TableField> checkFieldList = Lists.newArrayList();
            checkFieldList.addAll(updateFields);
            checkFieldList.addAll(insertFields);
            List<String> fieldCodeList = checkFieldCodeUnique(checkFieldList);
            if(!CollectionUtils.isEmpty(fieldCodeList)){
                throw new ServiceException("存在重复字段编码：" + fieldCodeList.get(0));
            }
            if(!CollectionUtils.isEmpty(deletedFields)){
                // 转换为 String[]
                String[] ids = deletedFields.stream()
                        .map(field -> String.valueOf(field.getId())) // 将 Long 转换为 String
                        .toArray(String[]::new);
                tableFieldMapper.deleteTableFieldByIds(ids);
            }
            if(!CollectionUtils.isEmpty(updateFields)){
                updateFields.forEach(field -> {
                    tableFieldMapper.updateTableField(field);
                });
            }
            if(!CollectionUtils.isEmpty(insertFields)){
                insertFields.forEach(field -> field.setCreateTime(DateUtils.getNowDate()));
                tableFieldMapper.batchInsertTableField(insertFields);
            }
        }
        return tableModelMapper.updateTableModel(tableModel);
    }


    /**
     * 校验字段编码是否唯一
     *
     * @param tableFieldList 字段集合
     */
    public List<String> checkFieldCodeUnique(List<TableField> tableFieldList) {
        // 查找重复的 fieldCode
        Map<String, Long> fieldCodeCountMap = tableFieldList.stream()
                .collect(Collectors.groupingBy(TableField::getFieldCode, Collectors.counting()));

        // 筛选出重复的 fieldCode
        List<String> duplicateNames = fieldCodeCountMap.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return duplicateNames;
    }

    /**
     * 批量删除列模型
     *
     * @param ids 需要删除的列模型主键
     * @return 结果
     */
    @Override
    public int deleteTableModelByIds(String ids)
    {
        String[] idsArray = Convert.toStrArray(ids);
        //删除表格模型字段
        tableFieldMapper.deleteByModelIds(idsArray);
        //删除tab字段
        tabFieldMapper.deleteByModelIds(idsArray);
        return tableModelMapper.deleteTableModelByIds(idsArray);
    }

    /**
     * 删除列模型信息
     *
     * @param id 列模型主键
     * @return 结果
     */
    @Override
    public int deleteTableModelById(Long id)
    {
        return tableModelMapper.deleteTableModelById(id);
    }


}
