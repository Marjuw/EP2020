const mongoose = require('mongoose');

const chatSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    bezeichnung: {type: String, required: true},
    erstelldatum: {type: String, required: true}, //Schauen ob Datum als Typ ?
    userID: {type: [Number], required: true}
});

module.exports = mongoose.model('Chats', chatSchema);