const express = require('express');
const router = express.Router();
const path = require('path');
const mysql = require('mysql');
var bcrypt = require('bcrypt-nodejs');
var operations = require('../scripts/database_operations');
var dbconfig = require('../config/database');
var connection = mysql.createConnection(dbconfig.connection);


const Vol = require('../modeles/vol.js');
const User = require('../modeles/user.js');
const Message = require('../modeles/message.js');

connection.query('USE ' + dbconfig.database);

//Retourne tous les vols avec
router.get('/',function (req, res, next) {
  var liste_vols = [];
  connection.query("SELECT *, (SELECT COUNT(participer.idvols) FROM participer WHERE participer.idvols = vols.idvols) as nbUser FROM vols LEFT JOIN users ON (vols.idCreateur = users.idusers) LIMIT 10",function(err,rows){
    for(var i = 0;i<rows.length;i++){
      liste_vols.push(new Vol(rows[i].idvols,rows[i].nomVol,rows[i].date_depart,rows[i].date_arrivee,rows[i].fini,rows[i].idCreateur,rows[i].ville_depart,rows[i].ville_arrivee,[],"null",rows[i].nomUser + " " +rows[i].prenom,"",rows[i].nbUser));
    }
    res.send(liste_vols);
  });
});

router.get('/:idvol',function (req, res, next) {
  var idvol = req.params.idvol;
  var liste_users = [];
  var i;
  connection.query("SELECT DISTINCT * FROM vols LEFT JOIN participer ON (participer.idvols = vols.idvols) LEFT JOIN users ON (participer.iduser = users.idusers) WHERE vols.idvols = ?",[idvol],function(err,rows){
    for(i = 0;i<rows.length;i++){
      liste_users.push(new User(rows[i].idusers,rows[i].nomUser,rows[i].prenom,rows[i].age,rows[i].username,rows[i].password,rows[i].userKey,rows[i].email,rows[i].description,rows[i].rang,"",""));
    }
    var vol = new Vol(rows[0].idvols,rows[0].nomVol,rows[0].date_depart,rows[0].date_arrivee,rows[0].fini,rows[0].idCreateur,rows[0].ville_depart,rows[0].ville_arrivee,liste_users,"","","","");
    res.send(vol);
  });
});

router.get('/like/:name',function (req, res, next) {
  var name = req.params.name;
  var liste_vols = []
  connection.query("SELECT *, (SELECT COUNT(participer.idvols) FROM participer WHERE participer.idvols = vols.idvols) as nbUser FROM vols LEFT JOIN users ON (vols.idCreateur = users.idusers) WHERE vols.nomVol LIKE ?",["%"+name+"%"],function(err,rows){
    if(err) throw err;
    for(var i = 0;i<rows.length;i++){
      liste_vols.push(new Vol(rows[i].idvols,rows[i].nomVol,rows[i].date_depart,rows[i].date_arrivee,rows[i].fini,rows[i].idCreateur,rows[i].ville_depart,rows[i].ville_arrivee,[],"null",rows[i].nomUser + " " +rows[i].prenom,"",rows[i].nbUser));
    }
    res.send(liste_vols);
  });
});

router.post('/participer',function(req,res){
  var idvols = req.body.idvols;
  var iduser = req.body.iduser;
  connection.query("INSERT INTO participer (iduser,idvols) VALUES (?,?)",[iduser,idvols],function(err){
    if(err){
      console.log(err);
      res.send("0");
    }
    else{
      res.send("1");
    }
  });

});

router.post('/debarquer',function(req,res){
    var idvols = req.body.idvols;
    var iduser = req.body.iduser;
    connection.query("DELETE FROM participer WHERE idvols = ? AND iduser = ?",[idvols,iduser],function(err){
      if(err){
        console.log(err);
        res.send("0");
      }
      else{
        res.send("1");
      }
    });
});

//Il faut cependant ajouter la participation du createur au vol
router.post('/create',function (req, res, next) {
  var nomVol = req.body.nomVol;
  var date_depart = req.body.date_depart;
  var date_arrivee = req.body.date_arrivee ;
  var fini = "0";
  var idCreateur = req.body.idCreateur;
  var ville_arrivee = req.body.ville_arrivee;
  var ville_depart = req.body.ville_depart;
  connection.query("INSERT INTO vols (nomVol,date_depart,date_arrivee,fini,idCreateur,ville_arrivee,ville_depart) VALUES (?,?,?,?,?,?,?)",[nomVol, date_depart, date_arrivee, fini, idCreateur, ville_arrivee,ville_depart],function(err,rows){
    if(err){
      console.log(err);
      res.send("0");
    }
    else{
      var idvols = rows.insertId;
      connection.query("INSERT INTO participer (iduser,idvols) VALUES (?,?)",[idCreateur,idvols],function(err){
        if(err){
          res.send("0");
        }
        else{
          res.send(idvols+"");
        }
      });
    }
  });
});

//Non utilisée à ce jour voir messages.js
router.post('/send',function (req, res, next) {
  var contenu = req.body.contenu;
  var idvols = req.body.idvols;
  var iduser = req.body.iduser;
  var date = req.body.date;
  connection.query("INSERT INTO messages (contenu,date,idvols,iduser) VALUES (?,?,?,?)",[contenu, date, idvols, iduser],function(err){
    if(err){
      console.log(err);
      res.send("0");
    }
    else{
      res.send("1");
    }
  });
});

module.exports = router;
