<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tc交易平台</title>
<#include "/WEB-INF/templates/common/pre-lib.ftl"/>
    <link href="${.vars.staticBase}/styles/item/item.css" type="text/css" rel="stylesheet"/>
    <link href="${.vars.staticBase}/libs/thumbnail/thumbnail.min.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<div class="container container-fixed">
    <!-- nav start -->
<#include "/WEB-INF/templates/common/header.ftl"/>
    <!-- nav end -->

    <!-- container start -->
    <div class="row">
        <!-- section a start -->
        <div id="section_a_div" class="col-md-4">
            <center>
                <div id="preview" class="spec-preview">
                    <span class="jqzoom">
                        <img jqimg="file:///home/zhangyaowu/%E4%B8%8B%E8%BD%BD/images/b1.jpg"
                             src="file:///home/zhangyaowu/%E4%B8%8B%E8%BD%BD/images/b1.jpg"/>
                    </span>
                </div>
                <!--缩图开始-->
                <div class="spec-scroll"><a class="prev">&lt;</a> <a class="next">&gt;</a>
                    <div class="items">
                        <ul>
                            <li>
                                <img alt="佳能"
                                     bimg="http://zui.sexy/docs/img/img1.jpg"
                                     src="http://zui.sexy/docs/img/img1.jpg"
                                     onmousemove="preview(this);">
                            </li>
                            <li>
                                <img alt="佳能"
                                     bimg="http://zui.sexy/docs/img/img2.jpg"
                                     src="http://zui.sexy/docs/img/img2.jpg"
                                     onmousemove="preview(this);">
                            </li>
                            <li>
                                <img alt="佳能"
                                     bimg="http://zui.sexy/docs/img/img3.jpg"
                                     src="http://zui.sexy/docs/img/img3.jpg"
                                     onmousemove="preview(this);">
                            </li>
                            <li>
                                <img alt="佳能"
                                     bimg="http://zui.sexy/docs/img/img4.jpg"
                                     src="http://zui.sexy/docs/img/img4.jpg"
                                     onmousemove="preview(this);">
                            </li>
                            <li>
                                <img alt="佳能"
                                     bimg="http://zui.sexy/docs/img/img5.jpg"
                                     src="http://zui.sexy/docs/img/img5.jpg"
                                     onmousemove="preview(this);">
                            </li>
                            <li>
                                <img alt="佳能"
                                     bimg="http://zui.sexy/docs/img/img5.jpg"
                                     src="http://zui.sexy/docs/img/img5.jpg"
                                     onmousemove="preview(this);">
                            </li>
                            <li>
                                <img alt="佳能"
                                     bimg="http://zui.sexy/docs/img/img5.jpg"
                                     src="http://zui.sexy/docs/img/img5.jpg"
                                     onmousemove="preview(this);">
                            </li>
                        </ul>
                    </div>
                </div>
                <!--缩图结束-->
            </center>
        </div>
        <!-- section a end -->

        <!-- section b start -->
        <div id="section_b_div" class="col-md-8">
            <!-- section ba start -->
            <div id="section_ba_div" class="col-md-6">
                <!-- hit start -->
                <div id="hit_div" class="table-responsive">
                    <table class="table">
                        <tbody>
                        <tr>
                            <td>
                                <h1>商品名</h1>
                                <small>商品描述</small>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="col-md-6">
                                        <h6>原价：<i class="icon icon-yen"></i>500.00</h6>
                                    </div>
                                    <div class="col-md-6">
                                        <h6>平台价：<i class="icon icon-yen"></i>500.00</h6>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="col-md-6">
                                        <h6>数量：</h6>
                                    </div>
                                    <div class="col-md-6">
                                        <h6>单位：</h6>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="col-md-12">
                                        <h6>支付方式：在线 / 线下</h6>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="col-md-12">
                                        <h6>配送方式：在线 / 线下</h6>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <!-- hit end -->
            </div>
            <!-- section ba end -->

            <!-- section bb start -->
            <div id="section_bb_div" class="col-md-6">
                <!-- hit start -->
                <div id="hit_div" class="table-responsive">
                    <table class="table">
                        <tbody>
                        <tr>
                            <td>
                                <h1>卖家名</h1>
                                <small>卖家描述</small>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="col-md-12">
                                        <h6>联系方式：123123123</h6>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="col-md-12">
                                        <h6>卖家总销量：123123123</h6>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="col-md-12">
                                        <h6>卖家好评度：15%</h6>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <!-- hit end -->
            </div>
            <!-- section bb end -->

            <!-- section bc start -->
            <div id="section_bc_div" class="col-md-12">
                <!-- hit start -->
                <div id="hit_div" class="table-responsive">
                    <table class="table">
                        <tbody>
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="col-md-12">
                                        <p>
                                            <i class="icon icon-tag"></i> 优惠一：123123123
                                        </p>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="col-md-12">
                                        <p>
                                            <i class="icon icon-tag"></i> 优惠一：123123123
                                        </p>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="col-md-12">
                                        <p>
                                            <i class="icon icon-tag"></i> 优惠一：123123123
                                        </p>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="col-md-12">
                                        <p>
                                            <i class="icon icon-leaf"></i> 平台承诺：正品保障，七天无理由退货
                                        </p>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="col-md-12">
                                        <p>
                                            <i class="icon icon-leaf"></i> 提醒：此商品为代购服务，不支持7天无理由退货
                                        </p>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="col-md-12">
                                        <div class="btn-group">
                                            <button class="btn btn-primary">
                                                <i class="icon icon-share-alt"></i> 立即购买
                                            </button>
                                            <button class="btn">
                                                <i class="icon icon-heart-empty"></i> 加入收藏
                                            </button>
                                            <button class="btn btn-primary">
                                                <i class="icon icon-shopping-cart"></i> 加入购物车
                                            </button>
                                            <button class="btn btn-primary">
                                                <i class="icon icon-shopping-cart"></i> 联系卖家
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <!-- hit end -->
            </div>
            <!-- section bc end -->
        </div>
    </div>
    <!-- container end -->

    <!-- section c start -->
    <div class="row with-padding">
        <ul class="nav nav-tabs">
            <li class="active"><a href="###" data-target="#tab2Content1" data-toggle="tab">商品详情</a></li>
            <li><a href="###" data-target="#tab2Content2" data-toggle="tab">买家评价</a></li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane fade active in" id="tab2Content1">
                <p>我是标签1。</p>
            </div>
            <div class="tab-pane fade" id="tab2Content2">
                <p>标签2的内容。</p>
            </div>
        </div>
    </div>
    <!-- section c end -->

    <!-- footer start -->
<#include "/WEB-INF/templates/common/footer.ftl"/>
    <!-- footer end -->
</div>

<#include "/WEB-INF/templates/common/post-lib.ftl"/>
<script src="${.vars.staticBase}/libs/jqplugin/jquery.jqzoom.min.js" type="application/javascript"></script>
<script src="${.vars.staticBase}/libs/thumbnail/thumbnail.min.js" type="application/javascript"></script>
<script src="${.vars.staticBase}/scripts/item/item.js" type="application/javascript"></script>
</body>
</html>