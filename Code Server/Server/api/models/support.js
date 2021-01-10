const mongoose = require('mongoose');

const supportSchema = mongoose.Schema({

    _id: mongoose.Schema.Types.ObjectId,
    problembereich: {type: String, required: true},
    betreff: {type: String, required: true},
    inhalt: {type: String, required: true}

});

module.exports = mongoose.model('support', supportSchema);