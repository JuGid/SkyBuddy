const express = require('express');
const router = express.Router();
const path = require('path');
const mysql = require('mysql');
const async = require('async');
var bcrypt = require('bcrypt-nodejs');
var operations = require('../scripts/database_operations');
var dbconfig = require('../config/database');
var connection = mysql.createConnection(dbconfig.connection);


const Localisation = require('../modeles/localisation.js');

connection.query('USE ' + dbconfig.database);

router.get('/:idvols',function(req,res,next){
  var idvols = req.params.idvols;
  var liste_localisation = [];
  connection.query("SELECT * FROM localisation INNER JOIN users ON(users.idusers = localisation.iduser) WHERE idvols = ?",[idvols],function(err,rows){
    if(err){
      console.log(err);
      res.send("0");
    }
    else{
      for(var i = 0;i<rows.length;i++){
        //idlocalisation,iduser,idvols,posx,posy,nomuser
        liste_localisation.push(new Localisation(rows[i].idlocalisation,rows[i].iduser,rows[i].idvols,rows[i].posx,rows[i].posy,rows[i].prenom + " " + rows[i].nomUser));
      }
      res.send(liste_localisation);
    }
  });
});

router.post('/localise',function(req,res,next){
  var idvols = req.body.idvols;
  var iduser = req.body.iduser;
  var posx = req.body.posx;
  var posy = req.body.posy;

  connection.query("SELECT * FROM localisation WHERE idvols = ? AND iduser = ?",[idvols,iduser],function(err,rows){
    if(err){
      console.log(err);
    }
    else{
      if(rows.length <= 0){
        //on ajoute
        connection.query("INSERT INTO localisation(iduser,idvols,posx,posy) VALUES(?,?,?,?)",[iduser,idvols,posx,posy],function(err){
          if(err){
            res.send("0");
          }
          else{
            res.send("1");
          }
        });
      }
      else{
        //on update
        connection.query("UPDATE localisation SET posx = ?, posy = ? WHERE iduser = ? AND idvols = ?",[posx,posy,iduser,idvols],function(err){
          if(err){
            res.send("0");
          }
          else{
            res.send("1");
          }
        });
      }
    }
  });
});

router.post('/update',function(req,res,next){
  var idvols = req.params.idvols;
  connection.query()
});

module.exports = router;
