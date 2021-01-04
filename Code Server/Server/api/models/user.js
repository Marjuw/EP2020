const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    nickname: {type: String, required: true},
    firstName: String,
    name: String,
    street: String,
    postCode: Number,
    place: String,
    beschreibung: String,
    interests: String,
    skills: [String],     // Tags (evtl als array)
   // avatarPicture:  String,   // Bild Dateityp ???
    communication: String

});

module.exports = mongoose.model('User', userSchema);