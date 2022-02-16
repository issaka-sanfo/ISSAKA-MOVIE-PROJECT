// /models/Product.js

const mongoose = require('mongoose');
const {Schema} = mongoose;

const ratingSchema = new Schema({
    rating: String,
    commentTitle: String,
    commentContent: String,
    userId: String,
    movieId: String,
})

mongoose.model('ratings', ratingSchema);