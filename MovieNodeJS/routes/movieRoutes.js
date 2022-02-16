// /routes/productRoutes.js
const mongoose = require('mongoose');
const Rating = mongoose.model('ratings');
const Movie = mongoose.model('movies');

module.exports = (app) => {

  app.get(`/api/rating`, async (req, res) => {
    let ratings = await Rating.find();
    return res.status(200).send(ratings);
  });

  app.get(`/api/ratingpermovie`, async (req, res) => {
    let ratings = await Rating.aggregate([ {$group : {_id:"$movieId", count:{$sum:1}}} ]);
    return res.status(200).send(ratings);
  });

  app.post(`/api/rating`, async (req, res) => {
    let ratings = await Rating.create(req.body);
    return res.status(200).send(ratings);
  });

  app.get(`/api/movie`, async (req, res) => {
    let movies = await Movie.find();
    return res.status(200).send(movies);
  });

  app.post(`/api/movie`, async (req, res) => {
    let movie = await Movie.create(req.body);
    return res.status(201).send({
      error: false,
      movie
    })
  })

  app.put(`/api/movie/:id`, async (req, res) => {
    const {id} = req.params;

    let movie = await Movie.findByIdAndUpdate(id, req.body);

    return res.status(202).send({
      error: false,
      movie
    })

  });

  app.delete(`/api/movie/:id`, async (req, res) => {
    const {id} = req.params;

    let movie = await Movie.findByIdAndDelete(id);

    return res.status(202).send({
      error: false,
      movie
    })

  })

}