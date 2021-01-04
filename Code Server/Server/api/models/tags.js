const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    bezeichnung: String,
    typ: String  // Kategorie, Skills/Anforderungen, Kommunikation, Zweck
});

module.exports = mongoose.model('User', userSchema);