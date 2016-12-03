<!DOCTYPE html>
<html lang="en">
<head>
    <title>首页</title>
<#include "/WEB-INF/templates/commons/pre-lib.ftl"/>
    <link href="${.vars.staticBase}/styles/accounts/signin.css"
          type="text/css"
          rel="stylesheet"/>
</head>
<body>
<div>
    <!-- + header -->
<#include "/WEB-INF/templates/commons/header.ftl"/>
    <!-- - header -->

    <!-- + body -->
    <div id="body"
         class="am-container">
        <!-- + signin form -->
        <div id="doc-signin-div"
             class="am-u-lg-12">
            <form id="doc-signin-form"
                  class="am-form">
                <fieldset>
                    <legend>注册</legend>

                    <div class="am-form-group">
                        <label>昵称</label>
                        <div class="am-input-group am-input-group-secondary">
                            <span class="am-input-group-label"><i class="am-icon-user am-icon-fw"></i></span>
                            <input id="doc-signup-username"
                                   type="text"
                                   class="am-form-field"
                                   name="username"
                                   placeholder="请输入您的账号">
                        </div>
                    </div>

                    <div class="am-form-group">
                        <label>账号</label>
                        <div class="am-input-group am-input-group-secondary">
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
                        <div class="am-input-group am-input-group-secondary">
                            <span class="am-input-group-label"><i class="am-icon-user am-icon-fw"></i></span>
                            <input id="doc-signup-username"
                                   type="text"
                                   class="am-form-field"
                                   name="username"
                                   placeholder="请输入您的账号">
                        </div>
                    </div>

                    <div class="am-form-group">
                        <label>区域</label>
                        <div class="am-input-group am-input-group-secondary">
                            <div id="doc-regions">
                                <select data-am-selected="{maxHeight: 200}"
                                        placeholder="请选择一个省">
                                    <option value=""></option>
                                    <option v-bind:value="province.code"
                                            v-for="province in provinces">
                                        {{ province.name }}
                                    </option>
                                </select>
                                <select data-am-selected="{maxHeight: 200}"
                                        placeholder="请选择一个市">
                                    <option value=""></option>
                                    <option v-bind:value="city.code"
                                            v-for="city in cities">
                                        {{ city.name }}
                                    </option>
                                </select>
                                <select data-am-selected="{maxHeight: 200}"
                                        placeholder="请选择一个区">
                                    <option value=""></option>
                                    <option v-bind:value="district.code"
                                            v-for="district in districts">
                                        {{ district.name }}
                                    </option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <p>
                        <button type="button"
                                class="am-btn am-btn-default am-btn-secondary">
                            提交
                        </button>
                        <button type="button"
                                class="am-btn am-btn-default">
                            重置
                        </button>
                    </p>
                </fieldset>
            </form>
        </div>
        <!-- - signin form -->
    </div>
    <!-- - body -->

    <!-- + footer -->
<#include "/WEB-INF/templates/commons/footer.ftl"/>
    <!-- - footer -->
</div>

<#include "/WEB-INF/templates/commons/post-lib.ftl"/>
<script src="${.vars.staticBase}/libs/jquery.md5.min.js"
        type="application/javascript"></script>
<script src="${.vars.staticBase}/scripts/commons/region.js"
        type="application/javascript"></script>
<script src="${.vars.staticBase}/scripts/accounts/signin.js"
        type="application/javascript"></script>
</body>
</html>