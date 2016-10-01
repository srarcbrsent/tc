<nav class="navbar navbar-inverse" role="navigation">
    <div class="container-fluid">
        <!-- 导航头部 -->
        <div class="navbar-header">
            <!-- 品牌名称或logo -->
            <a class="navbar-brand" href="#">Tc</a>
        </div>
        <!-- 导航项目 -->
        <div class="collapse navbar-collapse navbar-collapse-example">
            <!-- 一般导航项目 -->
            <ul class="nav navbar-nav">
                <li>
                    <a href="${.vars.base}/index.html">首页</a>
                </li>
                <!-- 导航中的下拉菜单 -->
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">商品列表<b class="caret"></b></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="your/nice/url">商品</a></li>
                        <li><a href="your/nice/url">产品</a></li>
                        <li><a href="your/nice/url">书籍</a></li>
                        <li><a href="your/nice/url">杂货</a></li>
                    </ul>
                </li>
                <li>
                    <a href="#">订单列表</a>
                </li>
                <li>
                    <a href="#">收藏列表</a>
                </li>
                <li>
                    <a href="#">管理商品</a>
                </li>
                <li>
                    <a href="#">个人信息</a>
                </li>
                <li>
                    <a href="#">管理用户</a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a id="nav_signup_btn" href="${.vars.base}/account/go2signup.html">注册</a></li>
                <li><a id="nav_signin_btn" href="${.vars.base}/account/go2signin.html">登陆</a></li>
                <li><a id="nav_search_btn" href="javascript:void(0)">综合搜索</a></li>

            </ul>
        </div>
    </div>
</nav>
<style type="text/css">
    body {
        padding-top: 15px;
    }
</style>