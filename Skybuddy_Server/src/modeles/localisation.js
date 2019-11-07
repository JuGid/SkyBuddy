class Localisation {
    constructor (idlocalisation,iduser,idvols,posx,posy,nomuser) {
      this.idlocalisation = idlocalisation;
      this.iduser =iduser;
      this.idvols = idvols;
      this.posx = posx;
      this.posy = posy;
      this.nomuser = nomuser;
    }
    toJson(){
      return {
        'idlocalisation':idlocalisation,
        'iduser':iduser,
        'idvols':idvols,
        'posx':posx,
        'posy':posy,
        'nomuser':nomuser
      };
    }
  }

  module.exports = Localisation;
