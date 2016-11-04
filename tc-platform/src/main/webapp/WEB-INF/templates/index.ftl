<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tc交易平台</title>
<#include "/WEB-INF/templates/common/pre-lib.ftl"/>
    <link href="${.vars.staticBase}/styles/index.css" type="text/css" rel="stylesheet"/>
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
        <div id="carousel_div" class="col-md-10">
            <!-- carousel start -->
            <div id="myNiceCarousel" class="carousel slide" data-ride="carousel">
                <!-- 圆点指示器 -->
                <ol class="carousel-indicators">
                    <li data-target="#myNiceCarousel" data-slide-to="0" class="active"></li>
                    <li data-target="#myNiceCarousel" data-slide-to="1"></li>
                    <li data-target="#myNiceCarousel" data-slide-to="2"></li>
                </ol>

                <!-- 轮播项目 -->
                <div class="carousel-inner">
                    <div class="item active">
                        <img alt="First slide" src="http://zui.sexy/docs/img/slide1.jpg">
                        <div class="carousel-caption">
                            <h3>我是第一张幻灯片</h3>
                            <p>:)</p>
                        </div>
                    </div>
                    <div class="item">
                        <img alt="Second slide" src="http://zui.sexy/docs/img/slide2.jpg">
                        <div class="carousel-caption">
                            <h3>我是第二张幻灯片</h3>
                            <p>0.0</p>
                        </div>
                    </div>
                    <div class="item">
                        <img alt="Third slide" src="http://zui.sexy/docs/img/slide3.jpg">
                        <div class="carousel-caption">
                            <h3>我是第三张幻灯片</h3>
                            <p>最后一张咯~</p>
                        </div>
                    </div>
                </div>

                <!-- 项目切换按钮 -->
                <a class="left carousel-control" href="#myNiceCarousel" data-slide="prev">
                    <span class="icon icon-chevron-left"></span>
                </a>
                <a class="right carousel-control" href="#myNiceCarousel" data-slide="next">
                    <span class="icon icon-chevron-right"></span>
                </a>
            </div>
            <!-- carousel end -->

            <!-- hit start -->
            <div id="hit_div" class="table-responsive">
                <table class="table">
                    <tbody>
                    <tr>
                        <td style="width: 100px"><code>&lt;header&gt;</code></td>
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

            <!-- card start -->
            <div class="cards cards-borderless">
                <div class="col-lg-3">
                    <a class="card" href="#">
                        <img src="http://zui.sexy/docs/img/img2.jpg" alt="">
                        <div class="card-heading"><strong>良辰美景</strong></div>
                        <div class="card-content text-muted">良辰美景奈何天，赏心乐事谁家院。</div>
                        <div class="card-actions">
                            <button type="button" class="btn btn-danger btn-mini">加入收藏</button>
                            <div class="pull-right text-danger"><i class="icon-yen"></i> 520.00</div>
                        </div>
                    </a>
                </div>
                <div class="col-lg-3">
                    <a class="card" href="#">
                        <img src="http://zui.sexy/docs/img/img2.jpg" alt="">
                        <div class="card-heading"><strong>良辰美景</strong></div>
                        <div class="card-content text-muted">良辰美景奈何天，赏心乐事谁家院。</div>
                        <div class="card-actions">
                            <button type="button" class="btn btn-danger btn-mini">加入收藏</button>
                            <div class="pull-right text-danger"><i class="icon-yen"></i> 520.00</div>
                        </div>
                    </a>
                </div>
                <div class="col-lg-3">
                    <a class="card" href="#">
                        <img src="http://zui.sexy/docs/img/img2.jpg" alt="">
                        <div class="card-heading"><strong>良辰美景</strong></div>
                        <div class="card-content text-muted">良辰美景奈何天，赏心乐事谁家院。</div>
                        <div class="card-actions">
                            <button type="button" class="btn btn-danger btn-mini">加入收藏</button>
                            <div class="pull-right text-danger"><i class="icon-yen"></i> 520.00</div>
                        </div>
                    </a>
                </div>
                <div class="col-lg-3">
                    <a class="card" href="#">
                        <img src="http://zui.sexy/docs/img/img2.jpg" alt="">
                        <div class="card-heading"><strong>良辰美景</strong></div>
                        <div class="card-content text-muted">良辰美景奈何天，赏心乐事谁家院。</div>
                        <div class="card-actions">
                            <button type="button" class="btn btn-danger btn-mini">加入收藏</button>
                            <div class="pull-right text-danger"><i class="icon-yen"></i> 520.00</div>
                        </div>
                    </a>
                </div>
            </div>
            <!-- cart end -->

            <!-- notification start -->
            <div id="comments_div" class="comments">
                <header>
                    <h5>留言</h5>
                </header>
                <div class="comments-list">
                    <div class="comment">
                        <div class="content">
                            <div class="pull-right text-muted">2 个小时前</div>
                            <div><a href="###"><strong>Catouse</strong></a> <span class="text-muted">回复</span> <a
                                    href="###">张士超</a></div>
                            <div class="text">你到底把我家钥匙放哪里了...</div>
                            <div class="actions">
                                <a href="##">回复</a>
                                <a href="##">编辑</a>
                                <a href="##">删除</a>
                            </div>
                        </div>
                    </div>
                    <div class="comment">
                        <div class="content">
                            <div class="pull-right text-muted">2 个小时前</div>
                            <div><a href="###"><strong>Catouse</strong></a> <span class="text-muted">回复</span> <a
                                    href="###">张士超</a></div>
                            <div class="text">你到底把我家钥匙放哪里了...</div>
                            <div class="actions">
                                <a href="##">回复</a>
                                <a href="##">编辑</a>
                                <a href="##">删除</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- notification end -->
        </div>
        <!-- section end -->
    </div>
    <!-- container end -->

    <hr/>

    <!-- footer start -->
<#include "/WEB-INF/templates/common/footer.ftl"/>
    <!-- footer end -->
</div>

<#include "/WEB-INF/templates/common/post-lib.ftl"/>
<script src="${.vars.staticBase}/scripts/index.js" type="application/javascript"></script>
</body>
</html>