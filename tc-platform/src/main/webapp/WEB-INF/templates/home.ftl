<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>首页</title>
<#include "/WEB-INF/templates/common/pre-lib.ftl"/>
    <link href="${.vars.staticBase}/styles/home.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<div>
    <!-- header start -->
<#include "/WEB-INF/templates/common/header.ftl"/>
    <!-- header end -->

    <!-- container start -->
    <div id="main-container" class="layui-main">
        <fieldset class="layui-elem-field">
            <legend>主页 - 这是已经登陆状态了 骚年 -
                <a href="${base}/auth/signout.html" class="layui-btn layui-btn-normal layui-btn-small layui-btn-radius">
                    点击我登出
                </a>
            </legend>
            <div class="layui-field-box">
                <!-- create account start -->
                <fieldset class="layui-elem-field layui-field-title">
                    <legend>创建账号 - 创建账号之后就可以尽情的玩耍了</legend>
                    <div class="layui-field-box"></div>
                </fieldset>
                <!-- create account end -->

                <!-- find account start -->
                <fieldset class="layui-elem-field layui-field-title">
                    <legend>查询账号 - 账号列表一览</legend>
                    <div class="layui-field-box"></div>
                </fieldset>
                <!-- find account end -->
            </div>
        </fieldset>
    </div>


    <!-- container end -->
    <!-- footer start -->
<#include "/WEB-INF/templates/common/footer.ftl"/>
    <!-- footer end -->
</div>

<#include "/WEB-INF/templates/common/post-lib.ftl"/>
<script src="${.vars.staticBase}/scripts/home.js" type="application/javascript"></script>
</body>
</html>