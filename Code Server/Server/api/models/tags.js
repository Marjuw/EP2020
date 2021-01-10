const mongoose = require('mongoose');

const tagSchema = mongoose.Schema({

    _id: mongoose.Schema.Types.ObjectId,
    bezeichnung: {type: String, required: true},
    typ: {type: String, required: true}  // Kategorie, Skills/Anforderungen, Kommunikation, Zweck als Tag
});

module.exports = mongoose.model('tags', tagSchema);