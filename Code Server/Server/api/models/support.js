const mongoose = require('mongoose');

const supportSchema = mongoose.Schema({

    _id: String,
    problembereich: {type: String, required: true},
    betreff: {type: String, required: true},
    inhalt: {type: String, required: true},
    erstelldatum: {type: String, required: true},
    user: {type: Number, required: true}

});

module.exports = mongoose.model('support', supportSchema);