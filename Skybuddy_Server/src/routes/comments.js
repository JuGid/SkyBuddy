const express = require('express');
const router = express.Router();
const path = require('path');
const mysql = require('mysql');
var bcrypt = require('bcrypt-nodejs');
var dbconfig = require('../config/database');
var connection = mysql.createConnection(dbconfig.connection);

connection.query('USE ' + dbconfig.database);

router.post('/create',function (req, res, next) {
  var note = req.body.note;
  var contenu = req.body.contenu ;
  var id_user_note = req.body.id_user_note ;
  var id_vol = req.body.id_vol;
  var id_user_noteur = req.body.id_user_noteur;
  connection.query("INSERT INTO mark (contenu,valeur,idvols,id_user_noteur,id_user_note) VALUES (?,?,?,?,?)",[contenu,note, id_vol, id_user_noteur,id_user_note],function(err){
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
