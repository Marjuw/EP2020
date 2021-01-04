const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    //Später
});

module.exports = mongoose.model('User', userSchema);