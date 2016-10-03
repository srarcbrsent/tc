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
                                                                <td style="width: 100px"><code>&lt;header&gt;</code>
                                                                </td>
                                                                <td style="width: 80px">头部</td>
                                                                <td>标题等信息</td>
                                                            </tr>
                                                            <tr>
                                                                <td><code>.items</code></td>
                                                                <td>列表项组</td>
                                                                <td>可以在 <code>.list</code> 内堆叠多个列表项组。</td>
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
                <div class="col-md-12">
                    <table id="orders_table" class="table datatable">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>时间</th>
                            <th>评分</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>123</td>
                            <td>123</td>
                            <td>123</td>
                        </tr>
                        <tr>
                            <td>123</td>
                            <td>123</td>
                            <td>123</td>
                        </tr>
                        </tbody>
                    </table>
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