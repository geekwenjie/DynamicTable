
<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">DynamicTable</h1>
<h4 align="center">动态表格列字段配置，快速实现table字段隐藏显示</h4>


## 系统简介

​	在日常开发中，本人经常需要处理 CMS 系统和进销存管理系统的表格功能。项目中表格字段的显示与隐藏需求变化频繁，而每次修改都需要手动调整代码并重新部署，既费时又容易出错。为了解决这一痛点，我开发了一套支持自定义表格字段管理的系统，旨在提供灵活的字段配置方式，让开发者无需改动代码即可动态调整表格字段，从而提升开发效率和用户体验。

## 功能简介

1、无需在现有系统中进行集成，只需部署 DynamicTable 系统并对接接口即可轻松实现动态表格

2、可以为同一数据库表不同的表格设置不同的列显示，灵活满足多场景需求

3、DynamicTable是基于若依系统开发的SpringBoot项目

## 案例演示


https://github.com/user-attachments/assets/cdf4e2eb-0961-4e07-86ff-4558f0e9b324



## 使用流程

1、运行系统

​	本系统基于[若依系统](https://doc.ruoyi.vip/ruoyi/document/hjbs.html "超链接title")开发，可以参考若依文档运行系统（点击可跳转）

2、修改从数据库连接，编辑`resources`目录下的`application-druid.yml`

```js
# 从库数据源
slave:
   # 从数据源开关/默认关闭
   enabled: true
   url: jdbc:mysql://localhost:3306/bussiness_db?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
   username: root
   password: 12345678
```

将数据源改为您业务系统的数据源

3、登录系统

​	默认用户名：admin 密码：123456

<img src="doc/image-20241203144314646.png" alt="image-20241203144314646" style="zoom: 20%;" />

4、新增表格模型

<img src="doc/image-20241203150951853.png" alt="image-20241203150951853" style="zoom:25%;" />



<img src="doc/image-20241203151039405.png" alt="image-20241203151039405" style="zoom:25%;" />

5、字段设置：【表格模型】列表点击编辑，切换到【字段设置】

<img src="doc/image-20241203151200134.png" alt="image-20241203151200134" style="zoom:25%;" />

（1）默认这里带出的都是数据库表中所有字段

（2）如果有不是数据库表中的字段，也可以新增自定义字段

（3）是否显示列：暂时无用

（4）是否排序列：标识字段是否可以排序

（5）默认宽度：列宽

6、列表设置

​	在您的业务系统中，同一个数据库表可能对应多个表格视图，系统允许针对每个表格视图配置不同的字段显示，提供更灵活的管理方式

<img src="doc/image-20241203164951485.png" alt="image-20241203151200134" style="zoom:25%;" />

7、第三方业务系统对接

您的业务系统可以使用如下接口对接

接口名称：查询表格字段

请求方式：GET

请求地址：/api/tablecolumn/getTableColumnList

请求参数：

| 参数名称  | 参数解释 | 是否必填 |
| --------- | -------- | -------- |
| modelCode | 模型编码 | 是       |
| tabName   | 列表名称 | 是       |

请求结果：

| 参数名称   | 参数解释                                 |
| ---------- | ---------------------------------------- |
| code       | 返回结果：0-成功，其他-失败              |
| msg        | 返回消息                                 |
| fieldName  | 字段名称                                 |
| fieldCode  | 字段编码                                 |
| width      | 宽度                                     |
| fieldOrder | 字段顺序                                 |
| sortFlag   | 是否可以排序：1：可以排序，0：不可以排序 |

返回成功案例：

```json
{
    "msg": "操作成功",
    "code": 0,
    "data": [
        {
            "fieldName": "负责人",
            "fieldCode": "leader",
            "width": 100,
            "fieldOrder": 0,
            "sortFlag": 0
        },
        {
            "fieldName": "邮箱",
            "fieldCode": "email",
            "width": 100,
            "fieldOrder": 1,
            "sortFlag": 0
        }
    ]
}
```

8、对接示例

（1）Vue2+Element UI

```js
// 获取表字段列表
export function getColumns(modelCode,tabName) {
  return request({
    url: 'http://127.0.0.1/api/tablecolumn/getTableColumnList?modelCode=' + modelCode + "&tabName=" + tabName,
    method: 'get'
  })
}

export default {
  name: "Customer",
  data() {
    return {
      // 表格列（从接口获取）
      columns: []
    };
  },
  created() {
    this.getColumns();
    this.getList();
  },
  methods: {
    /** 查询表格列字段 */
    getColumns() {
      getColumns("Customer","customerList").then(response => {
        this.columns = response.data;
      });
    },
    /** 查询客户列表 */
    getList() {
      this.loading = true;
      listCustomer(this.queryParams).then(response => {
        this.customerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    }
  }
};
```

```vue
<el-table v-loading="loading" :data="customerList" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55" align="center" />
    <!-- 动态渲染表头 -->
    <el-table-column
      v-for="(column, index) in columns"
      :key="index"
      :label="column.fieldName"
      :prop="column.fieldCode"
      :width="column.width">
    </el-table-column>
    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
      <template slot-scope="scope">
        <el-button
          size="mini"
          type="text"
          icon="el-icon-edit"
          @click="handleUpdate(scope.row)"
          v-hasPermi="['system:customer:edit']"
        >修改</el-button>
        <el-button
          size="mini"
          type="text"
          icon="el-icon-delete"
          @click="handleDelete(scope.row)"
          v-hasPermi="['system:customer:remove']"
        >删除</el-button>
      </template>
    </el-table-column>
</el-table>
```

（2）Jquery + Bootstrap 

```JS
// 获取表格列字段
$.ajax({
    url: 'http://127.0.0.1/api/tablecolumn/getTableColumnList?modelCode=Customer&tabName=customerList',  // Replace with your actual endpoint
    method: 'GET',
    success: function(response) {
        if(response.code != 0){
            $.modal.alertError(response.msg);
            return;
        }
        var columns = response.data;
        var columnDefs = [];
        columns.forEach(function(column) {
            columnDefs.push({
                field: column.fieldCode,
                title: column.fieldName,
                width: column.width,
                sortable: column.sortFlag,
            });
        });
        // 在数组的开头添加复选框
        columnDefs.unshift({
            checkbox: true
        });  // 添加到开头
        //操作列
        var extraColumnEnd =  {
            title: '操作',
            align: 'center',
            width: 100,
            formatter: function(value, row, index) {
                var actions = [];
                actions.push('<a onclick=""><i class="fa fa-edit"></i>编辑</a> ');
                actions.push('<a onclick=""><i class="fa fa-remove"></i>删除</a>');
                return actions.join('');
            }
        }
        // 在数组的最后添加操作列
        columnDefs.push(extraColumnEnd); // 添加到末尾
        var options = {
            url: prefix + "/list",
            createUrl: prefix + "/add",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            exportUrl: prefix + "/export",
            modalName: "客户",
            columns: columnDefs,
        };
        $.table.init(options);
    },
    error: function() {
        alert('Failed to load columns.');
    }
});
```

```html
 <table id="bootstrap-table"></table>
```

## 联系方式

如果您有任何问题或建议，欢迎联系：

- **微信**: deng775747758 （请备注：DynamicTable）
- **Email**: 775747758@qq.com
