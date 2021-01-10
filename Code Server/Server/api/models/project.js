const mongoose = require('mongoose');

const projectSchema = mongoose.Schema({


    _id: mongoose.Schema.Types.ObjectId,
    projektleiter: {type: Number, required: true} ,   //Hier soll die UserID automatisch eingetragen werden
    name: {type: String, required: true},
    gewuenschteTeamgroesse: Number,
    gewuenschteRollen: String,
    beschreibung: {type: String, required: true},
    kategorie: {type: [Number], required: true} ,   // Tag
    ausfuehrungsort: {type: String, required: true},  //ob persönlich oder Online
    ausfuehrungsort_sichtbar: Boolean,  //wenn true dann nicht nur innerhalb des Teams sondern auch global sichtbar
    plz: String,
    plz_sichtbar: Boolean,  //wenn true dann nicht nur innerhalb des eigenen Profils und Teams sondern auch global sichtbar
    ort: String,
    ort_sichtbar: Boolean,  //wenn true dann nicht nur innerhalb des eigenen Profils und Teams sondern auch global sichtbar
    zweck: [Number],  //Tag
    startzeitpunkt: Date,
    dauer: Number,
    anforderung: [Number],    //Tag
    aktuelleTeilnehmerzahl: Number,
    teilnehmer: [Number], //Hier soll die UserID der Teilnehmer automatisch eingetragen werden
    teilnehmer_sichtbar: Boolean,       //wenn true dann nicht nur innerhalb des Teams sondern auch global sichtbar
    avatarPicture:  String   // Bild Dateityp ?



});

module.exports = mongoose.model('Project', projectSchema);