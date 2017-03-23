// --- npm init -> package.json
// [npm install --save-dev gulp] -> 核心
// [npm install --save-dev gulp del] -> 删除
// [npm install --save-dev gulp-if] -> 条件
// [npm install --save-dev gulp-filter] -> 过滤器
// [npm install --save-dev run-sequence] -> 顺序执行
// [npm install --save-dev jshint] -> JS校验核心
// [npm install --save-dev gulp-jshint] -> JS校验
// [npm install --save-dev gulp-csslint] -> CSS校验
// [npm install --save-dev gulp-file-include] -> HTML合并
// [npm install --save-dev gulp-replace] -> 替换文本
// [npm install --save-dev gulp-htmlmin] -> 压缩HTML
// [npm install --save-dev gulp-concat] -> 合并JS
// [npm install --save-dev gulp-uglify] -> 压缩JS
// [npm install --save-dev gulp-concat-css] -> 合并CSS
// [npm install --save-dev gulp-clean-css] -> 压缩CSS
// [npm install --save-dev gulp-rev] -> 给静态资源加MD5
// [npm install --save-dev gulp-rev-collector] -> 替换静态资源引用
// [npm install --save-dev gulp-connect] -> 静态资源服务器

var gulp = require('gulp');
var runSequence = require('run-sequence');
var connect = require('gulp-connect');
var del = require('del');
var gulpif = require('gulp-if');
var filter = require('gulp-filter');
var jshint = require('gulp-jshint');
var csslint = require('gulp-csslint');
var fileInclude = require('gulp-file-include');
var replace = require('gulp-replace');
var htmlmin = require('gulp-htmlmin');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var concatCss = require('gulp-concat-css');
var cleanCSS = require('gulp-clean-css');
var rev = require('gulp-rev');
var revCollector = require('gulp-rev-collector');


var _environment = {
    dev: false,
    htmlBase: 'http://www.tc.com',
    staticBase: 'http://static.tc.com',
    openApiBase: 'http://www.tc.com',
    port: 80
};

// 默认开发模式下编译
gulp.task('default', ['local-dev'], function () {
});

gulp.task('local-dev', function (cb) {
    _environment.dev = true;
    _environment.htmlBase = 'http://www.tc.com';
    _environment.openApiBase = 'http://www.tc.com';
    _environment.staticBase = 'http://static.tc.com';
    task(cb);
});

gulp.task('local-pdu', function (cb) {
    _environment.dev = false;
    _environment.htmlBase = 'http://www.tc.com';
    _environment.openApiBase = 'http://www.tc.com';
    _environment.staticBase = 'http://static.tc.com';
    task(cb);
});

gulp.task('remote-pdu', function (cb) {
    _environment.dev = false;
    _environment.htmlBase = 'http://www.tc.com';
    _environment.openApiBase = 'http://www.tc.com';
    _environment.staticBase = 'http://static.tc.com';
    task(cb);
});

var task = function (cb) {
    runSequence(
        'clean',
        'javascript',
        'stylesheet',
        'mg-script-libs',
        'mg-style-libs',
        'libs-static',
        'libs-other',
        'images',
        'html',
        'error-pages',
        'special',
        cb);
};

// 清理工作空间
gulp.task('clean', function (cb) {
    return del([
        'dist/**',
        'manifests/**'
    ], cb);
});

// 编译javascript
gulp.task('javascript', function () {
    return gulp
        .src('src/resources/scripts/**/*.js')
        // 静态检查
        .pipe(jshint())
        .pipe(jshint.reporter('default'))
        // 替换静态资源前缀路径
        .pipe(replace(/\/tc-static\/src\/resources/g, _environment.staticBase))
        // 压缩(开发不压缩)
        .pipe(gulpif(!_environment.dev, uglify()))
        // 加版本号
        .pipe(rev())
        // 输出
        .pipe(gulp.dest('dist/resources/scripts'))
        // 生成 manifest
        .pipe(rev.manifest())
        .pipe(gulp.dest('manifest/scripts'));
});

// 编译stylesheet
gulp.task('stylesheet', function () {
    return gulp
        .src('src/resources/styles/**/*.css')
        // 静态检查
        .pipe(csslint())
        .pipe(csslint.formatter('compact'))
        // 替换静态资源前缀路径
        .pipe(replace(/\/tc-static\/src\/resources/g, _environment.staticBase))
        // 压缩(开发不压缩)
        .pipe(gulpif(!_environment.dev, cleanCSS()))
        // 加版本号
        .pipe(rev())
        // 输出
        .pipe(gulp.dest('dist/resources/styles'))
        // 生成 manifest
        .pipe(rev.manifest())
        .pipe(gulp.dest('manifest/styles'));
});

// 合并javascript-lib
gulp.task('mg-script-libs', function (cb) {
    del('src/resources/libs/_header_lib.min.js', cb);
    return gulp
        .src([
            'src/resources/libs/core/jquery-3.2.0.min.js',
            'src/resources/libs/thirdparty/axios.min.js',
            'src/resources/libs/thirdparty/lodash.min.js'
        ])
        .pipe(concat('_footer_lib.min.js'))
        .pipe(gulp.dest('src/resources/libs'));
});

// 合并stylesheet-lib
gulp.task('mg-style-libs', function (cb) {
    del('src/resources/libs/_footer_lib.min.css', cb);
    return gulp
        .src([
            'src/resources/libs/core/normalize.min.css'
        ])
        .pipe(concatCss("_header_lib.min.css"))
        .pipe(gulp.dest('src/resources/libs'));
});

// 编译libs-static
gulp.task('libs-static', function () {
    // 所有libs均已压缩
    return gulp
        .src([
            'src/resources/libs/**/*.js',
            'src/resources/libs/**/*.css'
        ])
        // 加版本号
        .pipe(rev())
        // 输出
        .pipe(gulp.dest('dist/resources/libs'))
        // 生成 manifest
        .pipe(rev.manifest())
        .pipe(gulp.dest('manifest/libs'));
});

// 编译libs-other
gulp.task('libs-other', function () {
    return gulp
        .src([
            'src/resources/libs/**/*.*',
            '!src/resources/libs/**/*.js',
            '!src/resources/libs/**/*.css'])
        .pipe(gulp.dest('dist/resources/libs'));
});

// 编译images
gulp.task('images', function () {
    return gulp
        .src('src/resources/images/**/*.*')
        .pipe(gulp.dest('dist/resources/images'));
});

// 编译html
gulp.task('html', function () {
    return gulp
        .src(['manifest/**/*.json', 'src/html/**/*.ftl'])
        // 包含文件
        .pipe(fileInclude({
            prefix: '@@',
            basepath: '@root'
        }))
        // 替换静态文件引用
        .pipe(revCollector({
            replaceReved: true
        }))
        // 替换静态资源前缀路径
        .pipe(replace(/\/tc-static\/src\/resources/g, _environment.staticBase))
        // 替换根路径
        .pipe(replace(/\$\{htmlBase}/g, _environment.htmlBase))
        // 替换后端接口openApiBase
        .pipe(replace(/\$\{openApiBase}/g, _environment.openApiBase))
        // 替换静态资源staticBase
        .pipe(replace(/\$\{staticBase}/g, _environment.staticBase))
        // 压缩(开发不压缩)(使用ftl此选项不可使用)
        .pipe(gulpif(false, htmlmin({
            //清除HTML注释
            removeComments: false,
            //压缩HTML
            collapseWhitespace: false,
            //压缩页面JS
            minifyJS: true,
            //压缩页面CSS
            minifyCSS: true
        })))
        .pipe(gulp.dest('dist/ftl'));
});

// 编译error-pages
gulp.task('error-pages', function () {
    return gulp
        .src(['manifest/**/*.json', 'src/html/**/*.html'])
        // 包含文件
        .pipe(fileInclude({
            prefix: '@@',
            basepath: '@root'
        }))
        // 替换静态文件引用
        .pipe(revCollector({
            replaceReved: true
        }))
        // 替换静态资源前缀路径
        .pipe(replace(/\/tc-static\/src\/resources/g, _environment.staticBase))
        // 替换根路径
        .pipe(replace(/\$\{htmlBase}/g, _environment.htmlBase))
        // 替换后端接口openApiBase
        .pipe(replace(/\$\{openApiBase}/g, _environment.openApiBase))
        // 替换静态资源staticBase
        .pipe(replace(/\$\{staticBase}/g, _environment.staticBase))
        // 压缩(开发不压缩)(使用ftl此选项不可使用)
        .pipe(gulpif(false, htmlmin({
            //清除HTML注释
            removeComments: false,
            //压缩HTML
            collapseWhitespace: false,
            //压缩页面JS
            minifyJS: true,
            //压缩页面CSS
            minifyCSS: true
        })))
        .pipe(gulp.dest('dist/resources/html'));
});

// 编译特殊文件
gulp.task('special', function () {
    return gulp
        // layui 使用模块名加载模块 加版本之后文件名变化无法获取 复制源文件到目的地
        .src([
            'src/resources/libs/layui/**/*.js',
            'src/resources/libs/layui/**/*.css'
        ])
        // 输出
        .pipe(gulp.dest('dist/resources/libs/layui'));
});

// ----- 服务器
gulp.task('start', ['local'], function () {
    connect.server({
        name: 'tc-frontend',
        root: ['dist/html', 'dist/resources'],
        port: _environment.port,
        livereload: true
    });
});
