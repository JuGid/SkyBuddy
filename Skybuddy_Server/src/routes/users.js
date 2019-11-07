const express = require('express');
const router = express.Router();
const path = require('path');
const mysql = require('mysql');
var bcrypt = require('bcrypt-nodejs');
var dbconfig = require('../config/database');
var connection = mysql.createConnection(dbconfig.connection);

const User = require('../modeles/user.js');
const Vol = require('../modeles/vol.js');
const Mark = require('../modeles/mark.js');

connection.query('USE ' + dbconfig.database);

router.get('/',function (req, res, next) {
  var liste_users = [];
  connection.query("SELECT * FROM users",function(err,rows){
    if(err) throw err;
    for(var i = 0;i<rows.length;i++){
      liste_users.push(new User(rows[i].id,rows[i].nomUser,rows[i].prenom,rows[i].age,rows[i].username,rows[i].password,rows[i].userKey,rows[i].email,rows[i].description,rows[i].rang,"",""));
    }
    res.send(liste_users);
  });
});

router.get('/:iduser', function (req, res, next) {
  var idusers = req.params.iduser;
  connection.query("SELECT *, (SELECT COUNT(*) FROM participer WHERE participer.iduser = users.idusers) as nbVol, (SELECT AVG(mark.valeur) FROM mark WHERE mark.id_user_note = users.idusers) as noteMoyenne FROM users WHERE users.idusers = ?;",[idusers],function(err,rows){
    res.send(new User(rows[0].idusers,rows[0].nomUser,rows[0].prenom,rows[0].age,rows[0].username,rows[0].password,rows[0].userKey,rows[0].email,rows[0].description,rows[0].rang,rows[0].noteMoyenne,rows[0].nbVol));
  });
});

router.get('/:iduser/follow', function (req, res, next) {
  var iduser = req.params.iduser;
  var liste_vols = [];
  connection.query("SELECT *,(SELECT COUNT(participer.idvols) FROM participer WHERE participer.idvols = vols.idvols) as nbUser, (SELECT nomUser FROM users as usersInt LEFT JOIN vols as volsInt ON(volsInt.idCreateur = usersInt.idusers) WHERE usersInt.idusers = vols.idCreateur AND volsInt.idvols = vols.idvols) as nomCreateur,(SELECT prenom FROM users as usersInt LEFT JOIN vols as volsInt ON(volsInt.idCreateur = usersInt.idusers)	WHERE usersInt.idusers = vols.idCreateur AND volsInt.idvols = vols.idvols) as prenomCreateur FROM vols LEFT JOIN participer ON (participer.idvols=vols.idvols) LEFT JOIN users ON(participer.iduser = users.idusers) WHERE users.idusers = ?",[iduser],function(err,rows){
    if(err) throw err;
    for(var i = 0; i< rows.length;i++){
      liste_vols.push(new Vol(rows[i].idvols,rows[i].nomVol,rows[i].date_depart,rows[i].date_arrivee,rows[i].fini,rows[i].idCreateur,rows[i].ville_depart,rows[i].ville_arrivee,[],"null",rows[i].nomCreateur + " " +rows[i].prenomCreateur,"",rows[i].nbUser));
    }
    res.send(liste_vols);
  });
});

router.get('/:iduser/comments', function (req, res, next) {
  var iduser = req.params.iduser;
  var liste_marks=[];
  connection.query("SELECT *, (SELECT nomUser FROM users WHERE users.idusers = mark.id_user_noteur) as nomNoteur, (SELECT prenom FROM users  WHERE users.idusers = mark.id_user_noteur) as prenomNoteur FROM mark WHERE mark.id_user_note = ?",[iduser],function(err,rows){
    if(err) throw err;
    for(var i = 0;i < rows.length;i++){
      liste_marks.push(new Mark(rows[i].idmark,rows[i].contenu,rows[i].valeur,rows[i].dateMark,rows[i].idvols,rows[i].prenomNoteur + " " + rows[i].nomNoteur,iduser));
    }
    res.send(liste_marks);
  });
});

router.post('/:iduser/update',function(req,res){
  var iduser = req.params.iduser;
  var password = req.body.password;
  var password_crypt = bcrypt.hashSync(password,null,null);
  var mail = req.body.mail;
  var description = req.body.description;
  connection.query("SELECT * FROM users WHERE users.idusers = ?",[iduser],function(err,rows){
    if(password != rows[0].password){
      connection.query("UPDATE users SET email = ?, password = ?, description = ? WHERE users.idusers = ? ",[mail,password_crypt,description,iduser],function(err){
        if(err){
          console.log(err);
          res.send("0");
        }
        else{
          res.send("1");
        }
      });
    }
    else{
      connection.query("UPDATE users SET email = ?, description = ? WHERE users.idusers = ? ",[mail,description,iduser],function(err){
        if(err){
          console.log(err);
          res.send("0");
        }
        else{
          res.send("1");
        }
      });
    }
  });

});

module.exports = router;
