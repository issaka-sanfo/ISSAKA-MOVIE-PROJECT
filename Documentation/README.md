#MOVIE-PROJECT

Here is a quick documentation of the project diveded into 4 Chepters:
On the root folder we have 3 subfolders: MovieJavaWeb, MovieNodeJS and MovieReactJS



Chapter #1: MovieJavaWeb


1: DataModels: Java classes that represents the Database.

	/src/main/java/fr/epita/ratingMovies/domain: 
		
		*Address.java
		*Contact.java
		*Movie.java
		*MovieUser.java
		*Role.java
		*SeenMovie.java
		*User.java




2: Repositories: JPA for Database queries.

	/src/main/java/fr/epita/ratingMovies/repository:

		*AddressRepository.java
		*ContactRepository.java
		*MovieRepository.java
		*MovieUserRepository.java
		*RoleRepository.java
		*SeenMovieRepository.java
		*UserRepository.java




3: Services: 
	
	3.1: DTO - Data Transfer Object for the services channels
		
		*AddressDTO.java
		*ContactDTO.java
		*MovieDTO.java
		*MovieUserDTO.java
		*RoleDTO.java
		*SeenMovieDTO.java
		*UserDTO.java


	3.2: Mapper - To map the DTO fields to the data model classes

		*AddressMapper.java
		*ContactMapper.java
		*MovieMapper.java
		*MovieUserMapper.java
		*RoleMapper.java
		*SeenMovieMapper.java
		*UserMapper.java


	3.3: Service Classes - To serve the entity operations 'CRUD'

		*AddressService.java
		*ContactService.java
		*MovieService.java
		*MovieUserService.java
		*RoleService.java
		*SeenMovieService.java
		*UserService.java




4: Web: 
	
	4.1: REST - we make APIs endpoints for all the entities

		*AddressResource.java			:		https://localhost:8080/api/addresses
		*ContactResource.java			:		https://localhost:8080/api/
		*MovieResource.java			:		https://localhost:8080/api/
		*MovieUserResource.java		:		https://localhost:8080/api/
		*RoleResource.java			:		https://localhost:8080/api/
		*SeenMovieResource.java		:		https://localhost:8080/api/
		*UserResource.java			:		https://localhost:8080/api/

	Those Endpoints are used for API Management in APIMAN.

5: Run:

	1. Configuration files ("application-dev.yml" and "application-prod.yml") to run the Backend can be find in:
	MovieJavaWeb\src\main\resources\config

	DataBase configuration in application-dev.yml
	...
	datasource:
		type: com.zaxxer.hikari.HikariDataSource
		url: jdbc:postgresql://localhost:5432/MovieJavaWeb
		username: postgres
		password: root
	...

	2. The Java Backend can be run either with intelliJ Run button or the following command:
	$ mvnw          // it means maven work as maven is used for dependencies

	3. The project should be running on port 8080 in development mod, for production mod we need the following command:
	$ mvnw -Pprod // with port 8080 by default


Chapter #2: MovieNodeJS

	1. The following dependencies are installed:
	"dependencies": {
		"body-parser": "^1.19.1", // Parse data returned by API call to JSON for instance
		"express": "^4.17.2", // Simply MongoDB Queries
		"mongoose": "^6.1.3" // MongoDB library
	},
	"devDependencies": {
		"concurrently": "^6.5.1", // Run commands concurrently on different ports
		"nodemon": "^2.0.15" // Reload the project While modifying the code
	}

	2. Models in the subfolder: MovieNodeJS/models

	Movie:
	const movieSchema = new Schema({
		title: String,
		releaseDate: String,
		category: String,
		movieDirector: String,
	})

	Ratings:
	const ratingSchema = new Schema({
		rating: String,
		commentTitle: String,
		commentContent: String,
		userId: String,
		movieId: String,
	})



	3. The NodeJS APIs are defined in the subfolder MovieNodeJS/routes

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
 

	4. Run the Node Backend with the following command:
	$ npm run nodejs


Chapter #3: MovieReactJS

	1. The Movie Entities are in the subfolder:
	MovieReactJS/src/main/webapp/app/entities

	2. The rating is a subreact project:
	MovieReactJS/src/main/webapp/app/rating

	3. For APIs call "axios" is used in React
	Run the ReactJS Frontend with the following command:

	$ npm start // the frontend should be running on port 9000 for movies and port 3000 for movie-rating

	When this command is run there is a Menu link on the NavBar to Open the Rating Page with A Chart
	To add a new Movie Go to : http://localhost:9000/movie/new

		Run Commands' configuration:
		"scripts": {
			...,
			"rating": "npm run start --prefix src/main/webapp/app/rating",
			"start": "concurrently --kill-others-on-fail \"npm run rating\" \"npm run webpack:dev\"",
			...
		}

Chapter #4: API Management

1. Install APIMan
2. Register a Producer and Consumer user
3. Define an Organization for both the Producer and the Consumer
4. Define a Plan, a Policy and expose an API for the Producer
5. As Consumer, register to the Producer API
