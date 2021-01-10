const mongoose = require('mongoose');

const userSchema = mongoose.Schema({

    _id: mongoose.Schema.Types.ObjectId,
    nickname: {type: String, required: true},
    vorname: String,
    vorname_sichtbar: Boolean, //wenn true dann nicht nur innerhalb des eigenen Profils und Teams sondern auch global sichtbar
    name: String,
    name_sichtbar: Boolean, //wenn true dann nicht nur innerhalb des eigenen Profils und Teams sondern auch global sichtbar
    strasse: String,
    strasse_sichtbar: Boolean, //wenn true dann nicht nur innerhalb des eigenen Profils und Teams sondern auch global sichtbar
    plz: String,
    plz_sichtbar: Boolean,  //wenn true dann nicht nur innerhalb des eigenen Profils und Teams sondern auch global sichtbar
    ort: String,
    ort_sichtbar: Boolean,  //wenn true dann nicht nur innerhalb des eigenen Profils und Teams sondern auch global sichtbar
    beschreibung: String,
    interessen: {type: [Number], required: true},      //Tag
    faehigkeiten: {type: [Number], required: true},    //Tag
    avatarPicture:  String,   // Bild Dateityp ?
    kommunikation: String,
    email: {type: String, required: true},
    passwort: {type: String, required: true},
    tags_abonniert: [Number]

});

module.exports = mongoose.model('User', userSchema);