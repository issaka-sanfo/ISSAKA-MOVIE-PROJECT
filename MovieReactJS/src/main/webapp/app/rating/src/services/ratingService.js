//  /client/src/services/productService.js

import axios from 'axios';

export default {

  getAll: async () => {
    let res = await axios.get(`/api/rating`);
    return res.data || [];
  },

  sendComment: async postbody => {
    let res = await axios.post(`/api/rating`,postbody);
    return res.data || [];
  },

  getRating: async () => {
    let res = await axios.get(`/api/ratingpermovie`);
    return res.data || [];
  },

  getMovies: async () => {
    let res = await axios.get(`/api/movie`);
    return res.data || [];
  }

}