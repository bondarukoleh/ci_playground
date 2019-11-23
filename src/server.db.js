const express = require('express');
const app = express();
const {getDbConnection} = require('./helpers');

const port = 3000;
const dbConnection = getDbConnection();

// Request handling
app.get('/', function (req, res) {
  // create table if not exist
  dbConnection.query('INSERT INTO visits (ts) values (?)', Date.now(),function(err, dbRes) {
    if(err) throw err;
    res.send('Hello from server! You are visitor number '+dbRes.insertId);
  });
});


const server = app.listen(port, function () {
  console.log(`Test app listening at ${server.address().port}`);
});
