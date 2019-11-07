class User {
    constructor (id,nomUser,prenom,age,username,password,userKey,email,description,rang,noteMoyenne,nbVol) {
        this.id = id;
        this.nomUser = nomUser;
        this.prenom = prenom;
        this.age = age;
        this.username = username;
        this.password = password;
        this.userKey = userKey;
        this.email = email;
        this.description = description;
        this.rang = rang;
        this.noteMoyenne = noteMoyenne;
        this.nbVol = nbVol;
    }

    toJson(){
      return {
        'id' : this.id,
        'nomUser' : this.nomUser,
        'prenom' : this.prenom,
        'age' : this.age,
        'username' : this.username,
        'password' : this.password,
        'userKey' : this.userKey,
        'email' : this.emai,
        'description' : this.description,
        'rang' : this.rang,
        'noteMoyenne' : this.noteMoyenne,
        'nbVol' : this.nbVol
      };
    }
  }

  module.exports = User;
