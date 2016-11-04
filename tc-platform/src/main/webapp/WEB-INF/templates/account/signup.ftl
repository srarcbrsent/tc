<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tc交易平台</title>
<#include "/WEB-INF/templates/common/pre-lib.ftl"/>
    <link href="${.vars.staticBase}/styles/account/signup.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<div class="container container-fixed">
    <!-- nav start -->
<#include "/WEB-INF/templates/common/header.ftl"/>
    <!-- nav end -->

    <!-- container start -->
    <div class="row">
        <!-- signin font start -->
        <h1 class="text-center">注册</h1>
        <!-- signin font end -->
        <hr/>

        <div class="col-md-2"></div>
        <div id="signin_div" class="col-md-8">

            <!-- form start -->
            <form id="signin_form">
                <fieldset>
                    <legend>基本信息</legend>

                    <div class="form-group">
                        <label for="signin_account">账号</label>
                        <input type="text" class="form-control" id="signin_account" placeholder="输入用来注册的账号">
                        <div class="help-block">账号可以是6-16位大小写字母和数字的组合</div>
                    </div>
                    <div class="form-group">
                        <label for="signin_password">密码</label>
                        <input type="password" class="form-control" id="signin_password" placeholder="输入用来注册的密码">
                        <div class="help-block">密码可以是6-22位大小写字母和数组的组合</div>
                    </div>
                </fieldset>

                <fieldset>
                    <legend>可选信息</legend>

                    <div class="form-group">
                        <label for="signin_password">姓名</label>
                        <input type="password" class="form-control" id="signin_password" placeholder="输入用来注册的密码">
                        <div class="help-block">密码可以是6-22位大小写字母和数组的组合</div>
                    </div>
                    <div class="form-group">
                        <label for="signin_account">地址</label>
                        <div class="row row-fluid">
                            <div class="col-md-2">
                                <select class="form-control">
                                    <option value="">请选择一种水果</option>
                                    <option value="apple">苹果</option>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <select class="form-control">
                                    <option value="">请选择一种水果</option>
                                    <option value="apple">苹果</option>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <select class="form-control">
                                    <option value="">请选择一种水果</option>
                                    <option value="apple">苹果</option>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <input type="password" class="form-control" id="signin_password"
                                       placeholder="输入用来注册的密码">
                            </div>
                        </div>
                        <div class="help-block">账号可以是6-16位大小写字母和数字的组合</div>
                    </div>
                    <div class="form-group">
                        <label for="signin_account">性别</label>
                        <div class="radio">
                            <label>
                                <input type="radio" name="radioOptionsExample"> 单选框 1
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input type="radio" name="radioOptionsExample"> 单选框 2
                            </label>
                        </div>
                        <div class="help-block">账号可以是6-16位大小写字母和数字的组合</div>
                    </div>
                    <div class="form-group">
                        <label for="signin_account">手机</label>
                        <input type="text" class="form-control" id="signin_account" placeholder="输入用来注册的账号">
                        <div class="help-block">账号可以是6-16位大小写字母和数字的组合</div>
                    </div>
                    <div class="form-group">
                        <label for="signin_account">邮箱</label>
                        <input type="text" class="form-control" id="signin_account" placeholder="输入用来注册的账号">
                        <div class="help-block">账号可以是6-16位大小写字母和数字的组合</div>
                    </div>
                    <p>注册成功后可以在个人信息页面补充可选信息</p>
                </fieldset>
                <hr/>
                <button type="submit" class="btn btn-primary">提交</button>
                <button type="reset" class="btn">重置</button>
            </form>
            <!-- form end -->
            <hr/>
            <a href="javascript:void(0)">已有账号？直接登陆</a>
        </div>
        <div class="col-md-1"></div>
    </div>
    <!-- container end -->

    <hr/>

    <!-- footer start -->
<#include "/WEB-INF/templates/common/footer.ftl"/>
    <!-- footer end -->
</div>



<#include "/WEB-INF/templates/common/post-lib.ftl"/>
<script src="${.vars.staticBase}/libs/jqplugin/jquery.validate.min.js" type="application/javascript"></script>
<script src="${.vars.staticBase}/scripts/account/signup.js" type="application/javascript"></script>
</body>
</html>