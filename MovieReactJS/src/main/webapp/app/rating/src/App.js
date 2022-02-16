// /client/src/App.js
//import logo from './logo.svg';
import './assets/App.css';


import React, { useState, useEffect } from "react";

// SERVICES
import ratingService from './services/ratingService';
import CanvasJSReact from './assets/canvasjs.react';
var CanvasJSChart = CanvasJSReact.CanvasJSChart;

function App() {
  
  const [ratingData, setratingsData] = useState(null);
  const [movies, setmovies] = useState(null);

  var data = new FormData();
  var object = {};
  var array = [];

  useEffect(() => {

    if(!ratingData) {
      getRatings();
    }
    if(!movies){
      getMovies()
    }
    
  })

  const sendCom = async postbody => {
    // (B2) APPEND FIELDS
    data.append("rating", document.getElementById("rating").value);
    // data.append("commentTitle", document.getElementById("ctitle").value);
    data.append("commentContent", document.getElementById("comment").value);
    data.append("movieId", document.getElementById("movie").value);
    data.forEach((value, key) => object[key] = value);
    let res = await ratingService.sendComment({
      "rating" :object["rating"],
      "commentTitle" : object["commentTitle"],
      "commentContent":object["commentContent"],
      "movieId":object["movieId"]
      });
    console.log(res);
  }

  

  const getRatings = async () => {
    
    let result = await ratingService.getRating();
    setratingsData(result)
    console.log(ratingData);

  }

  const getMovies = async () => {
    let result = await ratingService.getMovies();
    setmovies(result)
    console.log(movies);
  }


  const sendComment = async () => {
    sendCom()
  }
 
  return (
    <div className="App">
      <div>
        <h1>Give a Comment on a Movie</h1>
        <form id="formElem">
          <select id="movie" name="movie">  
            <option> ---Choose title--- </option>  
            {(movies && movies.length > 0) ? (
              movies.map(movie => <option value={movie.title}> {movie.title} </option>)
              ): (
                <p>No Movie found</p>
              )}  
          </select> 
          <br/>
          <label for="rating">Rating:</label>
          <input id="rating" name="rating" defaultValue={"10"}/>
          <br/>
          <label for="comment">Comment:</label>
          <input id="comment" name="comment" defaultValue={"Write..."}/>
          <br/>
          <input id="postSubmit" type="submit" onClick={sendComment} value="Send!"/>
        </form>
      </div>
      <br/>
      {/* <h1>List of Comments:</h1>
      <ul className="list">
        {(ratings && ratings.length > 0) ? (
          ratings.map(rating => renderRating(rating))
        ) : (
          <p>No Ratings found</p>
        )}
      </ul> */}

        <div hidden>
        {(ratingData && ratingData.length > 0) ? ( // Populate Chart Data
            ratingData.map(rate => array.push({y:rate.count,label:rate._id}))
          ) : (
            <p>No Ratings found</p>
          )}
        </div>
    
      <div>
        <CanvasJSChart options = {{
          animationEnabled: true,
          exportEnabled: true,
          theme: "dark2", // "light1", "dark1", "dark2"
          title:{
            text: "Popularity of Movies"
          },
          data: [{
            type: "pie",// pie,column,spline
            indexLabel: "{label}: {y}",		
            startAngle: -90,
            dataPoints: array

          }]
        }}
              /* onRef = {ref => this.chart = ref} */
          />
      </div>

    </div>
  );
}

export default App;