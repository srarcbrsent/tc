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
            <form class="am-form">
                <fieldset>
                    <legend>表单标题</legend>

                    <div class="am-form-group">
                        <label for="doc-ipt-email-1">邮件</label>
                        <div class="am-input-group am-input-group-primary">
                            <span class="am-input-group-label"><i class="am-icon-user am-icon-fw"></i></span>
                            <input type="text"
                                   class="am-form-field"
                                   placeholder="你的大名">
                        </div>
                    </div>

                    <div class="am-form-group">
                        <label for="doc-ipt-email-1">邮件</label>
                        <div class="am-input-group am-input-group-primary">
                            <span class="am-input-group-label"><i class="am-icon-user am-icon-fw"></i></span>
                            <input type="text"
                                   class="am-form-field"
                                   placeholder="你的大名">
                        </div>
                    </div>

                    <div class="am-form-group">
                        <label for="doc-ipt-email-1">邮件</label>
                        <div class="am-input-group am-input-group-primary">
                            <span class="am-input-group-label"><i class="am-icon-user am-icon-fw"></i></span>
                            <input type="text"
                                   class="am-form-field"
                                   placeholder="你的大名">
                        </div>
                    </div>

                    <div class="am-form-group">
                        <label for="doc-ipt-email-1">邮件</label>
                        <div class="am-input-group am-input-group-primary">
                            <select multiple data-am-selected>
                                <option value="a">Apple</option>
                                <option value="b">Banana</option>
                                <option value="o">Orange</option>
                                <option value="m">Mango</option>
                            </select>
                        </div>
                    </div>

                    <p>
                        <button type="submit"
                                class="am-btn am-btn-default am-disabled">提交
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