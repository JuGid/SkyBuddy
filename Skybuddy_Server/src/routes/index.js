const express = require('express');
const router  = express.Router();
const mysql = require('mysql');
var bcrypt = require('bcrypt-nodejs');
const uuidv4 = require('uuid/v4');

const Article = require('../modeles/article.js');
const User = require('../modeles/user.js');

var dbconfig = require('../config/database');
var connection = mysql.createConnection(dbconfig.connection);
connection.query('USE ' + dbconfig.database);

/* GET home page. */
router.get('/', function (req, res, next) {
  var liste_articles = [];
  connection.query("SELECT * FROM articles ORDER BY idarticles DESC LIMIT 10 ",function(err,rows){
    for(var i = 0;i<rows.length;i++){
      liste_articles.push(new Article(rows[i].idarticles,rows[i].contenu,rows[i].titre));
    }
    res.send(liste_articles);
  });
});

//Testé et fonctionnel
router.post('/connect', function(req,res,next){
  var username = req.body.username;
  var password = req.body.password;
  connection.query("SELECT *, (SELECT COUNT(*) FROM participer WHERE users.idusers = participer.iduser) as nbVol, (SELECT AVG(mark.valeur) FROM mark WHERE mark.id_user_note = users.idusers) as noteMoyenne FROM users WHERE username = ?",[username],function(err,rows){
    if(err) console.log(err);
    if(!rows.length){
      res.send("0");
    }
    else{
      if(bcrypt.compareSync(password,rows[0].password)){
        var userKey = uuidv4();
        connection.query("UPDATE users SET userKey = ? WHERE username = ?",[userKey,username]);
        var user = new User(rows[0].idusers,rows[0].nomUser,rows[0].prenom,rows[0].age,rows[0].username,rows[0].password,userKey,rows[0].email,rows[0].description,rows[0].rang,rows[0].noteMoyenne,rows[0].nbVol);
        res.send(user);
      }
      else{
        res.send("0");
      }
    }
  });
});

//A tester quand le parsage des user sera fait.
router.post('/disconnect', function(req,res,next){
  var userKey = req.body.userKey;
  var username = req.body.username;

  console.log(userKey);
  connection.query("SELECT * FROM users WHERE username = ?",[username],function(err,rows){
    if(rows[0].userKey==userKey){
      connection.query("UPDATE users SET userKey = NULL WHERE username = ?",[username]);
      res.send("1");
    }
    else{
      res.send("0");
    }
  });

});

//Testé en fonctionnel
router.post('/subscribe', function(req,res,next){
  var username = req.body.username;
  var password = req.body.password;
  var password_crypt = bcrypt.hashSync(password,null,null);
  var nom = req.body.nom;
  var prenom = req.body.prenom;
  var age = req.body.age;
  var email = req.body.email;

  connection.query("SELECT * FROM users WHERE username = ?",[username],function(err,rows){
    if(err) console.log(err);
    if(rows.length){
      res.send("0");
    }
    else{
      connection.query("INSERT INTO users (nomUser,prenom,username,password,email,rang,age) values (?,?,?,?,?,?,?)",[nom,prenom,username,password_crypt,email,"Débutant",age]);
      res.send("1");
    }
  });
});

module.exports = router;
