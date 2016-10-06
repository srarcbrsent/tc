<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tc交易平台</title>
<#include "/WEB-INF/templates/common/pre-lib.ftl"/>
    <link href="${.vars.staticBase}/styles/order/orders.css" type="text/css" rel="stylesheet"/>
    <link href="${.vars.staticBase}/libs/typeahead/jquery.typeahead.min.css" type="text/css" rel="stylesheet"/>
    <link href="${.vars.staticBase}/libs/zui/lib/datatable/zui.datatable.min.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<div class="container container-fixed">
    <!-- nav start -->
<#include "/WEB-INF/templates/common/header.ftl"/>
    <!-- nav end -->

    <!-- container start -->
    <div class="row">
        <!-- menu start -->
    <#include "/WEB-INF/templates/common/menus.ftl"/>
        <!-- menu end -->

        <!-- section start -->
        <div id="section_div" class="col-md-10">
            <h1>订单列表</h1>
            <hr/>

            <div class="row">
                <!-- filterbox start -->
                <div class="col-md-12">
                    <div id="filter_div" class="table-responsive">
                        <form>
                            <table class="table">
                                <tbody>
                                <tr>
                                    <td>
                                        <div class="with-padding">
                                            <div class="panel with-padding">
                                                <div class="panel-body">
                                                    <!-- hit start -->
                                                    <div id="hit_div" class="table-responsive">
                                                        <table class="table">
                                                            <tbody>
                                                            <tr>
                                                                <td>
                                                                    <code>&lt;header&gt;</code>
                                                                </td>
                                                                <td>头部</td>
                                                                <td>标题等信息</td>
                                                            </tr>
                                                            <tr>
                                                                <td></td>
                                                                <td></td>
                                                                <td></td>
                                                            </tr>
                                                            <tr>
                                                                <td><code>&lt;footer&gt;</code></td>
                                                                <td>底部</td>
                                                                <td>显示分页信息等。</td>
                                                            </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                    <!-- hit end -->
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </form>
                    </div>
                </div>
                <!-- filterbox end -->

                <!-- table start -->
                <div id="orders_div" class="col-md-12">
                    <div id="orders_header" class="row text-center">
                        <table class="col-md-12">
                            <tbody>
                            <tr>
                                <td class="col-md-5">宝贝</td>
                                <td class="col-md-2">卖家</td>
                                <td class="col-md-1">单价</td>
                                <td class="col-md-1">数量</td>
                                <td class="col-md-1">付款</td>
                                <td class="col-md-1">状态</td>
                                <td class="col-md-1">操作</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="row order_table">
                        <table class="col-md-12 order_table_header">
                            <tbody>
                            <tr>
                                <td class="col-md-4">订单号：456456123231</td>
                                <td class="col-md-4">支付号：123123123123123</td>
                                <td class="col-md-4"></td>
                            </tr>
                            </tbody>
                        </table>
                        <table class="col-md-12 order_table_body">
                            <tbody>
                            <tr>
                                <td class="col-md-5">
                                    <div class="col-md-4">
                                        <img src="http://zui.sexy/docs/img/img1.jpg"
                                             alt="圆角图片"/>
                                    </div>
                                    <div class="col-md-8">
                                        <p class="lead text-ellipsis">123123jgjkdfsdfsadfasasdfasdf123</p>
                                        <p class="text-ellipsis">fasdjdfasjlkasdfjk</p>
                                    </div>
                                </td>
                                <td class="col-md-2">支付</td>
                                <td class="col-md-1">123</td>
                                <td class="col-md-1">123</td>
                                <td class="col-md-1" rowspan="3">123</td>
                                <td class="col-md-1">123</td>
                                <td class="col-md-1" rowspan="3">
                                    <div class="btn-group btn-group-vertical">
                                        <button type="button" class="btn">付款</button>
                                        <button type="button" class="btn">发货</button>
                                        <button type="button" class="btn">收货</button>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="col-md-5">
                                    <div class="col-md-4">
                                        <img src="http://zui.sexy/docs/img/img1.jpg"
                                             alt="圆角图片"/>
                                    </div>
                                    <div class="col-md-8">
                                        <p class="lead text-ellipsis">123123jgjkdfsdfsadfasasdfasdf123</p>
                                        <p class="text-ellipsis">fasdjdfasjlkasdfjk</p>
                                    </div>
                                </td>
                                <td class="col-md-2">支付</td>
                                <td class="col-md-1">123</td>
                                <td class="col-md-1">123</td>
                                <td class="col-md-1">123</td>
                            </tr>
                            <tr>
                                <td class="col-md-5">
                                    <div class="col-md-4">
                                        <img src="http://zui.sexy/docs/img/img1.jpg"
                                             alt="圆角图片"/>
                                    </div>
                                    <div class="col-md-8">
                                        <p class="lead text-ellipsis">123123jgjkdfsdfsadfasasdfasdf123</p>
                                        <p class="text-ellipsis">fasdjdfasjlkasdfjk</p>
                                    </div>
                                </td>
                                <td class="col-md-2">支付</td>
                                <td class="col-md-1">123</td>
                                <td class="col-md-1">123</td>
                                <td class="col-md-1">123</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="row order_table">
                        <table class="col-md-12 order_table_header">
                            <tbody>
                            <tr>
                                <td class="col-md-4">订单号：456456123231</td>
                                <td class="col-md-4">支付号：123123123123123</td>
                                <td class="col-md-4"></td>
                            </tr>
                            </tbody>
                        </table>
                        <table class="col-md-12 order_table_body">
                            <tbody>
                            <tr>
                                <td class="col-md-5">
                                    <div class="col-md-4">
                                        <img src="http://zui.sexy/docs/img/img1.jpg"
                                             alt="圆角图片"/>
                                    </div>
                                    <div class="col-md-8">
                                        <p class="lead text-ellipsis">123123jgjkdfsdfsadfasasdfasdf123</p>
                                        <p class="text-ellipsis">fasdjdfasjlkasdfjk</p>
                                    </div>
                                </td>
                                <td class="col-md-2">支付</td>
                                <td class="col-md-1">123</td>
                                <td class="col-md-1">123</td>
                                <td class="col-md-1" rowspan="3">123</td>
                                <td class="col-md-1">123</td>
                                <td class="col-md-1" rowspan="3">123</td>
                            </tr>
                            <tr>
                                <td class="col-md-5">
                                    <div class="col-md-4">
                                        <img src="http://zui.sexy/docs/img/img1.jpg"
                                             alt="圆角图片"/>
                                    </div>
                                    <div class="col-md-8">
                                        <p class="lead text-ellipsis">123123jgjkdfsdfsadfasasdfasdf123</p>
                                        <p class="text-ellipsis">fasdjdfasjlkasdfjk</p>
                                    </div>
                                </td>
                                <td class="col-md-2">支付</td>
                                <td class="col-md-1">123</td>
                                <td class="col-md-1">123</td>
                                <td class="col-md-1">123</td>
                            </tr>
                            <tr>
                                <td class="col-md-5">
                                    <div class="col-md-4">
                                        <img src="http://zui.sexy/docs/img/img1.jpg"
                                             alt="圆角图片"/>
                                    </div>
                                    <div class="col-md-8">
                                        <p class="lead text-ellipsis">123123jgjkdfsdfsadfasasdfasdf123</p>
                                        <p class="text-ellipsis">fasdjdfasjlkasdfjk</p>
                                    </div>
                                </td>
                                <td class="col-md-2">支付</td>
                                <td class="col-md-1">123</td>
                                <td class="col-md-1">123</td>
                                <td class="col-md-1">123</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!-- table end -->

                <!-- pager start -->
                <div class="col-md-12 with-padding">
                    <ul class="pager pull-right">
                        <li class="previous"><a href="your/nice/url">«</a></li>
                        <li><a href="your/nice/url">1</a></li>
                        <li class="active"><a href="your/nice/url">2</a></li>
                        <li><a href="your/nice/url">3</a></li>
                        <li><a href="your/nice/url">4</a></li>
                        <li><a href="your/nice/url">5</a></li>
                        <li class="next"><a href="your/nice/url">»</a></li>
                    </ul>
                </div>
                <!-- pager end -->
            </div>
        </div>

    </div>
    <!-- container end -->

    <!-- footer start -->
<#include "/WEB-INF/templates/common/footer.ftl"/>
    <!-- footer end -->
</div>

<#include "/WEB-INF/templates/common/post-lib.ftl"/>
<script src="${.vars.staticBase}/scripts/order/orders.js" type="application/javascript"></script>
<script src="${.vars.staticBase}/libs/typeahead/jquery.typeahead.min.js" type="application/javascript"></script>
<script src="${.vars.staticBase}/libs/zui/lib/datatable/zui.datatable.min.js" type="application/javascript"></script>

</body>
</html>