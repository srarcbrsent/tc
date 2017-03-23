<!DOCTYPE html>
<html lang="en">
<head>
    <title>首页 - 这只是一个实验工程而已</title>
<#include "commons/header_lib.ftl">
    <link href="/tc-static/src/resources/styles/home.css"
          type="text/css"
          rel="stylesheet"/>
</head>
<body>
<#-- + wrapper -->
<div class="global-wrapper">
<#-- + header -->
<#include "commons/header.ftl">
<#-- - header -->

<#-- + body -->
    <div class="layui-main body-block">
        <div class="layui-tab layui-tab-brief">
            <div class="layui-tab-content">
            <#-- + body left -->
                <div class="home-body-left">
                <#include "commons/menu.ftl">
                </div>
            <#-- - body left -->

            <#-- + body right -->
                <div class="home-body-right">
                    <div class="home-content-title">
                        <p>关于我</p>
                    </div>
                    <div class="home-content-body">
                        <form class="layui-form about-me-form">
                            <div class="home-about-me-left">
                                <img class="about-me-avatar"
                                     src="https://i1.hdslb.com/bfs/face/3286300e9f74dbdf68213fe996d2203b3261aa66.jpg"/>
                                <input type="file" name="avatar" class="layui-upload-file about-me-avatar">
                            </div>
                            <div class="home-about-me-right">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">昵&nbsp;&nbsp;&nbsp;&nbsp;称</label>
                                    <div class="layui-input-inline about-me-nickname">
                                        <input type="text"
                                               name="nickname"
                                               required
                                               lay-verify="required"
                                               placeholder="请输入邮箱"
                                               autocomplete="off"
                                               class="layui-input about-me-nickname">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">邮&nbsp;&nbsp;&nbsp;&nbsp;箱</label>
                                    <div class="layui-input-inline about-me-email">
                                        <input type="text"
                                               name="email"
                                               required
                                               lay-verify="required"
                                               placeholder="请输入邮箱"
                                               autocomplete="off"
                                               class="layui-input about-me-email">
                                    </div>
                                    <div class="layui-form-mid">-</div>
                                    <div class="layui-input-inline about-me-active-email">
                                        <div class="layui-btn-group">
                                            <a href="javascript:void(0)" class="layui-btn">邮箱已激活</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">手&nbsp;&nbsp;&nbsp;&nbsp;机</label>
                                    <div class="layui-input-inline about-me-mobile">
                                        <input type="text"
                                               name="mobile"
                                               required
                                               lay-verify="required"
                                               placeholder="请输入手机"
                                               autocomplete="off"
                                               class="layui-input about-me-mobile">
                                    </div>
                                    <div class="layui-form-mid">-</div>
                                    <div class="layui-input-inline about-me-active-mobile">
                                        <div class="layui-btn-group">
                                            <a href="javascript:void(0)" class="layui-btn">手机已激活</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">地&nbsp;&nbsp;&nbsp;&nbsp;址</label>
                                    <div class="layui-input-inline about-me-province">
                                        <select name="province"
                                                lay-verify="required">
                                            <option value="">请选择省</option>
                                            <option value="0">北京</option>
                                            <option value="1">上海</option>
                                            <option value="2">广州</option>
                                            <option value="3">深圳</option>
                                            <option value="4">杭州</option>
                                        </select>
                                    </div>
                                    <div class="layui-form-mid">-</div>
                                    <div class="layui-input-inline about-me-city">
                                        <select name="city" lay-verify="required">
                                            <option value="">请选择市</option>
                                            <option value="0">北京</option>
                                            <option value="1">上海</option>
                                            <option value="2">广州</option>
                                            <option value="3">深圳</option>
                                            <option value="4">杭州</option>
                                        </select>
                                    </div>
                                    <div class="layui-form-mid">-</div>
                                    <div class="layui-input-inline about-me-district">
                                        <select name="district" lay-verify="required">
                                            <option value="">请选择区</option>
                                            <option value="0">北京</option>
                                            <option value="1">上海</option>
                                            <option value="2">广州</option>
                                            <option value="3">深圳</option>
                                            <option value="4">杭州</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">锁&nbsp;&nbsp;&nbsp;&nbsp;定</label>
                                    <div class="layui-input-inline about-me-locked">
                                        <input type="checkbox" name="switch" lay-skin="switch" disabled/>
                                    </div>
                                </div>
                                <div class="layui-form-item layui-form-text">
                                    <label class="layui-form-label">介&nbsp;&nbsp;&nbsp;&nbsp;绍</label>
                                    <div class="layui-input-inline about-me-desc">
                                        <textarea name="desc" placeholder="请输入内容" class="layui-textarea"></textarea>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">角&nbsp;&nbsp;&nbsp;&nbsp;色</label>
                                    <div class="layui-input-inline about-me-permissions">
                                        <input type="checkbox" name="like[write]" title="写作" disabled>
                                        <input type="checkbox" name="like[read]" title="阅读" checked disabled>
                                        <input type="checkbox" name="like[dai]" title="发呆" disabled>
                                        <input type="checkbox" name="like[write]" title="写作" disabled>
                                        <input type="checkbox" name="like[read]" title="阅读" checked disabled>
                                        <input type="checkbox" name="like[dai]" title="发呆" disabled>
                                        <input type="checkbox" name="like[write]" title="写作" disabled>
                                        <input type="checkbox" name="like[read]" title="阅读" checked disabled>
                                        <input type="checkbox" name="like[dai]" title="发呆" disabled>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-input-block">
                                        <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
                                        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <div class="layui-clear"></div>
                    </div>
                </div>
            <#-- - body right -->

            <#-- + clear both -->
                <div class="layui-clear"></div>
            <#-- - clear both -->
            </div>
        </div>
    </div>
<#-- - body -->

<#-- + footer -->
<#include "commons/footer.ftl">
<#-- - footer -->
</div>
<#-- - wrapper -->

<#include "commons/footer_lib.ftl">
<script src="/tc-static/src/resources/scripts/home.js"
        type="application/javascript"></script>
</body>
</html>