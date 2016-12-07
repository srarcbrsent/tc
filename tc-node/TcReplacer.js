'use strict';

const _fs = require('fs');

_fs.readFile(__filename, (err, data) => {
    if (err) {
        throw err;
    }
    let content = data.toString();
    let contentReplaced = content.replace(new RegExp('__filename', 'g'), '______filename');
    _fs.writeFile(__dirname + '/_tmp.js', contentReplaced, (err) => {
        if (err) {
            throw err;
        }
        console.log('It\'s saved!');
    });
});