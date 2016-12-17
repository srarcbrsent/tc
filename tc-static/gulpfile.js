// --- npm init -> package.json
// [npm install --save-dev gulp] -> 核心
// [npm install --save-dev gulp del] -> 删除
// [npm install --save-dev gulp-if] -> 条件
// [npm install --save-dev gulp-filter] -> 过滤器
// [npm install --save-dev run-sequence] -> 顺序执行
// [npm install --save-dev jshint] -> JS校验核心
// [npm install --save-dev gulp-jshint] -> JS校验
// [npm install --save-dev gulp-file-include] -> HTML合并
// [npm install --save-dev gulp-replace] -> 替换文本
// [npm install --save-dev gulp-htmlmin] -> 压缩HTML
// [npm install --save-dev gulp-concat] -> 合并JS
// [npm install --save-dev gulp-uglify] -> 压缩JS
// [npm install --save-dev gulp-concat-css] -> 合并CSS
// [npm install --save-dev gulp-clean-css] -> 压缩CSS
// [npm install --save-dev gulp-rev] -> 给静态资源加MD5
// [npm install --save-dev gulp-rev-collector] -> 替换静态资源引用

var gulp = require('gulp');
var del = require('del');
var gulpif = require('gulp-if');
var filter = require('gulp-filter');
var runSequence = require('run-sequence');
var jshint = require('gulp-jshint');
var fileinclude = require('gulp-file-include');
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
    staticBase: 'http://static.tc.com',
    openApiBase: 'http://openapi.tc.com'
};

// 开发模式下编译
gulp.task('default', function (cb) {
    _environment.dev = true;
    task(cb);
});

// 生产模式下编译
gulp.task('pdu', function (cb) {
    task(cb);
});

var task = function(cb) {
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
    del('src/resources/libs/_post-lib.min.js', cb);
    return gulp
        .src([
            'src/resources/libs/jquery-3.1.0.min.js',
            'src/resources/libs/vue.min.js',
            'src/resources/libs/axios.min.js',
            'src/resources/libs/underscore.min.js',
            'src/resources/libs/assets/js/amazeui.min.js',
            'src/resources/libs/layer/layer.min.js'
        ])
        .pipe(concat('_post-lib.min.js'))
        .pipe(gulp.dest('src/resources/libs'));
});

// 合并stylesheet-lib
gulp.task('mg-style-libs', function (cb) {
    del('src/resources/libs/_pre-lib.min.css', cb);
    return gulp
        .src([
            'src/resources/libs/normalize.min.css',
            'src/resources/libs/assets/css/amazeui.flat.min.css'
        ])
        .pipe(concatCss("_pre-lib.min.css"))
        .pipe(gulp.dest('src/resources/libs'));
});

// 编译libs-static
gulp.task('libs-static', function () {
    // 所有libs均已压缩
    return gulp
        .src([
            // libs不打包为min文件不打包
            'src/resources/libs/**/*.min.js',
            'src/resources/libs/**/*.min.css'
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
        .src(['manifest/**/*.json', 'src/html/**/*.html'])
        // 包含文件
        .pipe(fileinclude({
            prefix: '@@',
            basepath: '@root'
        }))
        // 替换静态文件引用
        .pipe(revCollector({
            replaceReved: true
        }))
        // 替换静态资源前缀路径
        .pipe(replace(/\/tc-static\/src\/resources/g, _environment.staticBase))
        // 替换后端接口openApiBase
        .pipe(replace(/\$\{openApiBase}/g, _environment.openApiBase))
        // 替换静态资源staticBase
        .pipe(replace(/\$\{staticBase}/g, _environment.staticBase))
        // 压缩(开发不压缩)
        .pipe(gulpif(!_environment.dev, htmlmin({
            //清除HTML注释
            removeComments: true,
            //压缩HTML
            collapseWhitespace: true,
            //压缩页面JS
            minifyJS: true,
            //压缩页面CSS
            minifyCSS: true
        })))
        .pipe(gulp.dest('dist/html'));
});

// 编译特殊文件
gulp.task('special', function () {
    return gulp
        // 因为静态资源合并 相对路径发生改变 手动加载此文件
        .src('src/resources/libs/layer/skin/default/layer.css')
        .pipe(gulp.dest('dist/resources/libs/skin/default'));
});

// gulp-connect 启动server
