<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>首页</title>
<#include "/WEB-INF/templates/common/pre-lib.ftl"/>
    <link href="${.vars.staticBase}/styles/index.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<div>
    <!-- + header -->
<#include "/WEB-INF/templates/common/header.ftl"/>
    <!-- - header -->

    <!-- + container -->
    <div id="main-container" class="layui-main">
        <fieldset class="layui-elem-field">
        <#if signupErrorMsg??>
            <legend>${signupErrorMsg!"系统异常，请稍后再试！"}</legend>
        <#else>
            <legend>登陆吧 好基友 登陆后你才知道什么里面有什么</legend>
        </#if>
            <div class="layui-field-box">
                <form id="signup-form" class="layui-form layui-form-pane" action="${base}/auth/h_signup.html"
                      method="post">
                    <div class="layui-form-item">
                        <label class="layui-form-label">用户名</label>
                        <div class="layui-input-block">
                            <input type="text" name="username" required lay-verify="username" placeholder="请输入用户名"
                                   autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">密码框</label>
                        <div class="layui-input-block">
                            <input type="password" name="password" required lay-verify="password" placeholder="请输入密码"
                                   autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">记住我</label>
                        <div class="layui-input-block">
                            <input type="checkbox" name="rememberMe" lay-skin="switch">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">验证码</label>
                        <div class="layui-input-inline">
                            <input type="text" name="verificationCode" required lay-verify="verificationCode"
                                   placeholder="请输入验证码" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">${verificationCode}</div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <button class="layui-btn" lay-submit lay-filter="signup">立即提交</button>
                            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                        </div>
                    </div>
                </form>
            </div>
        </fieldset>
    </div>
    <!-- - container -->

    <!-- + footer -->
<#include "/WEB-INF/templates/common/footer.ftl"/>
    <!-- - footer -->
</div>

<#include "/WEB-INF/templates/common/post-lib.ftl"/>
<script src="${.vars.staticBase}/libs/jqplugin/jquery.md5.min.js" type="application/javascript"></script>
<script src="${.vars.staticBase}/scripts/index.js" type="application/javascript"></script>
</body>
</html>