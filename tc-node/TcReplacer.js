'use strict';

const _fs = require('fs');

function _loadFile() {
    _fs.readFile(__filename, (err, data) => {
        if (err) {
            throw err;
        }
        return data.toString();
    });
}

function _listFiles(fullPath, filter, doWith) {
    if (_fs.statSync(fullPath).isFile()) {
        if (filter(fullPath)) {
            doWith(fullPath);
        }
    } else {
        let files = _fs.readdirSync(fullPath);
        files.forEach(filename => {
            _listFiles(fullPath + '/' + filename);
        })
    }
}

function main() {
    let args = process.argv.slice(2);
    let rootPath = args[0];
    let sourceStr = args[1];
    let targetStr = args[2];
    // root path exists
    let rootExists = _fs.existsSync(rootPath);
    if (!rootExists) {
        throw new Error("rootPath path not exists!");
    }
    // iterator
    _listFiles(rootPath, fullPath => {
        return fullPath.endsWith('pom.xml');
    }, fullPath => {
        console.log(fullPath);
    });
}

main();