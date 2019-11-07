class Article {
    constructor (idarticles,contenu,titre) {
      this.idarticles = idarticles;
      this.contenu = contenu;
      this.titre = titre;
    }

    toJson(){
      return{
        'idarticles':this.idarticles,
        'contenu':this.contenu,
        'titre':this.titre
      };
    }
  }

  module.exports = Article;
