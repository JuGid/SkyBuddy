class Message {
    constructor (contenu,date,idvols,iduser,nomenvoyeur) {
        this.contenu = contenu;
        this.date= date;
        this.idvols = idvols;
        this.iduser = iduser;
        this.envoyeur = nomenvoyeur;
    }

    toJson(){
      return{
        'contenu':this.contenu,
        'date':this.date,
        'idvols':this.idvols,
        'iduser':this.iduser,
        'envoyeur':this.envoyeur
      };
    }
  }

  module.exports = Message;
