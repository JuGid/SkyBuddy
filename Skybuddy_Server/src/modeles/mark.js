class Mark {
    constructor (idmark,contenu,valeur,dateMark,idvols,user_noteur,user_note) {
      this.idmark = idmark;
      this.contenu =contenu;
      this.valeur = valeur;
      this.dateMark = dateMark;
      this.idvols = idvols;
      this.user_noteur = user_noteur;
      this.user_note = user_note;
    }
    toJson(){
      return {
        'idmark':this.idmark,
        'contenu':this.contenu,
        'valeur':this.valeur,
        'dateMark':this.dateMark,
        'idvols':this.idvols,
        'user_noteur':this.user_noteur,
        'user_note':this.user_note
      };
    }
  }

  module.exports = Mark;
