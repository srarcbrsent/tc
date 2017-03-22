<!DOCTYPE html>
<html lang="en">
<head>
    <title>首页 - 这只是一个实验工程而已</title>
<#include "commons/header_lib.ftl">
    <link href="/tc-static/src/resources/styles/home.css"
          type="text/css"
          rel="stylesheet"/>
</head>
<body>
<#-- + wrapper -->
<div class="global-wrapper">
    <#-- + header -->
<#include "commons/header.ftl">
    <#-- - header -->

    <#-- + body -->
    <div class="layui-main body-block">
        <div class="layui-tab layui-tab-brief">
            <div class="layui-tab-content">
                <#-- + body left -->
                <div class="home-body-left">
                <#include "commons/menu.ftl">
                </div>
                <#-- - body left -->

                <#-- + body right -->
                <div class="home-body-right">
                    <div class="home-content-title">
                        <p>你好世界</p>
                    </div>
                    <div class="home-content-body"></div>
                </div>
                <#-- - body right -->
            </div>
        </div>
    </div>
    <#-- - body -->

    <#-- + footer -->
<#include "commons/footer.ftl">
    <#-- - footer -->
</div>
<#-- - wrapper -->

<#include "commons/footer_lib.ftl">
<script src="/tc-static/src/resources/scripts/home.js"
        type="application/javascript"></script>
</body>
</html>