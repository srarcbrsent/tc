// --- npm init -> package.json
// [npm install --save-dev gulp] -> 核心
// [npm install --save-dev jshint] -> JS校验核心
// [npm install --save-dev gulp-jshint] -> JS校验
// [npm install --save-dev gulp-content-includer] ->


var gulp = require('gulp');
var jshint = require('gulp-jshint');

gulp.task('default', function() {
    // 将你的默认的任务代码放在这
});

// 检查脚本
gulp.task('lint', function() {
    gulp.src('src/resources/scripts/**/*.js')
        .pipe(jshint())
        .pipe(jshint.reporter('default'));
});

// gulp-concat 合并js
// gulp-concat-css 合并css

//  合并html

// gulp-htmlmin 压缩html
// gulp-uglify 压缩丑化js
// gulp-minify-css 压缩css

// gulp-rev 给静态资源加md5版本号
// gulp-rev-collector 给html添md5版本号

// gulp-autoprefixer 自动添加浏览器前缀

// del 删除文件

// gulp-connect 启动server
