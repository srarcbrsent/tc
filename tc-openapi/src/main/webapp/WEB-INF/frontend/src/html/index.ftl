<!DOCTYPE html>
<html lang="en">
<head>
    <title>首页 - 这只是一个实验工程而已</title>
<#include "commons/header_lib.ftl">
    <link href="/tc-static/src/resources/styles/index.css"
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
                <div class="index-body-left">
                    <img src="/tc-static/src/resources/images/miku_trapeze.png"/>
                </div>
                <#-- - body left -->

                <#-- + body right -->
                <div class="index-body-right">
                    <fieldset class="layui-elem-field">
                        <legend>登陆</legend>
                        <div class="layui-field-box">
                            <form class="layui-form layui-form-pane index-login-fm">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">用户名</label>
                                    <div class="layui-input-block">
                                        <input type="text"
                                               name="username"
                                               lay-verify="username"
                                               placeholder="邮箱或手机"
                                               autocomplete="off"
                                               class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">密&nbsp;&nbsp;&nbsp;码</label>
                                    <div class="layui-input-block">
                                        <input type="password"
                                               name="password"
                                               lay-verify="password"
                                               placeholder="请输入密码"
                                               autocomplete="off"
                                               class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item layui-inline">
                                    <label class="layui-form-label">验证码</label>
                                    <div class="layui-input-inline" style="width: 175px;">
                                        <input type="text"
                                               name="verificationCode"
                                               placeholder="请输入右侧的验证码"
                                               autocomplete="off"
                                               class="layui-input">
                                    </div>
                                    <div class="layui-form-mid">-</div>
                                    <div class="layui-input-inline" style="width: 175px;">
                                        <div class="layui-btn-group">
                                            <a class="layui-btn layui-btn-primary verification-code-btn">
                                                ------
                                            </a>
                                            <a class="layui-btn layui-btn-primary refresh-verification-code-btn">
                                                <i class="layui-icon">&#x1002;</i>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">记住我</label>
                                    <div class="layui-input-block">
                                        <input type="checkbox" name="rememberMe" lay-skin="switch"/>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-input-block">
                                        <button type="submit"
                                                class="layui-btn"
                                                lay-submit
                                                lay-filter="fm-login">
                                            登陆
                                        </button>
                                        <button type="reset"
                                                class="layui-btn layui-btn-primary">
                                            重置
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </fieldset>
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
<script src="/tc-static/src/resources/libs/thirdparty/jsencrypt.min.js"
        type="application/javascript"></script>
<script src="/tc-static/src/resources/scripts/index.js"
        type="application/javascript"></script>
</body>
</html>