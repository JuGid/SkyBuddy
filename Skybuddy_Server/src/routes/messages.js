const express = require('express');
const router = express.Router();
const path = require('path');
const mysql = require('mysql');
var bcrypt = require('bcrypt-nodejs');
var operations = require('../scripts/database_operations');
var dbconfig = require('../config/database');
var connection = mysql.createConnection(dbconfig.connection);


const Vol = require('../modeles/vol.js');
const Message = require('../modeles/message.js');

connection.query('USE ' + dbconfig.database);

//Tous les vols suivis par l'utilisateur avec le dernier message envoyé
router.get('/:iduser',function(req, res, next){
  var iduser = req.params.iduser;
  var liste_vols = [];
  connection.query("SELECT *, (SELECT contenu FROM messages WHERE messages.idvols = vols.idvols ORDER BY idmessages DESC LIMIT 1) as lastMessage,(SELECT COUNT(participer.idvols) FROM participer WHERE participer.idvols = vols.idvols) as nbUser, (SELECT nomUser FROM users as usersInt LEFT JOIN vols as volsInt ON(volsInt.idCreateur = usersInt.idusers) WHERE usersInt.idusers = vols.idCreateur AND volsInt.idvols = vols.idvols) as nomCreateur,(SELECT prenom FROM users as usersInt LEFT JOIN vols as volsInt ON(volsInt.idCreateur = usersInt.idusers)	WHERE usersInt.idusers = vols.idCreateur AND volsInt.idvols = vols.idvols) as prenomCreateur FROM vols LEFT JOIN participer ON (participer.idvols=vols.idvols) LEFT JOIN users ON(participer.iduser = users.idusers) WHERE users.idusers = ?",[iduser],function(err,rows){
    if(err) throw err;
    for(var i = 0; i< rows.length;i++){
      liste_vols.push(new Vol(rows[i].idvols,rows[i].nomVol,rows[i].date_depart,rows[i].date_arrivee,rows[i].fini,rows[i].idCreateur,rows[i].ville_depart,rows[i].ville_arrivee,[],"null",rows[i].nomCreateur + " " +rows[i].prenomCreateur,rows[i].lastMessage,rows[i].nbUser));
    }
    res.send(liste_vols);
  });
});

router.get('/ask/:idvol',function(req,res,next){
  var idvol = req.params.idvol;
  var liste_messages = [];
  connection.query("SELECT *, (SELECT prenom FROM users WHERE users.idusers = messages.iduser) as prenomEnvoyeur, (SELECT nomUser FROM users WHERE users.idusers = messages.iduser) as nomEnvoyeur  FROM messages WHERE idvols = ?",[idvol],function(err,rows){
    if(err)throw err;
    for(var i = 0; i< rows.length;i++){
      //(contenu,date,idvols,iduser,nomenvoyeur)
      liste_messages.push(new Message(rows[i].contenu,rows[i].date,rows[i].idvols,rows[i].iduser,rows[i].prenomEnvoyeur + " " + rows[i].nomEnvoyeur));
    }
    res.send(liste_messages);
  });
});

router.post('/addmessages/:idvol',function(req,res,next){
    var idvols = req.params.idvol;
    var contenu = req.body.contenu;
    var iduser = req.body.iduser;
    var date = req.body.date;
    connection.query("INSERT INTO messages(contenu,date,idvols,iduser) VALUES(?,?,?,?)",[contenu,date,idvols,iduser],function(err){
      if(err){
        res.send("0");
      }
      else{
        res.send("1");
      }
    });
});

router.post('/maj/:iduser',function(req,res,next){
  var iduser = req.params.iduser;
  var date = req.body.date;
  console.log(date);
  connection.query("SELECT vols.idvols, users.idusers, (SELECT messages.date FROM messages WHERE messages.idvols = vols.idvols AND messages.date > ? ORDER BY messages.idmessages DESC LIMIt 1) as dateMessage FROM vols LEFT JOIN participer ON (participer.idvols=vols.idvols) LEFT JOIN users ON(participer.iduser = users.idusers) WHERE users.idusers = ? HAVING dateMessage IS NOT NULL",["'"+date+"'",iduser],function(err,rows){
    if(err){
      res.send("0");
    }
    else{
      if(!rows.length){
        res.send("0");
      }
      else{
        res.send("1");
      }
    }
  });
});

module.exports = router;
