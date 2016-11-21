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
    <!-- + header -->
<#include "/WEB-INF/templates/common/header.ftl"/>
    <!-- - header -->

    <!-- + container -->
    <div id="main-container" class="layui-main">
        <!-- + menu -->
        <div id="menu_div">
        <#include "/WEB-INF/templates/menu.ftl"/>
        </div>
        <!-- - menu -->

        <!-- + body -->
        <div id="body_div">
            <fieldset class="layui-elem-field">
                <legend>
                    主页 - 这是已经登陆状态了 骚年
                </legend>
                <div class="layui-field-box">
                    <!-- + create account -->
                    <fieldset class="layui-elem-field layui-field-title">
                        <legend>创建账号 - 创建账号之后就可以尽情的玩耍了 - 只有拥有创建账号权限的用户可见表单</legend>
                        <div class="layui-field-box">
                            <form id="signin_form" class="layui-form" action="">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">*&nbsp;名字</label>
                                    <div class="layui-input-block">
                                        <input type="text"
                                               name="name"
                                               required
                                               lay-verify="required"
                                               placeholder="请输入名字"
                                               autocomplete="off"
                                               class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">*&nbsp;账号</label>
                                    <div class="layui-input-block">
                                        <input type="text"
                                               name="account"
                                               lay-verify="required"
                                               placeholder="请输入账号"
                                               autocomplete="off"
                                               class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">邮箱</label>
                                    <div class="layui-input-block">
                                        <input type="email"
                                               name="email"
                                               lay-verify="required"
                                               placeholder="请输入邮箱"
                                               autocomplete="off"
                                               class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">电话</label>
                                    <div class="layui-input-block">
                                        <input type="tel"
                                               name="mobile"
                                               lay-verify="required"
                                               placeholder="请输入手机号码"
                                               autocomplete="off"
                                               class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">*&nbsp;密码</label>
                                    <div class="layui-input-block">
                                        <input type="password"
                                               name="title"
                                               required
                                               lay-verify="required"
                                               placeholder="请输入密码"
                                               autocomplete="off"
                                               class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">*&nbsp;地区</label>
                                    <div class="layui-input-inline">
                                        <select name="_province" lay-verify="">
                                            <option value="">请选择一个省</option>
                                            <option value="010">北京</option>
                                            <option value="021">上海</option>
                                            <option value="0571">杭州</option>
                                        </select>
                                    </div>
                                    <div class="layui-input-inline">
                                        <select name="_city" lay-verify="">
                                            <option value="">请选择一个市</option>
                                            <option value="010">北京</option>
                                            <option value="021">上海</option>
                                            <option value="0571">杭州</option>
                                        </select>
                                    </div>
                                    <div class="layui-input-inline">
                                        <select name="region" lay-verify="">
                                            <option value="">请选择一个区</option>
                                            <option value="010">北京</option>
                                            <option value="021">上海</option>
                                            <option value="0571">杭州</option>
                                        </select>
                                    </div>
                                </div>
                                <input type="hidden" name="avatar"/>
                            </form>
                            <div class="layui-form-item">
                                <label class="layui-form-label">头像</label>
                                <div class="layui-input-block">
                                    <input type="file"
                                           name="file"
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
                    <!-- - create account -->
                </div>
            </fieldset>
        </div>
        <!-- - body -->
        <div class="layui-clear"></div>
    </div>
    <!-- - container -->

    <!-- + footer -->
<#include "/WEB-INF/templates/common/footer.ftl"/>
    <!-- - footer -->

<#include "/WEB-INF/templates/common/post-lib.ftl"/>
<script src="${.vars.staticBase}/scripts/home.js" type="application/javascript"></script>
</body>
</html>