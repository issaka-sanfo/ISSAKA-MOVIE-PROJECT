// /models/Product.js

const mongoose = require('mongoose');
const {Schema} = mongoose;

const movieSchema = new Schema({
    title: String,
    releaseDate: String,
    category: String,
    movieDirector: String,
})

mongoose.model('movies', movieSchema);