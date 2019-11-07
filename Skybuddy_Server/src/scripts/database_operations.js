require("../modeles/vol");

var mysql = require('mysql');
var dbconfig = require('../config/database');
const Vol = require('../modeles/vol.js');
const User = require('../modeles/user.js');
var bcrypt = require('bcrypt-nodejs');
var connection = mysql.createConnection(dbconfig.connection);

connection.query("USE " + dbconfig.database);

module.exports = {

  insertUser : function(user){
    connection.query("INSERT INTO users(nomUser, prenom,username,password,email,description,age,rang) values(?,?,?,?,?,?,?,?)",[user.nom,user.prenom,user.username,bcrypt.hashSync(user.password,null,null),user.email,user.description,user.age,user.rang],function(err){
      if(err) throw err;
    });
  },

  insertFlight : function(vol){
    console.log("Insertion d'un vol");
    connection.query("INSERT INTO vols (nomVol,date_depart,date_arrivee,fini,idCreateur,ville_arrivee,ville_depart) values(?,?,?,?,?,?,?)",[vol.nom,vol.date_depart,vol.date_arrivee,vol.fini,vol.idCreateur,vol.ville_arrivee,vol.ville_depart],function(err){
      if(err) throw err;
    });
  },

  insertArticle : function(article){
    connection.query("INSERT INTO articles(contenu, titre) values(?,?)",[article.contenu,article.titre],function(err){
      if(err) throw err;
    });
  },

  insertNote : function(note){
    //idmark, contenu, valeur, dateMark,id_vols, id_user_noteur, id_user_note
    connection.query("INSERT INTO mark(contenu,valeur,dateMark,idvols,id_user_noteur,id_user_note) values(?,?,?,?,?,?)",[note.contenu,note.valeur,note.dateMark,note.id_vols,note.id_user_noteur,note.id_user_note],function(err){
      if(err) throw err;
    });
  },

  insertMessage : function(message){
    connection.query("INSERT INTO messages(contenu,date,idvols,iduser)values(?,?,?,?)",[message.contenu,message.date,message.id_vol,message.id_user],function(err){
      if(err) throw err;
    })
  },

  insertParticiper : function(participer){
    connection.query("INSERT INTO participer(iduser,idVols)values(?,?)",[participer.iduser,participer.idvols],function(err){
      if(err) throw err;
    });
  },

  getVol : function(id){
    connection.query("SELECT * FROM vols WHERE idvols = ?",[id],function(err,rows){
      const vol = new Vol(rows[0].nom,rows[0].date_depart,rows[0].date_arrivee,rows[0].fini,rows[0].ville_arrivee,rows[0].ville_depart,rows[0].id_user_creator);
      return vol;
    });
  },

  getUser : function(id){
    connection.query("SELECT * FROM users WHERE idusers = ?",[id],function(err,rows){
      const user = new User(rows[0].nom,rows[0].prenom,rows[0].username,rows[0].email,rows[0].age,"",rows[0].description,rows[0].rang);
      return user;
    });
  }

}
