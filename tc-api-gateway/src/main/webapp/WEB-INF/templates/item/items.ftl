<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tc交易平台</title>
<#include "/WEB-INF/templates/common/pre-lib.ftl"/>
    <link href="${.vars.staticBase}/styles/item/items.css" type="text/css" rel="stylesheet"/>
    <link href="${.vars.staticBase}/libs/typeahead/jquery.typeahead.min.css" type="text/css" rel="stylesheet"/>
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
            <h1>商品列表</h1>
            <hr/>

            <!-- filter start -->
            <div id="filter_div" class="table-responsive">
                <form>
                    <table class="table">
                        <tbody>
                        <tr>
                            <td>
                                <div class="with-padding">
                                    <div class="form-group">
                                        <label for="exampleInputAccount7" class="col-sm-2">商品名/卖家名</label>
                                        <form id="form-country_v1" name="form-country_v1">
                                            <div class="typeahead__container">
                                                <div class="typeahead__field">
                                                <span class="typeahead__query">
                                                    <input class="js-typeahead-country_v1"
                                                           name="country_v1[query]"
                                                           type="search"
                                                           placeholder="Search"
                                                           autocomplete="off"/>
                                                </span>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div id="filterbox_div">
                                    <div class="col-md-12 with-padding">
                                        <div class="col-md-1">
                                            <h6>品牌：</h6>
                                        </div>
                                        <div id="filterbox_brand_div" class="col-md-11">
                                            <button class="btn" type="button">主要3按钮</button>
                                            <button class="btn" type="button">主要f按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要fsd按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按fsd钮</button>
                                            <button class="btn" type="button">主要asdf按钮</button>
                                            <button class="btn" type="button">主要a按钮</button>
                                        </div>
                                    </div>
                                    <div class="col-md-12 with-padding">
                                        <div class="col-md-1">
                                            <h6>品牌：</h6>
                                        </div>
                                        <div id="filterbox_category_div" class="col-md-11">
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                            <button class="btn" type="button">主要按钮</button>
                                        </div>
                                    </div>
                                    <div class="col-md-12 with-padding">
                                        <div class="col-md-1">
                                            <h6>品牌：</h6>
                                        </div>
                                        <div class="col-md-11">
                                            <button id="filterbox_sort_price" class="btn" type="button">
                                                <i class="icon icon-double-angle-down"></i> 价格降序
                                            </button>
                                            <button id="filterbox_sort_name" class="btn" type="button">
                                                <i class="icon icon-double-angle-down"></i> 名称降序
                                            </button>
                                            <button id="filterbox_only_in_stock" class="btn" type="button">
                                                <i class="icon icon-filter"></i> 全部显示
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="with-padding">
                                    <div class="col-md-4"></div>
                                    <div class="col-md-2">
                                        <div>
                                            <button class="btn btn-block btn-primary" type="button">
                                                <i class="icon icon-search"></i> 搜索
                                            </button>
                                        </div>
                                    </div>
                                    <div class="col-md-2">
                                        <div>
                                            <button id="filterbox_reset" class="btn btn-block" type="button">
                                                <i class="icon icon-refresh"></i> 清空
                                            </button>
                                        </div>
                                    </div>
                                    <div class="col-md-4"></div>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </form>
            </div>
            <!-- filter end -->

            <!-- items start -->
            <div id="items_div" class="cards cards-condensed">
                <div class="col-md-4 col-sm-6 col-lg-3">
                    <a class="card" href="#">
                        <img src="http://zui.sexy/docs/img/img2.jpg" alt="">
                        <div class="card-heading"><strong>良辰美景</strong></div>
                        <div class="card-content text-muted">良辰美景奈何天，赏心乐事谁家院。</div>
                        <div class="card-content text-muted">张耀武售</div>
                        <div class="card-actions">
                            <div class="btn-group">
                                <button type="button" class="btn btn-primary btn-mini">立即购买</button>
                                <button type="button" class="btn btn-primary btn-mini">加入收藏</button>
                            </div>
                            <div class="pull-right text-danger"><i class="icon-yen"></i>
                                <strong class="items_price">520.00</strong>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="col-md-4 col-sm-6 col-lg-3">
                    <a class="card" href="#">
                        <img src="http://zui.sexy/docs/img/img2.jpg" alt="">
                        <div class="card-heading"><strong>良辰美景</strong></div>
                        <div class="card-content text-muted">良辰美景奈何天，赏心乐事谁家院。</div>
                        <div class="card-content text-muted">张耀武售</div>
                        <div class="card-actions">
                            <div class="btn-group">
                                <button type="button" class="btn btn-primary btn-mini">立即购买</button>
                                <button type="button" class="btn btn-primary btn-mini">加入收藏</button>
                            </div>
                            <div class="pull-right text-danger"><i class="icon-yen"></i>
                                <strong class="items_price">520.00</strong>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="col-md-4 col-sm-6 col-lg-3">
                    <a class="card" href="#">
                        <img src="http://zui.sexy/docs/img/img2.jpg" alt="">
                        <div class="card-heading"><strong>良辰美景</strong></div>
                        <div class="card-content text-muted">良辰美景奈何天，赏心乐事谁家院。</div>
                        <div class="card-content text-muted">张耀武售</div>
                        <div class="card-actions">
                            <div class="btn-group">
                                <button type="button" class="btn btn-primary btn-mini">立即购买</button>
                                <button type="button" class="btn btn-primary btn-mini">加入收藏</button>
                            </div>
                            <div class="pull-right text-danger"><i class="icon-yen"></i>
                                <strong class="items_price">520.00</strong>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="col-md-4 col-sm-6 col-lg-3">
                    <a class="card" href="#">
                        <img src="http://zui.sexy/docs/img/img2.jpg" alt="">
                        <div class="card-heading"><strong>良辰美景</strong></div>
                        <div class="card-content text-muted">良辰美景奈何天，赏心乐事谁家院。</div>
                        <div class="card-content text-muted">张耀武售</div>
                        <div class="card-actions">
                            <div class="btn-group">
                                <button type="button" class="btn btn-primary btn-mini">立即购买</button>
                                <button type="button" class="btn btn-primary btn-mini">加入收藏</button>
                            </div>
                            <div class="pull-right text-danger"><i class="icon-yen"></i>
                                <strong class="items_price">520.00</strong>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="col-md-4 col-sm-6 col-lg-3">
                    <a class="card" href="#">
                        <img src="http://zui.sexy/docs/img/img2.jpg" alt="">
                        <div class="card-heading"><strong>良辰美景</strong></div>
                        <div class="card-content text-muted">良辰美景奈何天，赏心乐事谁家院。</div>
                        <div class="card-content text-muted">张耀武售</div>
                        <div class="card-actions">
                            <div class="btn-group">
                                <button type="button" class="btn btn-primary btn-mini">立即购买</button>
                                <button type="button" class="btn btn-primary btn-mini">加入收藏</button>
                            </div>
                            <div class="pull-right text-danger"><i class="icon-yen"></i>
                                <strong class="items_price">520.00</strong>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="col-md-4 col-sm-6 col-lg-3">
                    <a class="card" href="#">
                        <img src="http://zui.sexy/docs/img/img2.jpg" alt="">
                        <div class="card-heading"><strong>良辰美景</strong></div>
                        <div class="card-content text-muted">良辰美景奈何天，赏心乐事谁家院。</div>
                        <div class="card-content text-muted">张耀武售</div>
                        <div class="card-actions">
                            <div class="btn-group">
                                <button type="button" class="btn btn-primary btn-mini">立即购买</button>
                                <button type="button" class="btn btn-primary btn-mini">加入收藏</button>
                            </div>
                            <div class="pull-right text-danger"><i class="icon-yen"></i>
                                <strong class="items_price">520.00</strong>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="col-md-4 col-sm-6 col-lg-3">
                    <a class="card" href="#">
                        <img src="http://zui.sexy/docs/img/img2.jpg" alt="">
                        <div class="card-heading"><strong>良辰美景</strong></div>
                        <div class="card-content text-muted">良辰美景奈何天，赏心乐事谁家院。</div>
                        <div class="card-content text-muted">张耀武售</div>
                        <div class="card-actions">
                            <div class="btn-group">
                                <button type="button" class="btn btn-primary btn-mini">立即购买</button>
                                <button type="button" class="btn btn-primary btn-mini">加入收藏</button>
                            </div>
                            <div class="pull-right text-danger"><i class="icon-yen"></i>
                                <strong class="items_price">520.00</strong>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="col-md-4 col-sm-6 col-lg-3">
                    <a class="card" href="#">
                        <img src="http://zui.sexy/docs/img/img2.jpg" alt="">
                        <div class="card-heading"><strong>良辰美景</strong></div>
                        <div class="card-content text-muted">良辰美景奈何天，赏心乐事谁家院。</div>
                        <div class="card-content text-muted">张耀武售</div>
                        <div class="card-actions">
                            <div class="btn-group">
                                <button type="button" class="btn btn-primary btn-mini">立即购买</button>
                                <button type="button" class="btn btn-primary btn-mini">加入收藏</button>
                            </div>
                            <div class="pull-right text-danger"><i class="icon-yen"></i>
                                <strong class="items_price">520.00</strong>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="col-md-4 col-sm-6 col-lg-3">
                    <a class="card" href="#">
                        <img src="http://zui.sexy/docs/img/img2.jpg" alt="">
                        <div class="card-heading"><strong>良辰美景</strong></div>
                        <div class="card-content text-muted">良辰美景奈何天，赏心乐事谁家院。</div>
                        <div class="card-content text-muted">张耀武售</div>
                        <div class="card-actions">
                            <div class="btn-group">
                                <button type="button" class="btn btn-primary btn-mini">立即购买</button>
                                <button type="button" class="btn btn-primary btn-mini">加入收藏</button>
                            </div>
                            <div class="pull-right text-danger"><i class="icon-yen"></i>
                                <strong class="items_price">520.00</strong>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="col-md-4 col-sm-6 col-lg-3">
                    <a class="card" href="#">
                        <img src="http://zui.sexy/docs/img/img2.jpg" alt="">
                        <div class="card-heading"><strong>良辰美景</strong></div>
                        <div class="card-content text-muted">良辰美景奈何天，赏心乐事谁家院。</div>
                        <div class="card-content text-muted">张耀武售</div>
                        <div class="card-actions">
                            <div class="btn-group">
                                <button type="button" class="btn btn-primary btn-mini">立即购买</button>
                                <button type="button" class="btn btn-primary btn-mini">加入收藏</button>
                            </div>
                            <div class="pull-right text-danger"><i class="icon-yen"></i>
                                <strong class="items_price">520.00</strong>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="col-md-4 col-sm-6 col-lg-3">
                    <a class="card" href="#">
                        <img src="http://zui.sexy/docs/img/img2.jpg" alt="">
                        <div class="card-heading"><strong>良辰美景</strong></div>
                        <div class="card-content text-muted">良辰美景奈何天，赏心乐事谁家院。</div>
                        <div class="card-content text-muted">张耀武售</div>
                        <div class="card-actions">
                            <div class="btn-group">
                                <button type="button" class="btn btn-primary btn-mini">立即购买</button>
                                <button type="button" class="btn btn-primary btn-mini">加入收藏</button>
                            </div>
                            <div class="pull-right text-danger"><i class="icon-yen"></i>
                                <strong class="items_price">520.00</strong>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="col-md-4 col-sm-6 col-lg-3">
                    <a class="card" href="#">
                        <img src="http://zui.sexy/docs/img/img2.jpg" alt="">
                        <div class="card-heading"><strong>良辰美景</strong></div>
                        <div class="card-content text-muted">良辰美景奈何天，赏心乐事谁家院。</div>
                        <div class="card-content text-muted">张耀武售</div>
                        <div class="card-actions">
                            <div class="btn-group">
                                <button type="button" class="btn btn-primary btn-mini">立即购买</button>
                                <button type="button" class="btn btn-primary btn-mini">加入收藏</button>
                            </div>
                            <div class="pull-right text-danger"><i class="icon-yen"></i>
                                <strong class="items_price">520.00</strong>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
            <!-- items end -->

            <!-- pager start -->
            <ul class="pager pull-right">
                <li class="previous"><a href="your/nice/url">«</a></li>
                <li><a href="your/nice/url">1</a></li>
                <li class="active"><a href="your/nice/url">2</a></li>
                <li><a href="your/nice/url">3</a></li>
                <li><a href="your/nice/url">4</a></li>
                <li><a href="your/nice/url">5</a></li>
                <li class="next"><a href="your/nice/url">»</a></li>
            </ul>
            <!-- pager end -->
        </div>
        <!-- section end -->
    </div>
    <!-- container end -->

    <!-- footer start -->
<#include "/WEB-INF/templates/common/footer.ftl"/>
    <!-- footer end -->
</div>

<#include "/WEB-INF/templates/common/post-lib.ftl"/>
<script src="${.vars.staticBase}/scripts/item/items.js" type="application/javascript"></script>
<script src="${.vars.staticBase}/libs/typeahead/jquery.typeahead.min.js" type="application/javascript"></script>

</body>
</html>