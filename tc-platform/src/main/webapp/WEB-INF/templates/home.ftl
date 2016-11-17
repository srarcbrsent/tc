<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>主页</title>
<#include "/WEB-INF/templates/common/pre-lib.ftl"/>
    <link href="${.vars.staticBase}/styles/home.css" type="text/css" rel="stylesheet"/>
</head>
<body>
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
                    <!-- create account start -->
                    <fieldset class="layui-elem-field layui-field-title">
                        <legend>创建账号 - 创建账号之后就可以尽情的玩耍了 - 只有拥有创建账号权限的用户可见表单</legend>
                        <div class="layui-field-box">
                            <form id="signin_form" class="layui-form" action="">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">名字</label>
                                    <div class="layui-input-block">
                                        <input type="text"
                                               name="title"
                                               required
                                               lay-verify="required"
                                               placeholder="请输入标题"
                                               autocomplete="off"
                                               class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">账号</label>
                                    <div class="layui-input-block">
                                        <input type="text"
                                               name="title"
                                               required
                                               lay-verify="required"
                                               placeholder="请输入标题"
                                               autocomplete="off"
                                               class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">邮箱</label>
                                    <div class="layui-input-block">
                                        <input type="text" name="title"
                                               required
                                               lay-verify="required"
                                               placeholder="请输入标题"
                                               autocomplete="off"
                                               class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">电话</label>
                                    <div class="layui-input-block">
                                        <input type="text"
                                               name="title"
                                               required
                                               lay-verify="required"
                                               placeholder="请输入标题"
                                               autocomplete="off"
                                               class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">密码</label>
                                    <div class="layui-input-block">
                                        <input type="password"
                                               name="title"
                                               required
                                               lay-verify="required"
                                               placeholder="请输入标题"
                                               autocomplete="off"
                                               class="layui-input">
                                    </div>
                                </div>
                            </form>
                            <div class="layui-form-item">
                                <label class="layui-form-label">头像</label>
                                <div class="layui-input-block">
                                    <input type="file"
                                           name="file（可随便定义）"
                                           class="layui-upload-file">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-input-block">
                                    <button class="layui-btn"
                                            lay-submit
                                            lay-filter="formDemo"
                                            form="signin_form">
                                        立即提交
                                    </button>
                                    <button type="reset"
                                            class="layui-btn layui-btn-primary"
                                            form="signin_form">
                                        重置
                                    </button>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <!-- create account end -->
                </div>
            </fieldset>
        </div>
        <div class="layui-clear"></div>
    </div>
    <!-- container end -->

    <!-- footer start -->
<#include "/WEB-INF/templates/common/footer.ftl"/>
    <!-- footer end -->

<#include "/WEB-INF/templates/common/post-lib.ftl"/>
<script src="${.vars.staticBase}/scripts/home.js" type="application/javascript"></script>
</body>
</html>