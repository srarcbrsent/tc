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

<!-- + container -->
<div id="main-container"
     class="layui-main">
    <!-- + menu -->
    <div id="menu_div">
    <#include "/WEB-INF/templates/commons/menu.ftl"/>
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
                        <form id="signin_form"
                              class="layui-form layui-form-pane"
                              action="">
                            <div class="layui-form-item">
                                <label class="layui-form-label">*&nbsp;名字</label>
                                <div class="layui-input-block">
                                    <input type="text"
                                           name="name"
                                           placeholder="请输入名字"
                                           autocomplete="off"
                                           class="layui-input">
                                    <label class="jv_error"
                                           for="name">名字为必填字段，规则为6-16位中英文数字或下划线。</label>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">*&nbsp;账号</label>
                                <div class="layui-input-block">
                                    <input type="text"
                                           name="account"
                                           placeholder="请输入账号"
                                           autocomplete="off"
                                           class="layui-input">
                                    <label class="jv_error"
                                           for="account">账号为必填字段，规则为6-16位中英文数字或下划线。</label>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">邮箱</label>
                                <div class="layui-input-block">
                                    <input type="email"
                                           name="email"
                                           placeholder="请输入邮箱"
                                           autocomplete="off"
                                           class="layui-input">
                                    <label class="jv_error"
                                           for="account">邮箱为选填字段。</label>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">手机号码</label>
                                <div class="layui-input-block">
                                    <input type="tel"
                                           name="mobile"
                                           placeholder="请输入手机号码"
                                           autocomplete="off"
                                           class="layui-input">
                                    <label class="jv_error"
                                           for="account">手机号码为选填字段，形如18888888888。</label>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">*&nbsp;密码</label>
                                <div class="layui-input-block">
                                    <input type="password"
                                           name="password"
                                           placeholder="请输入密码"
                                           autocomplete="off"
                                           class="layui-input">
                                    <label class="jv_error"
                                           for="account">密码为必填字段，规则为6-16位英文数字或下划线。</label>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">*&nbsp;地区</label>
                                <div class="layui-input-inline">
                                    <select name="province"
                                            lay-filter="signin_province">
                                        <option value="">请选择一个省</option>
                                    </select>
                                    <label class="jv_error">区域为必填字段。</label>
                                </div>
                                <div class="layui-input-inline">
                                    <select name="city"
                                            lay-filter="signin_city">
                                        <option value="">请选择一个市</option>
                                    </select>
                                </div>
                                <div class="layui-input-inline">
                                    <select name="district"
                                            lay-filter="signin_district">
                                        <option value="">请选择一个区</option>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-input-block">
                                    <button type="submit"
                                            class="layui-btn"
                                            lay-submit
                                            lay-filter="signin_form"
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
                            <input type="hidden"
                                   name="region"/>
                            <input type="hidden"
                                   name="avatar"/>
                        </form>
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
<#include "/WEB-INF/templates/commons/footer.ftl"/>
<!-- - footer -->

<#include "/WEB-INF/templates/commons/post-lib.ftl"/>
<script src="${staticBase}/libs/jqplugin/jquery.validate.min.js"
        type="application/javascript"></script>
<script src="${staticBase}/libs/jqplugin/additional-methods.min.js"
        type="application/javascript"></script>
<script src="${staticBase}/scripts/common/region.js"
        type="application/javascript"></script>
<script src="${staticBase}/scripts/home.js"
        type="application/javascript"></script>
</body>
</html>