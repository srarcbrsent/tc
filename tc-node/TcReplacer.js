const fs = require('fs');

fs.readFile(__filename, (err, data) => {
    if (err)
        throw err;
    console.log(data.toString());
});