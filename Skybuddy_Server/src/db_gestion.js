var mysql = require('mysql');

var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "skyeugene01!",
  database: "eugene"
});

con.connect(function(err) {
  if (err) throw err;
  console.log("Connected!");
});

function createTables(){
  var sql = "CREATE TABLE users (name VARCHAR(255), address VARCHAR(255))";
  con.query(sql, function (err, result) {
    if (err) {console.log("Table customers already exists")};
    console.log("Table created");
  });
}

function insertData(){
  var sql2 = "INSERT INTO customers (name, address) VALUES ('Company', 'Highway')";
  con.query(sql2, function (err, result) {
    if (err) throw err;
    console.log("1 record inserted");
  });
}

function selectData(){
  con.query("SELECT * FROM customers", function (err, result, fields) {
    if (err) throw err;
    console.log(result);
  });
}

createTables();
insertData();
selectData();
