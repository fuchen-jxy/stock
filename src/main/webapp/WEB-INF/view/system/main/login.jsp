<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class="loginHtml">
<head>
    <meta charset="utf-8">
    <title>进销存管理信息系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="icon" href="${pageContext.request.contextPath}/static/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/public.css" media="all"/>
</head>
<body class="loginBody">
<form class="layui-form" id="loginFrm" method="post" action="${pageContext.request.contextPath}/login/login.action">
    <div class="login_face"><img src="${pageContext.request.contextPath}/static/images/face.jpg" class="userAvatar">
    </div>
    <div class="layui-form-item input-item">
        <label for="loginname">用户名</label>
        <input type="text" placeholder="请输入用户名" autocomplete="off" name="username" id="loginname" class="layui-input"
               lay-verify="required">
    </div>
    <div class="layui-form-item input-item">
        <label for="pwd">密码</label>
        <input type="password" placeholder="请输入密码" autocomplete="off" name="password" id="pwd" class="layui-input"
               lay-verify="required">
    </div>
    <div class="layui-form-item input-item" id="imgCode">
        <label for="code">验证码</label>
        <input type="text" placeholder="请输入验证码" autocomplete="off" name="code" id="code" class="layui-input">
        <img src="${pageContext.request.contextPath}/login/getCode.action" onclick="this.src=this.src+'?'">
    </div>
    <div class="layui-form-item">
        <button class="layui-btn layui-block" lay-filter="login" lay-submit>登录</button>
    </div>
<%--    <div class="layui-form-item">--%>
<%--        <button class="layui-btn layui-block" lay-filter="toReg" id="toReg"><a id="toRegA" href="${pageContext.request.contextPath}/login/toReg.action">注册</a></button>--%>
<%--    </div>--%>
    <div class="layui-form-item layui-row" style="text-align: center;color: red;">
        ${error}
    </div>
</form>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/cache.js"></script>
<script type="text/javascript">
    layui.use(['form', 'layer', 'jquery'], function () {
        var form = layui.form,
            layer = parent.layer === undefined ? layui.layer : top.layer
        $ = layui.jquery;

        //登录按钮
        form.on("submit(login)", function (data) {
            $(this).text("登录中...").attr("disabled", "disabled").addClass("layui-disabled");
            setTimeout(function () {
                $("#loginFrm").submit();
            }, 1000);
            return false;
        });

        form.on("submit(toReg)", function (data) {
            $(this).text("注册中...").attr("disabled", "disabled").addClass("layui-disabled");
            $("#toRegA").trigger("click");
            return false;
        });

        //注册按钮
        $('#toReg').click(function(){
            $("#toRegA").trigger("click");
            return false;
        });

        //表单输入效果
        $(".loginBody .input-item").click(function (e) {
            e.stopPropagation();
            $(this).addClass("layui-input-focus").find(".layui-input").focus();
        })
        $(".loginBody .layui-form-item .layui-input").focus(function () {
            $(this).parent().addClass("layui-input-focus");
        })
        $(".loginBody .layui-form-item .layui-input").blur(function () {
            $(this).parent().removeClass("layui-input-focus");
            if ($(this).val() != '') {
                $(this).parent().addClass("layui-input-active");
            } else {
                $(this).parent().removeClass("layui-input-active");
            }
        })
    })

</script>
</body>
</html>
