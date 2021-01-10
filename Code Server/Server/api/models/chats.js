const mongoose = require('mongoose');

const chatSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    bezeichnung: {type: String, required: true},
    erstelldatum: {type: String, required: true}, //Schauen ob Datum als Typ ?
    userID: {type: [Number], required: true},
    Chatanahme: Boolean  // Ob eine Chatanfrage angenommen wird oder nicht
});

module.exports = mongoose.model('Chats', chatSchema);