const express = require("express");
const bodyparser = require('body-parser');
const expressLayouts = require("express-ejs-layouts");

//firebase
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";

const firebaseConfig = {
    apiKey: "AIzaSyBirmY1-ImwpZv-CpcGQmhqIK6Lg9xQWbk",
    authDomain: "redsens-11b11.firebaseapp.com",
    databaseURL: "https://redsens-11b11-default-rtdb.firebaseio.com",
    projectId: "redsens-11b11",
    storageBucket: "redsens-11b11.appspot.com",
    messagingSenderId: "799400728048",
    appId: "1:799400728048:web:425edd986647e7b19a3935",
    measurementId: "G-LWDPEX6SK5"
  };

const fb = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);

const app = express()
//bodyparser middleware
app.use(bodyparser.urlencoded({extended : false}));
app.use(bodyparser.json());

//ejs
app.use(expressLayouts);
app.set("views", "./views");
app.set("view engine", "ejs");


app.use('/',require('./router/render.js'));

//Server
const PORT = process.env.PORT || 3002;

app.listen(PORT, console.log(`server started on port ${PORT}`));