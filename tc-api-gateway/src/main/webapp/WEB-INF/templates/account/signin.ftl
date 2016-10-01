<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tc交易平台</title>
<#include "/WEB-INF/templates/common/pre-lib.ftl"/>
    <link href="${.vars.staticBase}/styles/account/signin.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<div class="container container-fixed">
    <!-- nav start -->
<#include "/WEB-INF/templates/common/header.ftl"/>
    <!-- nav end -->

    <!-- container start -->
    <div class="row">
        <!-- signin font start -->
        <h1 class="text-center">登陆</h1>
        <!-- signin font end -->

        <hr/>
        <div class="col-md-6">
            // TODO barcode
        </div>
        <div id="signin_div" class="col-md-6">
            <!-- form start -->
            <form id="signin_form">
                <fieldset>
                    <legend>基本信息</legend>

                    <div class="form-group">
                        <label for="signin_account">账号</label>
                        <input type="text" class="form-control" id="signin_account" placeholder="输入用来注册的账号">
                    </div>
                    <div class="form-group">
                        <label for="signin_password">密码</label>
                        <input type="password" class="form-control" id="signin_password" placeholder="输入用来注册的密码">
                    </div>
                    <div class="form-group">
                        <label for="signin_password">验证码</label>
                        <input type="password" class="form-control" id="signin_password" placeholder="输入用来注册的密码">
                        <img id="signin_verification_code_img" class="img-thumbnail"
                             src="http://zui.sexy/docs/img/slide1.jpg"/>
                    </div>
                    <div class="bg-primary with-padding">
                        <font>密码错误！！！！！</font>
                    </div>
                </fieldset>
                <hr/>
                <button type="submit" class="btn btn-primary">登陆</button>
            </form>
            <!-- form end -->
        </div>
    </div>
    <!-- container end -->

    <hr/>

    <!-- footer start -->
<#include "/WEB-INF/templates/common/footer.ftl"/>
    <!-- footer end -->
</div>



<#include "/WEB-INF/templates/common/post-lib.ftl"/>
<script src="${.vars.staticBase}/libs/jqplugin/jquery.validate.min.js" type="application/javascript"></script>
<script src="${.vars.staticBase}/scripts/account/signin.js" type="application/javascript"></script>
</body>
</html>