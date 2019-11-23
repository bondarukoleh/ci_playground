const mysql = require('mysql');

const {MYSQL_HOST, MYSQL_USER, MYSQL_PASSWORD, MYSQL_DATABASE} = process.env;
const createBaseTableQuery = 'CREATE TABLE IF NOT EXISTS visits (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, ts BIGINT)'

function getDbConnection(
	{
		host = MYSQL_HOST,
		user = MYSQL_USER,
		password = MYSQL_PASSWORD,
		database = MYSQL_DATABASE
	} = {}) {

	const connection = mysql.createConnection({host, user, password, database});

	connection.connect(function(err){
		if(err){
			console.log('Error connecting to db: ', err);
			return;
		}
		console.log('Connection to db established');
		con.query(createBaseTableQuery, function(err) {
			if(err) throw err;
		});
	});

	return connection;
}

module.exports = getDbConnection;
