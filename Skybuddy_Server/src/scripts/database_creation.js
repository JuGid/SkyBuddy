var mysql = require('mysql');
var dbconfig = require('../config/database');

var connection = mysql.createConnection(dbconfig.connection);

connection.query('CREATE DATABASE ' + dbconfig.database,function(err){
  console.log("Creation de la base de données "+dbconfig.database);
});

connection.query("USE "+ dbconfig.database,function(err){
  console.log("Utilisation de la base de données "+dbconfig.database);
});
//Il faut maintenant créer toutes les tables...
//je créé d'abord les string puis je fait la requête au SGBD
var user_table = "CREATE TABLE `eugene`.`users` (\
  `idusers` INT NOT NULL AUTO_INCREMENT,\
  `nomUser` VARCHAR(255) NULL,\
  `prenom` VARCHAR(255) NULL,\
  `age` INT NULL,\
  `username` VARCHAR(20) NULL,\
  `password` VARCHAR(255) NULL,\
  `userKey` VARCHAR(255) NULL,\
  `email` VARCHAR(255) NULL,\
  `description` VARCHAR(140) NULL,\
  `rang` VARCHAR(50) NULL,\
  PRIMARY KEY (`idusers`));";

var message_table = "CREATE TABLE `eugene`.`messages` (\
  `idmessages` INT NOT NULL AUTO_INCREMENT,\
  `contenu` TEXT NULL,\
  `date` DATETIME NULL,\
  `idvols` INT NULL,\
  `iduser` INT NULL,\
  PRIMARY KEY (`idmessages`),\
  INDEX `envoyer_idx` (`iduser` ASC) VISIBLE,\
  INDEX `groupe_idx` (`idvols` ASC) VISIBLE,\
  CONSTRAINT `envoyer`\
    FOREIGN KEY (`iduser`)\
    REFERENCES `eugene`.`users` (`idusers`)\
    ON DELETE NO ACTION\
    ON UPDATE NO ACTION,\
  CONSTRAINT `groupe`\
    FOREIGN KEY (`idvols`)\
    REFERENCES `eugene`.`vols` (`idvols`)\
    ON DELETE NO ACTION\
    ON UPDATE NO ACTION);";

var flight_table = "CREATE TABLE `eugene`.`vols` (\
  `idvols` INT NOT NULL AUTO_INCREMENT,\
  `nomVol` TEXT NULL,\
  `date_depart` DATETIME NULL,\
  `date_arrivee` DATETIME NULL,\
  `fini` TINYINT NULL,\
  `idCreateur` INT NULL,\
  `ville_arrivee` VARCHAR(100) NULL,\
  `ville_depart` VARCHAR(100) NULL,\
  PRIMARY KEY (`idvols`),\
  INDEX `creation_idx` (`idCreateur` ASC) VISIBLE,\
  CONSTRAINT `creation`\
    FOREIGN KEY (`idCreateur`)\
    REFERENCES `eugene`.`users` (`idusers`)\
    ON DELETE NO ACTION\
    ON UPDATE NO ACTION);";

var article_table = "CREATE TABLE `eugene`.`articles` (\
  `idarticles` INT NOT NULL AUTO_INCREMENT,\
  `contenu` TEXT NULL,\
  `titre` VARCHAR(100) NULL,\
  PRIMARY KEY (`idarticles`));\
  ";

var participations_table="CREATE TABLE `eugene`.`participer` (\
  `idparticiper` INT NOT NULL AUTO_INCREMENT,\
  `iduser` INT NULL,\
  `idvols` INT NULL,\
  PRIMARY KEY (`idparticiper`),\
  INDEX `participe_idx` (`idvols` ASC) VISIBLE,\
  INDEX `participe_idx1` (`iduser` ASC) VISIBLE,\
  CONSTRAINT `participe`\
    FOREIGN KEY (`idvols`)\
    REFERENCES `eugene`.`vols` (`idvols`)\
    ON DELETE NO ACTION\
    ON UPDATE NO ACTION,\
  CONSTRAINT `part`\
    FOREIGN KEY (`iduser`)\
    REFERENCES `eugene`.`users` (`idusers`)\
    ON DELETE NO ACTION\
    ON UPDATE NO ACTION);";

var mark_table = "CREATE TABLE `eugene`.`mark` (\
  `idmark` INT NOT NULL AUTO_INCREMENT,\
  `contenu` TEXT NULL,\
  `valeur` INT NULL,\
  `dateMark` DATETIME NULL,\
  `idvols` INT NULL,\
  `id_user_noteur` INT NULL,\
  `id_user_note` INT NULL,\
  PRIMARY KEY (`idmark`),\
  INDEX `vol_idx` (`idvols` ASC) VISIBLE,\
  INDEX `noteur_idx` (`id_user_noteur` ASC) VISIBLE,\
  INDEX `lenote_idx` (`id_user_note` ASC) VISIBLE,\
  CONSTRAINT `vol`\
    FOREIGN KEY (`idvols`)\
    REFERENCES `eugene`.`vols` (`idvols`)\
    ON DELETE NO ACTION\
    ON UPDATE NO ACTION,\
  CONSTRAINT `noteur`\
    FOREIGN KEY (`id_user_noteur`)\
    REFERENCES `eugene`.`users` (`idusers`)\
    ON DELETE NO ACTION\
    ON UPDATE NO ACTION,\
  CONSTRAINT `lenote`\
    FOREIGN KEY (`id_user_note`)\
    REFERENCES `eugene`.`users` (`idusers`)\
    ON DELETE NO ACTION\
    ON UPDATE NO ACTION);";


var localisation_table = "CREATE TABLE `localisation` (\
  `idlocalisation` int(11) NOT NULL AUTO_INCREMENT,\
  `iduser` int(11) NOT NULL,\
  `idvols` int(11) NOT NULL,\
  `posx` float NOT NULL,\
  `posy` float NOT NULL,\
  PRIMARY KEY (`idlocalisation`),\
  KEY `vols_idx` (`idvols`),\
  KEY `user_idx` (`iduser`),\
  CONSTRAINT `user` FOREIGN KEY (`iduser`) REFERENCES `users` (`idusers`),\
  CONSTRAINT `vols` FOREIGN KEY (`idvols`) REFERENCES `vols` (`idvols`));";

connection.query(user_table, function(err){
  if(err) throw err;
  console.log("Table users ajoutée");
});

connection.query(flight_table, function(err){
  if(err) throw err;
  console.log("Table des vols ajoutée");
});

connection.query(participations_table, function(err){
  if(err) throw err;
  console.log("Table des participation ajoutée");
});

connection.query(message_table, function(err){
  if(err) throw err;
  console.log("Table des messages ajoutée");
});

connection.query(article_table, function(err){
  if(err) throw err;
  console.log("Table des articles ajoutée");
});

connection.query(mark_table, function(err){
  if(err) throw err;
  console.log("Table des notes ajoutée");
});

connection.query(localisation_table, function(err){
  if(err) throw err;
  console.log("Table des localisation ajoutée");
});
