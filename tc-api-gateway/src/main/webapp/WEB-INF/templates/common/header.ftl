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
                    <a href="${.vars.base}/item/p/items.html">商品列表</a>
                </li>
                <li>
                    <a href="${.vars.base}/order/p/orders.html">订单列表</a>
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
                <li><a id="nav_signup_btn" href="${.vars.base}/account/p/signup.html">注册</a></li>
                <li><a id="nav_signin_btn" href="${.vars.base}/account/p/signin.html">登陆</a></li>
            </ul>
        </div>
    </div>
</nav>
<style type="text/css">
    body {
        padding-top: 15px;
    }
</style>
<script type="text/javascript">
    $(document).ready(function () {
        // totop logic
        $("<div id='to_top_div'><img src='http://static.tc.com/images/totop.png'></div>").appendTo('body');
        $('#to_top_div').css({
            width: '50px',
            height: '50px',
            bottom: '10px',
            right: '15px',
            position: 'fixed',
            cursor: 'pointer',
            zIndex: '999999'
        });
        if ($(this).scrollTop() == 0) {
            $('#to_top_div').hide();
        }
        $(window).scroll(function (event) {
            /* Act on the event */
            if ($(this).scrollTop() == 0) {
                $('#to_top_div').hide();
            }
            if ($(this).scrollTop() != 0) {
                $('#to_top_div').show();
            }
        });
        $('#to_top_div').click(function (event) {
            /* Act on the event */
            $("html,body").animate({
                        scrollTop: "0px"
                    },
                    666
            );
        });
    });
</script>