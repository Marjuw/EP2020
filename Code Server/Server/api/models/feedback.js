const mongoose = require('mongoose');

const feedbackSchema = mongoose.Schema({

    _id: mongoose.Schema.Types.ObjectId,
    grund: {type: String, required: true},
    inhalt: {type: String, required: true}

});

module.exports = mongoose.model('feedback', feedbackSchema);