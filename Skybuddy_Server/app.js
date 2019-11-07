//Importation des middleware nécessaires
const express = require('express');
const session = require('express-session');
const cookieParser = require('cookie-parser');
const logger = require('morgan');
const bodyParser = require('body-parser');
const path = require('path');
const morgan = require('morgan');

//Definition de mes fichiers routes
const indexRouter = require('./src/routes/index');
const flightsRouter = require("./src/routes/flights");
const usersRouter = require("./src/routes/users");
const commentsRouter = require("./src/routes/comments");
const messagesRouter = require("./src/routes/messages");
const localisationRouter = require("./src/routes/localisations");

const app = express();

//Initialisation des modules
app.use(morgan('dev'));
app.use(express.json()); //Je ne sais pas si cette ligne est vraiment utile ?
app.use(express.urlencoded({ extended: false }));// Ni celle-ci ?
app.use(bodyParser.urlencoded({ extended: true }));//Car il y a celle-ci
app.use(bodyParser.json());// Et celle-ci !
app.use(cookieParser());

//Il faudra ensuite passer l'instance de passport sur les routes pour autoriser
//les utilisateurs loggés.
app.use('/', indexRouter);
app.use('/flights', flightsRouter);
app.use('/users', usersRouter);
app.use('/comments', commentsRouter);
app.use('/messages',messagesRouter);
app.use('/localisations',localisationRouter);

module.exports = app;
