<!DOCTYPE html>
<html lang="en">
<head>
    <title>首页</title>
<#include "/WEB-INF/templates/common/pre-lib.ftl"/>
    <link href="${.vars.staticBase}/styles/index.css"
          type="text/css"
          rel="stylesheet"/>
</head>
<body>
<div>
    <!-- + header -->
<#include "/WEB-INF/templates/common/header.ftl"/>
    <!-- - header -->

    <!-- + body -->
    <div id="body"
         class="am-container">
        <!-- + signup form -->
        <div class="am-u-lg-12">
            <form id="doc-signup-form"
                  class="am-form">
                <fieldset>
                    <legend>登陆</legend>

                    <div class="am-form-group">
                        <label>账号</label>
                        <div class="am-input-group"
                             v-bind:class="usernameValidClass">
                            <span class="am-input-group-label"><i class="am-icon-user am-icon-fw"></i></span>
                            <input id="doc-signup-username"
                                   type="text"
                                   class="am-form-field"
                                   name="username"
                                   placeholder="请输入您的账号">
                        </div>
                    </div>

                    <div class="am-form-group">
                        <label>密码</label>
                        <div class="am-input-group"
                             v-bind:class="passwordValidClass">
                            <span class="am-input-group-label"><i class="am-icon-lock am-icon-fw"></i></span>
                            <input id="doc-signup-password"
                                   type="password"
                                   class="am-form-field"
                                   name="password"
                                   placeholder="请输入您的密码">
                        </div>
                    </div>

                    <div class="am-form-group">
                        <label>验证码</label>
                        <div class="am-input-group"
                             v-bind:class="verificationCodeValidClass">
                            <span class="am-input-group-label"><i class="am-icon-gg am-icon-fw"></i></span>
                            <input id="doc-signup-verification-code"
                                   type="text"
                                   class="am-form-field"
                                   name="verificationCode"
                                   placeholder="请输入右方的验证码">
                            <span class="am-input-group-label doc-verification-code-span">
                                <span>{{ hiddenModel.verificationCode }}</span>
                                <i v-on:click="reloadVerificationCode"
                                   class="am-icon-refresh am-icon-fw"></i>
                            </span>
                        </div>
                    </div>

                    <div class="am-form-group">
                        <label>是否记住密码?</label>
                        <div class="am-input-group am-input-group-primary">
                            <select id="doc-signup-remeber-me"
                                    data-am-selected
                                    name="rememberMe">
                                <option selected
                                        value="false">
                                    不记住密码
                                </option>
                                <option value="true">
                                    记住密码
                                </option>
                            </select>
                        </div>
                    </div>

                    <p>
                        <button type="submit"
                                class="am-btn am-btn-default"
                                v-bind:class="formInValidClass">提交
                        </button>
                    </p>
                </fieldset>
            </form>
        </div>
        <!-- - signup form -->
    </div>
    <!-- - body -->

    <!-- + footer -->
<#include "/WEB-INF/templates/common/footer.ftl"/>
    <!-- - footer -->
</div>

<#include "/WEB-INF/templates/common/post-lib.ftl"/>
<script src="${.vars.staticBase}/libs/jquery.md5.min.js"
        type="application/javascript"></script>
<script src="${.vars.staticBase}/scripts/index.js"
        type="application/javascript"></script>
</body>
</html>