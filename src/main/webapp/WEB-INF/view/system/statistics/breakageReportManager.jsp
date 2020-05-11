<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>报损统计</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/public.css" media="all"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui_ext/dtree/dtree.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui_ext/dtree/font/dtreefont.css">
</head>
<body class="childrenBody">

<!-- 搜索条件开始 -->
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>查询条件</legend>
</fieldset>
<form class="layui-form" method="post" id="searchFrm">

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">商品名称:</label>
            <div class="layui-input-inline" style="padding: 5px">
                <input type="text" name="itemName" autocomplete="off" class="layui-input layui-input-inline"
                       placeholder="请输入商品名称" style="height: 30px;border-radius: 10px">
            </div>
            <label class="layui-form-label">开始时间:</label>
            <div class="layui-input-inline" style="padding: 5px">
                <input type="date" name="dateS" autocomplete="off" class="layui-input layui-input-inline"
                       placeholder="请输入开始时间" style="height: 30px;border-radius: 10px">
            </div>
            <label class="layui-form-label">时间结束:</label>
            <div class="layui-input-inline" style="padding: 5px">
                <input type="date" name="dateE" autocomplete="off" class="layui-input layui-input-inline"
                       placeholder="请输入时间结束" style="height: 30px;border-radius: 10px">
            </div>
            <button type="button"
                    class="layui-btn layui-btn-normal layui-icon layui-icon-search layui-btn-radius layui-btn-sm"
                    id="doSearch" style="margin-top: 4px">查询
            </button>
            <button type="reset"
                    class="layui-btn layui-btn-warm layui-icon layui-icon-refresh layui-btn-radius layui-btn-sm" style="margin-top: 4px">重置
            </button>
        </div>
       
    </div>

</form>

<div>
    总金额: <label id="totalPrice"></label>
</div>

<!-- 数据表格开始 -->
<table class="layui-hide" id="userTable" lay-filter="userTable"></table>


<script src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
<script type="text/javascript">
    var tableIns;
    layui.use(['jquery', 'layer', 'form', 'table'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;
        //渲染数据表格
        tableIns = table.render({
            elem: '#userTable'   //渲染的目标对象
            , url: '${pageContext.request.contextPath}/statistics/breakageReport.action' //数据接口
            , title: '报损统计数据表'//数据导出来的标题
            , toolbar: "#userToolBar"   //表格的工具条
            , height: '600'
            , cellMinWidth: 100 //设置列的最小默认宽度
            , page: true  //是否启用分页
            , cols: [[   //列表数据
                {type: 'checkbox', fixed: 'left'}
                , {field: 'id', title: 'ID', align: 'center'}
                , {field: 'month', title: '日期', align: 'center'}
                , {field: 'itemName', title: '商品名称', align: 'center'}
                , {field: 'count', title: '数量', align: 'center'}
                , {field: 'price', title: '总价', align: 'center'}
            ]],
            done:function (data, curr, count) {
                var params = $("#searchFrm").serialize();
                $.post("${pageContext.request.contextPath}/statistics/breakageTotalPriceReport.action?" + params, function (res) {
                    $("#totalPrice").text(res);
                })
                //不是第一页时，如果当前返回的数据为0那么就返回上一页
                if(data.data.length==0&&curr!=1){
                    tableIns.reload({
                        page:{
                            curr:curr-1
                        }
                    })
                }
            }
        })

        //模糊查询
        $("#doSearch").click(function () {
            var params = $("#searchFrm").serialize();
            //alert(params);
            tableIns.reload({
                url: "${pageContext.request.contextPath}/statistics/breakageReport.action?" + params,
                page:{curr:1}
            })
            $.post("${pageContext.request.contextPath}/statistics/breakageTotalPriceReport.action?" + params, function (res) {
                $("#totalPrice").text(res);
            })
        });

        //监听头部工具栏事件
        table.on("toolbar(userTable)", function (obj) {
            switch (obj.event) {
                case 'add':
                    openAddUser();
                    break;
                case 'deleteBatch':
                    deleteBatch();
                    break;
            }
        });

        //监听行工具事件
        table.on('tool(userTable)', function (obj) {
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            if (layEvent === 'del') { //删除
                layer.confirm('真的删除【' + data.username + '】这个供应商么？', function (index) {
                    //向服务端发送删除指令
                    $.post("${pageContext.request.contextPath}/supplier/deleteSupplier.action", {id: data.id}, function (res) {
                        layer.msg(res.msg);
                        //刷新数据表格
                        tableIns.reload();
                    })
                });
            } else if (layEvent === 'edit') { //编辑
                //编辑，打开修改界面
                openUpdateUser(data);
            }
        });

        var url;
        var mainIndex;

        //打开添加页面
        function openAddUser() {
            mainIndex = layer.open({
                type: 1,
                title: '添加供应商',
                content: $("#saveOrUpdateDiv"),
                area: ['700px', '500px'],
                success: function (index) {
                    //清空表单数据
                    $("#dataFrm")[0].reset();
                    $("#password").removeAttr("readonly");
                    url = "${pageContext.request.contextPath}/supplier/addSupplier.action";
                }
            });
        }

        function openUpdateUser(data) {
            mainIndex = layer.open({
                type: 1,
                title: '修改供应商',
                content: $("#saveOrUpdateDiv"),
                area: ['700px', '500px'],
                success: function (index) {
                    form.val("dataFrm", data);
                    form.render();
                    url = "${pageContext.request.contextPath}/supplier/updateSupplier.action";
                }
            });
        }

        //保存
        form.on("submit(doSubmit)", function (obj) {
            //序列化表单数据
            var params = $("#dataFrm").serialize();
            $.post(url, params, function (obj) {
                layer.msg(obj.msg);
                //关闭弹出层
                layer.close(mainIndex)
                //刷新数据 表格
                tableIns.reload();
            })
        });

        //批量删除
        function deleteBatch() {
            //得到选中的数据行
            var checkStatus = table.checkStatus('userTable');
            var data = checkStatus.data;
            layer.alert(data.length);
            var params="";
            $.each(data,function(i,item){
                if (i==0){
                    params+="ids="+item.id;
                }else{
                    params+="&ids="+item.id;
                }
            });
            layer.confirm('真的要删除这些供应商么？', function (index) {
                //向服务端发送删除指令
                $.post("${pageContext.request.contextPath}/supplier/deleteBatchSupplier.action",params, function (res) {
                    layer.msg(res.msg);
                    //刷新数据表格
                    tableIns.reload();
                })
            });
        }
    });

</script>
</body>
</html>
