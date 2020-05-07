<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class="loginHtml">
<head>
    <meta charset="utf-8">
    <title>注册--视频系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="icon" href="${pageContext.request.contextPath}/static/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/public.css" media="all" />
</head>
<body class="loginBody">
<form class="layui-form" id="regFrm" method="post" action="${pageContext.request.contextPath}/login/register.action" style="height: 450px;">
    <div class="login_face"><img src="${pageContext.request.contextPath}/static/images/face.jpg" class="userAvatar"></div>
    <div class="layui-form-item input-item">
        <label for="username">用户名</label>
        <input type="text" placeholder="请输入用户名" autocomplete="off" name="username" id="username" class="layui-input" lay-verify="required">
    </div>
    <div class="layui-form-item input-item">
        <label for="password">密码</label>
        <input type="password" placeholder="请输入密码" autocomplete="off" name="password" id="password" class="layui-input" lay-verify="required">
    </div>
    <div class="layui-form-item input-item">
        <label for="repassword">重复密码</label>
        <input type="password" placeholder="请输入密码" autocomplete="off" name="repassword" id="repassword" class="layui-input" lay-verify="required">
    </div>
    <div class="layui-form-item input-item">
        <label for="phone">手机号码</label>
        <input type="tel" placeholder="请输入手机号码" autocomplete="off" name="phone" id="phone" class="layui-input" lay-verify="required">
    </div>
    <div class="layui-form-item input-item">
        <label for="name">昵称</label>
        <input type="text" placeholder="请输入昵称" autocomplete="off" name="name" id="name" class="layui-input" lay-verify="required">
    </div>
    <div class="layui-form-item input-item">
        <label for="age">年龄</label>
        <input type="number" placeholder="请输入年龄" autocomplete="off" name="age" id="age" class="layui-input" lay-verify="required">
    </div>
    <div class="layui-form-item">
        <button class="layui-btn layui-block" lay-filter="reg" lay-submit>注册</button>
    </div>
    <div class="layui-form-item layui-row" style="text-align: center;color: red;">
        ${error}
    </div>
</form>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/cache.js"></script>
<script type="text/javascript">
    layui.use(['form','layer','jquery'],function(){
        var form = layui.form,
            layer = parent.layer === undefined ? layui.layer : top.layer
        $ = layui.jquery;

        form.on("submit(reg)", function(data) {
            $(this).text("注册中...").attr("disabled","disabled").addClass("layui-disabled");
            var password = $("#password").val();
            var repassword = $("#repassword").val();
            if (password != repassword) {
                alert('两边密码不一致');
                $("#regFrm").reset();
            } else {
                $("#regFrm").submit();
            }
        });

        //表单输入效果
        $(".loginBody .input-item").click(function(e){
            e.stopPropagation();
            $(this).addClass("layui-input-focus").find(".layui-input").focus();
        })
        $(".loginBody .layui-form-item .layui-input").focus(function(){
            $(this).parent().addClass("layui-input-focus");
        })
        $(".loginBody .layui-form-item .layui-input").blur(function(){
            $(this).parent().removeClass("layui-input-focus");
            if($(this).val() != ''){
                $(this).parent().addClass("layui-input-active");
            }else{
                $(this).parent().removeClass("layui-input-active");
            }
        })
    })

</script>
</body>
</html>
