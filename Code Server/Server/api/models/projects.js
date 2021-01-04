const mongoose = require('mongoose');

const projectSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    userID: String,
    name: {type: String, required: true},
    name_sichtbar: Boolean,
    gewuenschteTeamgroesse: Int,
    gewuenschteRollen: Sring,
    beschreibung: String,
    kategorie: String,   // Tag ??
    ausfuehrungsort: String,
    zweck: String,  //Tag?
    startzeitpunkt: String,  //Schauen ob Datum als Typ ?
    dauer: Int,
    anforderung: String,    //Tag?
    aktuelleTeilnehmerzahl: Int,
    teilnehmer: String,
                //Wie sag ich jetzt was öffentlich ist und was nicht?

});

module.exports = mongoose.model('Projects', projectSchema);