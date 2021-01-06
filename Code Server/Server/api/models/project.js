const mongoose = require('mongoose');

const projectSchema = mongoose.Schema({


    _id: mongoose.Schema.Types.ObjectId,
    projektleiter: {type: String, required: true} ,   //Hier soll die UserID automatisch eingetragen werden
    name: {type: String, required: true},
    gewuenschteTeamgroesse: Number,
    gewuenschteRollen: String,
    beschreibung: String,
    kategorie: [String],   // Tag
    ausfuehrungsort: String,
    ausfuehrungsort_sichtbar: Boolean,  //wenn true dann nicht nur innerhalb des Teams sondern auch global sichtbar
    zweck: [String],  //Tag
    startzeitpunkt: Date,
    dauer: Number,
    anforderung: [String],    //Tag
    aktuelleTeilnehmerzahl: Number,
    teilnehmer: Number, //Hier soll die UserID der Teilnehmer automatisch eingetragen werden
    teilnehmer_sichtbar: Boolean        //wenn true dann nicht nur innerhalb des Teams sondern auch global sichtbar


});

module.exports = mongoose.model('Project', projectSchema);