const mongoose = require('mongoose');

const tagSchema = mongoose.Schema({

    _id: Number,
    bezeichnung: {type: String, required: true},
    typ: {type: String, required: true}  // Kategorie, Skills/Anforderungen, Kommunikation, Zweck als Tag
});

module.exports = mongoose.model('Tag', tagSchema);
