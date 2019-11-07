class Vol {
    constructor (idvols,nomVol,date_depart,date_arrivee,fini,idCreateur,ville_depart,ville_arrivee,liste_user,dernierMessage,nomPrenomCreateur,lastMessage,nbUser) {
      this.idvols=idvols;
      this.nomVol=nomVol;
      this.date_depart=date_depart;
      this.date_arrivee=date_arrivee;
      this.fini=fini;
      this.idCreateur=idCreateur;
      this.ville_depart=ville_depart;
      this.ville_arrivee=ville_arrivee;
      this.liste_user=liste_user; //Liste de tous les utilisateurs pour un vol
      this.dernierMessage=dernierMessage; //le dernier message envoyé
      this.nomPrenomCreateur = nomPrenomCreateur;
      this.lastMessage = lastMessage;
      this.nbUser = nbUser;
    }

    toJson(){
      return{
        'idvols':this.idvols,
        'nomVol':this.nomVol,
        'date_depart':this.date_depart,
        'date_arrivee':this.date_arrivee,
        'fini':this.fini,
        'idCreateur':this.idCreateur,
        'ville_depart':this.ville_depart,
        'ville_arrivee':this.ville_arrivee,
        'liste_user':this.liste_user,
        'dernierMessage': this.dernierMessage,
        'nomPrenomCreateur':this.nomPrenomCreateur,
        'lastMessage':this.lastMessage,
        'nbUser':this.nbUser
      }
    }
  }

  module.exports = Vol;
