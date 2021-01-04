const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    bezeichnung: String,
    erstelldatum: String, //Schauen ob Datum als Typ ?
    userID: [String],
    // gucken we ma
});

module.exports = mongoose.model('User', userSchema);