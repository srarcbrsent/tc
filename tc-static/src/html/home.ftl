<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>实验工程</title>
<#include "/WEB-INF/templates/commons/pre-lib.ftl"/>
    <link href="${staticBase}/styles/home.css"
          type="text/css"
          rel="stylesheet"/>
</head>
<body>
<!-- + header -->
<#include "/WEB-INF/templates/commons/header.ftl"/>
<!-- - header -->

<!-- + body -->
<div id="body"
     class="am-container">
    <!-- + menu -->
    <div id="doc-menu-div"
         class="am-u-lg-3">
    <#include "/WEB-INF/templates/commons/menu.ftl"/>
    </div>
    <!-- - menu -->

    <!-- + container -->
    <div id="doc-container-div"
         class="am-u-lg-9">
        <ul class="am-list am-list-static am-list-border">
            <li>...</li>
            <li>...</li>
            <li>...</li>
            <li>...</li>
            <li>...</li>
            <li>...</li>
            <li>...</li>
        </ul>
    </div>
    <!-- - container -->
</div>
<!-- - body -->

<!-- + footer -->
<#include "/WEB-INF/templates/commons/footer.ftl"/>
<!-- - footer -->

<#include "/WEB-INF/templates/commons/post-lib.ftl"/>
<script src="${staticBase}/scripts/home.js"
        type="application/javascript"></script>
</body>
</html>