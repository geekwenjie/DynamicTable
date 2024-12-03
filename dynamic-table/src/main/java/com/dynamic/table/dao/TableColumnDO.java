package com.dynamic.table.dao;

import lombok.Data;

/**
 * @author dwj
 * @date 2024/12/3
 */
@Data
public class TableColumnDO {

    /**
     * 列字段名称
     */
    private String fieldName;

    /**
     * 列字段编码
     */
    private String fieldCode;

    /**
     * 列字段是否排序（1：排序，0：不排序）
     */
    private int sortFlag;

    /**
     * 列字段宽度
     */
    private int width;

    /**
     * 列字段排序
     */
    private int fieldOrder;


}
