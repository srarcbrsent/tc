<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>错误</title>
<#include "/WEB-INF/templates/common/pre-lib.ftl"/>
    <link href="${.vars.staticBase}/styles/error_page/error_page.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<div>
    <!-- header start -->
<#include "/WEB-INF/templates/common/header.ftl"/>
    <!-- header end -->

    <!-- container start -->
    <div id="main-container" class="layui-main">
        <div id="menu_div">
        <#include "/WEB-INF/templates/menu.ftl"/>
        </div>

        <div id="body_div">
            <fieldset class="layui-elem-field">
                <legend>
                    主页 - 这是已经登陆状态了 骚年
                </legend>
                <div class="layui-field-box">

                </div>
            </fieldset>
        </div>
        <div class="layui-clear"></div>
    </div>
    <!-- container end -->

    <!-- footer start -->
<#include "/WEB-INF/templates/common/footer.ftl"/>
    <!-- footer end -->
</div>

<#include "/WEB-INF/templates/common/post-lib.ftl"/>
<script src="${.vars.staticBase}/scripts/error_page/error_page.js" type="application/javascript"></script>
</body>
</html>